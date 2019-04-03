package applica.feneal.services.impl.lavoratori;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.lavoratori.ListaLavoroRepository;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleRepository;
import applica.feneal.domain.data.dbnazionale.UtenteDBNazioneRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.IscrittoAnnoInCorso;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.search.LavoratoreSearchParams;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.tessere.Tessera;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.utils.Box;
import applica.feneal.domain.validation.LavoratoreValidator;
import applica.feneal.services.DelegheService;
import applica.feneal.services.LavoratoreService;
import applica.framework.*;
import applica.framework.security.Security;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fgran on 06/04/2016.
 */
@Service
public class LavoratoriServiceImpl implements LavoratoreService {

    @Autowired
    private DelegheRepository delRep;

    @Autowired
    private CompanyRepository compRep;

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private UtenteDBNazioneRepository utenteNazRep;

    @Autowired
    private LavoratoreValidator lavVal;

    @Autowired
    private Security sec;

    @Autowired
    private IscrizioniRepository isRep;

    @Autowired
    private LiberoDbNazionaleRepository libRep;

    @Autowired
    private DettaglioQuoteAssociativeRepository quoRep;

    @Autowired
    private ListaLavoroRepository liRep;

    @Autowired
    private UtenteDBNazioneRepository utBRep;

    @Autowired
    private DelegheService delServ;
    @Autowired
    private AziendeRepository azRep;

    @Override
    public Lavoratore getLavoratoreMultiterritorioById(long loggedUserId, long id) {
        //in questa funzione la richiesta di un lavoratore non è
        //necessariamente riferita ad un lavoratore anagrafato nel territorio
        //dell'utente loggato pertanto è necessaria una verifica preliminare.

        //se il lavoratore cercato per id afferisce al territorio dell'utente loggato allora
        //lo restituisco senza problemi.

        //altrimenti ne prendo il codice fiscale e verifico se esiste un utente con quel codice fiscale
        //censito per il territorio delll'utente loggato
        //se esiste prendo quell'utente (l'utente con un altro id) e lo restituisco (previa verifica del telefono
        //e del cell rispetto all'utente indicato vedi sotto)

        //se non esiste nel territorio dell'utente loggato un lavoratore con quel codice fiscale
        //clono il lavoratore per il terriotrio e lo restituisco

        //per prima cosa recupero l'utente per id disabilitando tutti i filtri
        Lavoratore requiredLav = findLavoratoreMultiTerritorioBisablingOwnerShip(id);
        //verifico se appartiene al territorio dell'utente loggato
        if (((User) sec.getLoggedUser()).getCompany().getLid() == requiredLav.getCompanyId())
            return requiredLav;

        //se sono qui il lavoratore appartiene ad un altro territorio
        //ricerco per codice fiscale
        Lavoratore sameTerritorioLav = findLavoratoreByFiscalCode(requiredLav.getFiscalcode());
        //se il lavoratore esiste verifica se è necessario aggiustarne i cellulari
        if (sameTerritorioLav != null)
        {
            verifyCellNumbers(requiredLav, sameTerritorioLav);
            return sameTerritorioLav;
        }

        //se sono giunto qui il lavoratore non esiste nel contesto del proprio terriotiro allora lo creo
        //clonandolo
        //mi basta prendere il lavoratore (requredLav)
        //togliergli l'id e il company id e salvarlo!!!!!
        requiredLav.setId(null);
        requiredLav.setCompanyId(0);
        requiredLav.setCe("");
        requiredLav.setEc("");
        lavRep.save(requiredLav);

        //ritorno il lavoratore con il nuovo id
        return requiredLav;
    }

    private void verifyCellNumbers(Lavoratore requiredLav, Lavoratore sameTerritorioLav) {
        //devo verificare che esisteno i numeri di telefono e se non esistono li prendo dall'altra anagrafica
        if (!StringUtils.isEmpty(sameTerritorioLav.getCellphone()))
            return;
        if (!StringUtils.isEmpty(sameTerritorioLav.getPhone()))
            return;

        if (StringUtils.isEmpty(sameTerritorioLav.getCellphone()))
            sameTerritorioLav.setCellphone(requiredLav.getCellphone());
        if (StringUtils.isEmpty(sameTerritorioLav.getPhone()))
            sameTerritorioLav.setPhone(requiredLav.getPhone());

        lavRep.save(sameTerritorioLav);

    }

    private Lavoratore findLavoratoreMultiTerritorioBisablingOwnerShip(long id) {
        LoadRequest req = LoadRequest.build().disableOwnershipQuery().filter("id", id);
        return lavRep.find(req).findFirst().get();
    }

    @Override
    public Lavoratore getLavoratoreById(long loggedUserId, long lavId) {
        return lavRep.get(lavId).orElse(null);
    }

    @Override
    public void saveOrUpdate(long loggedUserId, Lavoratore lav) throws Exception {

        String error = lavVal.validate(lav);
        if (org.apache.commons.lang.StringUtils.isEmpty(error))
        {
            lavRep.save(lav);
            return;
        }

        throw new Exception(error);
    }

    @Override
    public void delete(long loggedUserId, long idLav) throws Exception {
        User u = ((User) sec.getLoggedUser());
        //devo eseguire "a mano alcuni controlli di integrità referenziale"
        //devo verificare che non ci siano per l'utente le seguenti entità

        //Quote associative (dettaglio quota)
        //lista lavoro

        //cerco se ci sono quote riferite all'id del lavoratore
        LoadRequest req = LoadRequest.build().filter("idLavoratore", idLav);
        List<DettaglioQuotaAssociativa> l = quoRep.find(req).getRows();
        if (l.size() > 0 )
            throw new Exception("Sono presenti quote associative");


        //devo adesso eseguire una query direttamente nella tabella di associazione
        //delle lista di lalvoro
        Long numOfOccurrenceInListeLavoro = liRep.getNumberOfListForWorker(idLav);
        if (numOfOccurrenceInListeLavoro > 0)
            throw new Exception("Il lavoratore è presente in una o più liste di lavoro");



        lavRep.delete(idLav);
    }

    private List<Company> findTerrirotiPerRegioneUtenteLoggato(){
        User u = ((User) sec.getLoggedUser());
        int regionId = u.getCompany().getRegionId();

       return compRep.find(LoadRequest.build()).getRows().stream().filter(a -> a.getRegionId() == regionId).collect(Collectors.toList());
    }

    @Override
    public List<Lavoratore> findLavoratoriMultiterritorio(long loggedUserId, LavoratoreSearchParams params) {

//        if (params.isEmpty())
//            return new ArrayList<>();

        //  if (params.getPage() == null || params.getPage() == 0)
        params.setPage(1);

        int rowsPerPage = 500;

        LoadRequest req = null;

        if (StringUtils.isEmpty(params.getCompany())){
            req = LoadRequest.build().disableOwnershipQuery();

            List<Company> t = findTerrirotiPerRegioneUtenteLoggato();
            Disjunction or = new Disjunction();
            or.setChildren(new ArrayList<>());

            for (Company company : t) {
                Filter f = new Filter();
                f.setProperty("companyId");
                f.setType(Filter.EQ);
                f.setValue(company.getLid());
                or.getChildren().add(f);
            }

            req.getFilters().add(or);


        }
        else
            req = LoadRequest.build().disableOwnershipQuery().filter("companyId", params.getCompany());

        if (StringUtils.hasLength(params.getNamesurname())){
            //ricerca dal tasto in alto nella barra di ricerca globale

            Disjunction or = new Disjunction();

            Filter f = new Filter();
            f.setProperty("namesurname");
            f.setType(Filter.LIKE);
            f.setValue(params.getNamesurname());


            Filter f1 = new Filter();
            f1.setProperty("surnamename");
            f1.setType(Filter.LIKE);
            f1.setValue(params.getNamesurname());

            or.setChildren(Arrays.asList(f,f1));

            req.getFilters().add(or);



        }else{
            Conjunction and = new Conjunction();

            List<Filter> filters = new ArrayList<>();


            Filter f1 = new Filter();
            f1.setProperty("name");
            f1.setType(Filter.LIKE);
            f1.setValue(params.getName());
            filters.add(f1);


            Filter f2 = new Filter();
            f2.setProperty("surname");
            f2.setType(Filter.LIKE);
            f2.setValue(params.getSurname());
            filters.add(f2);

            Filter f3 = new Filter();
            f3.setProperty("fiscalcode");
            f3.setType(Filter.LIKE);
            f3.setValue(params.getFiscalcode());
            filters.add(f3);

            if (!StringUtils.isEmpty(params.getCell())){
                Filter f4 = new Filter();
                f4.setProperty("cellphone");
                f4.setType(Filter.LIKE);
                f4.setValue(params.getCell());
                filters.add(f4);
            }


            and.setChildren(filters);

            req.getFilters().add(and);
        }

        req.setPage(params.getPage());
        req.setRowsPerPage(rowsPerPage);



        List<Lavoratore>  result =lavRep.find(req).getRows();

        //se ho fatto una ricerca multirterritorio aggijungo la proprietà comapnyname

        List<Company> cc = compRep.find(null).getRows();

        for (Lavoratore lavoratore : result) {
            lavoratore.setCompanyName(cc.stream().filter(a -> a.getLid() == lavoratore.getCompanyId()).findFirst().get().getDescription());
        }


        return result;
    }


    @Override
    public List<Lavoratore> findLocalLavoratori(long loggedUserId, LavoratoreSearchParams params) {

//        if (params.isEmpty())
//            return new ArrayList<>();

      //  if (params.getPage() == null || params.getPage() == 0)
            params.setPage(1);

        int rowsPerPage = 200;

        LoadRequest req = null;

        if (StringUtils.isEmpty(params.getCompany()))
            req = LoadRequest.build();
        else
            req = LoadRequest.build().disableOwnershipQuery().filter("companyId", params.getCompany());

        if (StringUtils.hasLength(params.getNamesurname())){
            //ricerca dal tasto in alto nella barra di ricerca globale

            Disjunction or = new Disjunction();

            Filter f = new Filter();
            f.setProperty("namesurname");
            f.setType(Filter.LIKE);
            f.setValue(params.getNamesurname());


            Filter f1 = new Filter();
            f1.setProperty("surnamename");
            f1.setType(Filter.LIKE);
            f1.setValue(params.getNamesurname());

            or.setChildren(Arrays.asList(f,f1));

            req.getFilters().add(or);



        }else{
            Conjunction and = new Conjunction();

            List<Filter> filters = new ArrayList<>();


            Filter f1 = new Filter();
            f1.setProperty("name");
            f1.setType(Filter.LIKE);
            f1.setValue(params.getName());
            filters.add(f1);


            Filter f2 = new Filter();
            f2.setProperty("surname");
            f2.setType(Filter.LIKE);
            f2.setValue(params.getSurname());
            filters.add(f2);

            Filter f3 = new Filter();
            f3.setProperty("fiscalcode");
            f3.setType(Filter.LIKE);
            f3.setValue(params.getFiscalcode());
            filters.add(f3);

            if (!StringUtils.isEmpty(params.getCell())){
                Filter f4 = new Filter();
                f4.setProperty("cellphone");
                f4.setType(Filter.LIKE);
                f4.setValue(params.getCell());
                filters.add(f4);
            }


            and.setChildren(filters);

            req.getFilters().add(and);
        }

        req.setPage(params.getPage());
        req.setRowsPerPage(rowsPerPage);



        List<Lavoratore>  result =lavRep.find(req).getRows();

        //se ho fatto una ricerca multirterritorio aggijungo la proprietà comapnyname
        if (!StringUtils.isEmpty(params.getCompany())){
            List<Company> cc = compRep.find(null).getRows();

            for (Lavoratore lavoratore : result) {
                lavoratore.setCompanyName(cc.stream().filter(a -> a.getSid().equals(params.getCompany())).findFirst().get().getDescription());
            }
        }

        return result;
    }

    @Override
    public List<UtenteDbNazionale> findRemoteLavoratori(long loggedUserId, LavoratoreSearchParams params) throws ParseException {


        //if (params.getPage() == null || params.getPage() == 0)
        params.setPage(1);

        int rowsPerPage = 50;

        LoadRequest req = LoadRequest.build();


        Conjunction and = new Conjunction();

        List<Filter> filters = new ArrayList<>();

        if (!StringUtils.isEmpty(params.getFiscalcode())){


                Filter f8 = new Filter();
                f8.setProperty("codiceFiscale");
                f8.setType(Filter.LIKE);
                f8.setValue(params.getFiscalcode());
                filters.add(f8);


        }else{
            if (StringUtils.isEmpty(params.getSurname())){
                return new ArrayList<>();
            }
            if (params.getSurname().length() <4)
                return new ArrayList<>();

            if (!StringUtils.isEmpty(params.getSurname())){
                Filter f1 = new Filter();
                f1.setProperty("cognome");
                f1.setType(Filter.LIKE);
                f1.setValue(params.getSurname());
                filters.add(f1);
            }

            if (!StringUtils.isEmpty(params.getName())){
                Filter f2 = new Filter();
                f2.setProperty("nome");
                f2.setType(Filter.LIKE);
                f2.setValue(params.getName());
                filters.add(f2);
            }



            if (!StringUtils.isEmpty(params.getSex())){

                Filter f3 = new Filter();
                f3.setProperty("sesso");
                f3.setType(Filter.EQ);
                f3.setValue(params.getSex());
                filters.add(f3);
            }


            if (!StringUtils.isEmpty(params.getBirthDate())) {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                Date birthDate = null;
                try{
                    birthDate = formatter.parse(params.getBirthDate());
                }catch(Exception e){
                    birthDate = null;
                }
                if (birthDate != null){
                    Filter f4 = new Filter();
                    f4.setProperty("dataNascita");
                    f4.setType(Filter.EQ);
                    f4.setValue(birthDate);
                    filters.add(f4);
                }

            }

            if (!StringUtils.isEmpty(params.getNationality())) {
                Filter f5 = new Filter();
                f5.setProperty("id_Nazione");
                f5.setType(Filter.EQ);
                f5.setValue(Integer.parseInt(params.getNationality()));
                filters.add(f5);
            }

            if (!StringUtils.isEmpty(params.getLivingProvince())) {
                Filter f6 = new Filter();
                f6.setProperty("id_Provincia_Residenza");
                f6.setType(Filter.EQ);
                f6.setValue(Integer.parseInt(params.getLivingProvince()));
                filters.add(f6);
            }

            if (!StringUtils.isEmpty(params.getLivingCity())) {
                Filter f7 = new Filter();
                f7.setProperty("id_Comune_Residenza");
                f7.setType(Filter.EQ);
                f7.setValue(Integer.parseInt(params.getLivingCity()));
                filters.add(f7);
            }
        }




        and.setChildren(filters);

        req.getFilters().add(and);


        req.setPage(params.getPage());
        req.setRowsPerPage(rowsPerPage);
        //req.setSorts(Arrays.asList(new Sort("cognome", false)));
        List<UtenteDbNazionale> utenti = utenteNazRep.find(req).getRows();

        for (UtenteDbNazionale utenteDbNazionale : utenti) {
            utenteDbNazionale.setIscrizioni(isRep.findIscrizioniByFiscalCode(utenteDbNazionale.getCodiceFiscale()));
            utenteDbNazionale.setNumIscrizioni(utenteDbNazionale.getIscrizioni().size());
        }

        return utenti;
    }

    @Override
    public List<Lavoratore> findCurrentIscrizioniForAzienda(long firmId) {

        //devo ricercare tutti i dettagli quote associative che hanno come idAzienda quella selezionata
        Azienda a = azRep.get(firmId).orElse(null);
        if (a == null)
            return new ArrayList<>();

        LoadRequest req =LoadRequest.build().filter("idAzienda", firmId);
        req.getSorts().add(new Sort("dataDocumento", true));
        List<DettaglioQuotaAssociativa> d = quoRep.find(req).getRows();
        List<DettaglioQuotaAssociativa> quoteIscrittiSenzaDuplicati = new ArrayList<>();
        // Eventualmente faccio visualizzare una sola volta il record relativo ad un iscritto
        for (DettaglioQuotaAssociativa quota : d) {
            if (quoteIscrittiSenzaDuplicati.stream().filter(q -> q.getIdLavoratore() == quota.getIdLavoratore()).count() == 0)
                quoteIscrittiSenzaDuplicati.add(quota);
        }

        //verifoco l'esistenza di una dleega attiva
        List<DettaglioQuotaAssociativa> ll = new ArrayList<>();

        //ottengo la provincia di bolzano
        Company p = compRep.findCompanyByProvinceName("Bolzano");
        if (((User) sec.getLoggedUser()).getCompany().getLid() == p.getLid()){
            //eseguo la verifica della delega attiva
            for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quoteIscrittiSenzaDuplicati) {
                //se non ho selezionato una provincia nella ricerca... la recupero dalla quota
                if (delServ.hasWorkerDelegaAttivaOAccettata(dettaglioQuotaAssociativa.getIdLavoratore(),
                        dettaglioQuotaAssociativa.getSettore(),dettaglioQuotaAssociativa.getEnte(), a.getDescription(), dettaglioQuotaAssociativa.getProvincia())){
                    ll.add(dettaglioQuotaAssociativa);
                }
            }
        }else{
            ll = quoteIscrittiSenzaDuplicati;
        }





        HashMap<Long, Lavoratore> g = new HashMap<>();

        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : ll) {
            if (!g.containsKey(dettaglioQuotaAssociativa.getIdLavoratore()))
                if (isPeriodCompliant(dettaglioQuotaAssociativa)){
                    Lavoratore ll1 =  lavRep.get(dettaglioQuotaAssociativa.getIdLavoratore()).get();
                    ll1.setNotes(dettaglioQuotaAssociativa.getTipoDocumento());
                    g.put(dettaglioQuotaAssociativa.getIdLavoratore(),ll1);
                }

        }
//
//
//
       return new ArrayList<>(g.values());
    }

    private boolean isPeriodCompliant(DettaglioQuotaAssociativa dettaglioQuotaAssociativa) {
        //devo verificare se la competenza della quota è relativa all'ultimo anno
        Date inizio = dettaglioQuotaAssociativa.getDataInizio();
        Date fine = dettaglioQuotaAssociativa.getDataFine();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        boolean firstPartOfYear = true; //primo semestre
        //se il mese è gennao(0), febbraio (1) e marzo(2)
        //o
        if (month < 3 ){
            firstPartOfYear = true;
            //l'anno rimane invariato

        }else if (month > 8){
            firstPartOfYear = true;
            year = year + 1;
            //stiamo parlando del primo semestre dell'anno successivo
            //a quello attuale
        }
        else{
            firstPartOfYear = false;
        }


        //devo verificare se se negli ultimi due semestri risulta iscritto
        //se siamo nel secondo semestre
        // devo verificare che ci sia
        // una quotaa partire dal primo semestre dello stesso anno
        //altrimentoi se sono nel primo semestre devo verificare
        //se cè una quota dal primo semestre

        if (firstPartOfYear){
            Calendar c = Calendar.getInstance();
            c.set(year-1, Calendar.APRIL, 1, 0, 0);
            if (fine.after(c.getTime()) || fine.getTime() == c.getTime().getTime())
                return true;

        }else{
            Calendar c = Calendar.getInstance();
            c.set(year-1, Calendar.OCTOBER, 1, 0, 0);
            if (fine.after(c.getTime()) || fine.getTime() == c.getTime().getTime())
                return true;
        }

        return false;



    }

    @Override
    public List<LiberoDbNazionale> findNonIscrizioniForAzienda(long firmId) {
        User u = ((User) sec.getLoggedUser());

        //recupero la prpovincia di default dell'utente looggato altrimenti prendo la proima
        List<Province> p = u.getCompany().getProvinces();


        Azienda a = azRep.get(firmId).orElse(null);
        if (a == null)
            return new ArrayList<>();


        Disjunction d = new Disjunction();
        List<Filter> filters = new ArrayList<>();
        for (Province province : p) {
            filters.add(new Filter("nomeProvinciaFeneal", province.getDescription()));
        }

        //questo rappresenta il filtro sulle provincie
        d.setChildren(filters);

        //adesso aggiungo il filtro sul nome dell'azienda
        Filter f = new Filter("currentAzienda", a.getDescription());

        LoadRequest req = LoadRequest.build();
        req.getFilters().add(d);
        req.getFilters().add(f);


        List<LiberoDbNazionale> libs = libRep.find(req).getRows();

        for (LiberoDbNazionale lib : libs) {
            lib.setIscrizioni(isRep.findIscrizioniByFiscalCode(lib.getCodiceFiscale()));
        }


        for (LiberoDbNazionale lib : libs) {
            //cerco il numeor di deleghe per ogni libero
            lib.setDelegheOwner(delRep.hasLavoratoreSomeDelegaForCompany(((User) sec.getLoggedUser()).getCompany().getLid(), lib.getCodiceFiscale()));
        }


        return libs;
    }





    private List<Lavoratore> getMockedWorkers() {
        Lavoratore lav1 = new Lavoratore();

        lav1.setId(1);
        lav1.setName("Angelo");
        lav1.setSurname("Dalco");
        lav1.setBirthDate(new Date());
        lav1.setNationality("1");
        lav1.setLivingCity("5");
        lav1.setAddress("Viale Europa");
        lav1.setCap("76200");
        lav1.setFiscalcode("DDDDDDDDDDDDDDDD");

        Lavoratore lav2 = new Lavoratore();

        lav2.setId(2);
        lav2.setName("Ciccio");
        lav2.setSurname("Randa");
        lav2.setBirthDate(new Date());
        lav2.setNationality("1");
        lav2.setLivingCity("6");
        lav2.setAddress("Via Giolitti");
        lav2.setCap("56100");
        lav2.setFiscalcode("EEEEEEEEEEEEEEEE");

        return new ArrayList<Lavoratore>() {
            {
                add(lav1);
                add(lav2);
            }
        };

    }

    public Lavoratore findLavoratoreForProvince(String provinceName, String fiscalcode) {

        return lavRep.searchLavoratoreForProvince(provinceName, fiscalcode);
    }

    @Override
    public Lavoratore findLavoratoreByFiscalCode(String fiscalcode) {

        if (StringUtils.isEmpty(fiscalcode))
            return null;
        User u = ((User) sec.getLoggedUser());
        return lavRep.searchLavoratoreForCompany(u.getCompany().getLid(), fiscalcode);
    }

    @Override
    public UtenteDbNazionale findRemoteLavoratoreByFiscalCode(String fiscalCode) {

        return utBRep.findUtenteByFiscalCode(fiscalCode);
    }

    @Override
    public LiberoDbNazionale findRemoteLavoratoreLiberoByFiscalCodeAndProvince(String fiscalCode, String province) {

        LoadRequest req = LoadRequest.build().filter("codiceFiscale",fiscalCode).filter("nomeProvinciaFeneal", province);
        return libRep.find(req).findFirst().orElse(null);
    }

    @Override
    public Lavoratore getLavoratoreByFiscalCodeOrCreateItIfNotexist(String fiscalCode) throws Exception {

        Lavoratore l = this.findLavoratoreByFiscalCode(fiscalCode);

        if (l == null) {

            //lo recupero dal database nazionale
            UtenteDbNazionale n = this.findRemoteLavoratoreByFiscalCode(fiscalCode);
            if (n== null)
                return null;



            //se cè lo salvo nel db
            Lavoratore l1 = new Lavoratore(n);
            saveOrUpdate(((User) sec.getLoggedUser()).getLid(), l1);
            return l1;
        }

        return l;
    }

    @Override
    public Lavoratore getLavoratoreByFiscalCodeOrCreateItIfNotexist(String fiscalCode, String province) throws Exception {
        //cerco nel database della provincia
        Lavoratore l = findLavoratoreByFiscalCode(fiscalCode);

        if (l == null) {
            //se cè lo salvo nel db
            Lavoratore l1 = null;
            //lo recupero dal database nazionale
            UtenteDbNazionale n = findRemoteLavoratoreByFiscalCode(fiscalCode);
            if (n== null)
            {
                //se non cè lo recupero dalla lista dei non iscritti
                LiberoDbNazionale lib = findRemoteLavoratoreLiberoByFiscalCodeAndProvince(fiscalCode, province);
                if (lib == null)
                    return null;
                else{
                    l1 = new Lavoratore(lib);
                }
            }else{
                l1 = new Lavoratore(n);
            }

            saveOrUpdate(((User) sec.getLoggedUser()).getLid(), l1);
            return l1;
        }

        return l;
    }

    @Override
    public Lavoratore getLavoratoreLiberoByFiscalCodeOrCreateItIfNotexist(String fiscalCode, String province) throws Exception {


        //devo verificare se esiste il lavroatore nella tabella
        //e se non esiste cercarlo esclusivamente nella tabella dei liberi
        //la ricerca nella tabella dei lavoratori viene prima fata per questioni di prestazioni
        //sul sempolice codice fiscale

        Lavoratore l = superPerformSearch(fiscalCode);


        if (l == null) {

                //se non cè lo recupero dalla lista dei non iscritti
                LiberoDbNazionale lib = findRemoteLavoratoreLiberoByFiscalCodeAndProvince(fiscalCode, province);
                if (lib == null)
                    return null;
                else{
                    l = new Lavoratore(lib);
                }


            saveOrUpdate(((User) sec.getLoggedUser()).getLid(), l);
            return l;
        }

        return l;


    }

    private Lavoratore superPerformSearch(String fiscalCode) {
        final Box box = new Box();
        final String fiscal = fiscalCode;

        isRep.executeCommand(new Command() {
            @Override
            public void execute() {
                Session s = isRep.getSession();
                Transaction tx = null;

                try{

                    tx = s.beginTransaction();

                    String query = "Select id ,fiscalCode from fenealweb_lavoratore where fiscalCode = '" + fiscal + "' and companyId = " + ((User) sec.getLoggedUser()).getCompany().getLid();
                    List<Object[]> objects = s.createSQLQuery(query)
                            .addScalar("ID")
                            .addScalar("fiscalCode")
                            .list();

                    tx.commit();

                    List<Lavoratore> a = new ArrayList<>();
                    for (Object[] object : objects) {

                        Lavoratore v = new Lavoratore();
                        v.setId((BigInteger)object[0]);
                        v.setFiscalcode((String)object[1]);

                        a.add(v);
                    }


                    box.setValue(a);


                }
                catch(Exception e){
                    e.printStackTrace();
                    tx.rollback();
                }
                finally{
                    s.close();

                }
            }


        });



        List<Lavoratore> l = ((List<Lavoratore>) box.getValue());

        if (l.size() > 0)
            return l.get(0);

        return null;

    }

    @Override
    public Lavoratore getLavoratoreByRemoteIdOrCreateItIfNotexist(int workerId) throws Exception {


        UtenteDbNazionale ut = utBRep.get(workerId).orElse(null);
        if (ut == null)
            return null;

        return getLavoratoreByFiscalCodeOrCreateItIfNotexist(ut.getCodiceFiscale());

    }

    @Override
    public Lavoratore getLavoratoreOfOtherCompanyAndCreateItIfNotExistForCurrentCompany(String id) throws Exception {

        final Box lav = new Box();

        lavRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = lavRep.getSession();
                Transaction t = s.beginTransaction();
                try{
                    Long idWorker = Long.parseLong(id);
                    lav.setValue(s.get(Lavoratore.class, idWorker));
                }catch(Exception ex){

                }finally{
                    s.close();
                }

            }
        });

        Lavoratore l = ((Lavoratore) lav.getValue());

        //una volta ottenuto il lavoratore lo  posso creare nella company di riferimento ammesso che già
        //non esista un lavoratore con lo stesso codice fiscale
        //se non cè nulla vuol dire che il lavoratore è stato cancellato da chi ha creato la notifica
        if (l == null)
           return null;
        String fiscalCode = l.getFiscalcode();
        Lavoratore myWorker = findLavoratoreByFiscalCode(fiscalCode);

        if (myWorker == null){
            //lo creo
            myWorker = new Lavoratore();
            myWorker.setName(l.getName());
            myWorker.setSurname(l.getSurname());
            myWorker.setNamesurname(l.getNamesurname());
            myWorker.setNationality(l.getNationality());
            myWorker.setSurnamename(l.getSurnamename());
            myWorker.setAddress(l.getAddress());
            myWorker.setBirthDate(l.getBirthDate());
            myWorker.setBirthPlace(l.getBirthPlace());
            myWorker.setBirthProvince(l.getBirthProvince());
            myWorker.setCap(l.getCap());
            myWorker.setCe(l.getCe());
            myWorker.setCellphone(l.getCellphone());
            myWorker.setEc(l.getEc());
            myWorker.setFiscalcode(l.getFiscalcode());
            myWorker.setLivingCity(l.getLivingCity());
            myWorker.setImage(l.getImage());
            myWorker.setLivingProvince(l.getLivingProvince());
            myWorker.setMail(l.getMail());
            myWorker.setNotes(l.getNotes());
            myWorker.setPhone(l.getPhone());
            myWorker.setSex(l.getSex());

            lavRep.save(myWorker);

        }

        return myWorker;

    }

    @Override
    public IscrittoAnnoInCorso checkIfIscrittoAnnoInCorso(long workerId) throws Exception {

        //devo verificare se se negli ultimi due semestri risulta iscritto
        //se siamo nel periodo da luglio a dicembre devo verificare che ci sia una quota per il secondo semestre dell'anno precedente
        //o del primo dell'anno in corso
        //se siamo nel periodo da gennaio al 30 giugno devo verifcre che ci sia una quota nelll'anno precedetne




        boolean firstPartOfYear = true; //periodo da gennaio a giugno incluso
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        if (month > 5)
            firstPartOfYear = false;

        //recuoero il lavoratore
        Lavoratore lav = lavRep.get(workerId).orElse(null);
        if (lav == null)
            throw new Exception("Lavoratore nullo");

        //recupero le iscrizioni per il lavoratore
        List<Iscrizione> iscrizioni = isRep.findIscrizioniByFiscalCodeWithQuoteDetailsMerge(lav.getFiscalcode());
        if (iscrizioni.size() == 0)
            return new IscrittoAnnoInCorso();


        //pero prima di ricorrere allo stratagemma dei periodi verifico se cè una iscrizione per l'anno corrente
        //nel primo o secondo semestre

        Iscrizione iscrizione = null;

        for (Iscrizione iscr : iscrizioni) {
            if (iscr.getAnno() == year && ((User) sec.getLoggedUser()).getCompany().containProvince(iscr.getNomeProvincia())){
                iscrizione = iscr;
                break;
            }

        }



        if (iscrizione == null)
            iscrizione = checkIfIscritto(iscrizioni, firstPartOfYear);




        if (iscrizione == null)
            return new IscrittoAnnoInCorso();


        IscrittoAnnoInCorso result = new IscrittoAnnoInCorso();
        result.setIscritto(true);
        result.setSettore(iscrizione.getSettore());
        if (result.getSettore().equals(Sector.sector_inps))
        {
            result.setPeriodo(String.valueOf(iscrizione.getAnno()));
            return result;
        }
        if (result.getSettore().equals(Sector.sector_IMPIANTIFISSI))
        {
            result.setPeriodo(String.valueOf(iscrizione.getAnno()));
            result.setAzienda(iscrizione.getAzienda());
            return result;
        }

        result.setPeriodo(String.valueOf(iscrizione.getPeriodo()) + "-" +  String.valueOf(iscrizione.getAnno()));
        result.setAzienda(iscrizione.getAzienda());
        result.setEnte(iscrizione.getEnte());

        return result;
    }



    private Iscrizione checkIfIscritto(List<Iscrizione> iscrizioni, boolean firstPartOfYear) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (Iscrizione iscrizione : iscrizioni) {

            if (((User) sec.getLoggedUser()).getCompany().containProvince(iscrizione.getNomeProvincia()))
            {
                if (iscrizione.getSettore().equals(Sector.sector_IMPIANTIFISSI) && iscrizione.getAnno() == currentYear)
                    return iscrizione;
                else if (iscrizione.getSettore().equals(Sector.sector_inps) && iscrizione.getAnno() == currentYear)
                    return iscrizione;
                else if (iscrizione.getSettore().equals(Sector.sector_edile) ){
                    if (firstPartOfYear){
                        if (iscrizione.getAnno() == currentYear-1 || iscrizione.getAnno() == currentYear)
                            return iscrizione;

                    }else{
                        if (iscrizione.getAnno() == currentYear -1 && iscrizione.getPeriodo() == 2)
                            return iscrizione;

                        if (iscrizione.getAnno() == currentYear)
                            return iscrizione;
                    }
                }
            }


        }

        return null;
    }


}
