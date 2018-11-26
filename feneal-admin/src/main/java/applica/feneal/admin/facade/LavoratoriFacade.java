package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.*;
import applica.feneal.admin.viewmodel.deleghe.UIDelega;
import applica.feneal.admin.viewmodel.lavoratori.*;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.admin.viewmodel.reports.UiDelega;
import applica.feneal.admin.viewmodel.reports.UiIscrizione;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.core.FundRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.FiscalData;
import applica.feneal.domain.model.core.lavoratori.IscrittoAnnoInCorso;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.search.LavoratoreSearchParams;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.domain.model.core.tessere.Tessera;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.Fondo;
import applica.feneal.services.*;
import applica.feneal.services.messages.MessageInput;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgran on 06/04/2016.
 */
@Component
public class LavoratoriFacade {

    @Autowired
    private LavoratoreService svc;

    @Autowired
    private RichieseInfoAiTerritoriService richSvc;

    @Autowired
    private TessereService tessereSvc;

    @Autowired
    private GeoService geosvc;

    @Autowired
    private ComunicazioniService comSvc;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private Security security;

    @Autowired
    private IscrizioniRepository iscrRep;

    @Autowired
    private CompanyRepository companyRep;

    @Autowired
    private LiberiFacade libFac;

    @Autowired
    private DelegheFacade delegheFacade;

    @Autowired
    private MagazzinoDelegheRepository magRep;

    @Autowired
    private QuoteAssociativeService quoteService;

    @Autowired
    private VersamentiFacade verFac;

    @Autowired
    private ReportNonIscrittiService libService;

    public UiCompleteLavoratoreSummary getLavoratoreSummaryById(long id) throws Exception {
        UiCompleteLavoratoreSummary s = new UiCompleteLavoratoreSummary();

        Lavoratore l = getLavoratoreById(id);
        if (l == null)
            return null;

        UiLavoratoreAnagraficaSummary a = convertLavoratoreToUILavoratoreAnagSummary(l);
        s.setData(a);

        // Presenza iscrizione anno in corso
        IscrittoAnnoInCorso iscritto = svc.checkIfIscrittoAnnoInCorso(id);
        UiIscrittoAnnoInCorso i = convertIscrittoDataToUIIscrittoAnnoInCorso(iscritto);
        s.setIscrittoData(i);

        // Presenza tessera
        Tessera tessera = tessereSvc.findPrintedTessera(l.getFiscalcode(), Calendar.getInstance().get(Calendar.YEAR));
        if (tessera != null) {
            UiPrintedTessera t = convertTesseraToUiTessera(tessera);
            s.setTesseraData(t);
        }

        // Presenza altre tessere
        List<Tessera> otherTessere = tessereSvc.findOtherPrintedTessere(l.getFiscalcode(), Calendar.getInstance().get(Calendar.YEAR));
        List<UiPrintedTessera> uiPrintedTessere = new ArrayList<>();
        for (Tessera t : otherTessere) {
            UiPrintedTessera uiPrintedTessera = convertTesseraToUiTessera(t);
            uiPrintedTessere.add(uiPrintedTessera);
        }
        s.setOtherTessereData(uiPrintedTessere);

        return s;
    }

    public Lavoratore getLavoratoreById(long id) {
        return svc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), id);
    }

    private UiLavoratoreAnagraficaSummary convertLavoratoreToUILavoratoreAnagSummary(Lavoratore l) {
        UiLavoratoreAnagraficaSummary s = new UiLavoratoreAnagraficaSummary();
        s.setId(l.getLid());
        s.setName(l.getName());
        s.setSurname(l.getSurname());
        s.setImage(l.getImage());
        s.setNationality(l.getNationality());
        s.setBirthProvince(l.getBirthProvince());
        s.setBirthPlace(l.getBirthPlace());
        s.setBirthDate(l.getBirthDate());
        s.setLivingPlace(l.getLivingCity());
        s.setLivingProvince(l.getLivingProvince());
        s.setAddress(l.getAddress());
        s.setCap(l.getCap());
        s.setFiscalcode(l.getFiscalcode());
        s.setPhone(l.getPhone());
        s.setCellphone(l.getCellphone());
        s.setMail(l.getMail());
        s.setCe(l.getCe());
        s.setEc(l.getEc());
        if (l.getFund() == null)
            s.setFund("");
        else
            s.setFund(l.getFund().getDescription());

        return s;
    }

    public long saveAnagrafica(UiAnagrafica anag) throws Exception {

        Lavoratore l = convertUiAnagraficaToLavoratore(anag);

        svc.saveOrUpdate(((User) security.getLoggedUser()).getLid(),l);

        return l.getLid();
    }

    private Lavoratore convertUiAnagraficaToLavoratore(UiAnagrafica anag) {
        Lavoratore l = new Lavoratore();

        l.setId(anag.getId());
        l.setName(anag.getName());
        l.setSurname(anag.getSurname());
        l.setSex(anag.getSex());
        l.setFiscalcode(anag.getFiscalcode());
        l.setImage(anag.getImage());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            l.setBirthDate(df.parse(anag.getBirthDate()));
        } catch (ParseException e) {
            l.setBirthDate(null);
        }

        l.setAddress(anag.getAddress());
        l.setCap(anag.getCap());
        l.setPhone(anag.getPhone());
        l.setCellphone(anag.getCellphone());
        l.setMail(anag.getMail());
        l.setCe(anag.getCe());
        l.setEc(anag.getEc());
        l.setFund(retrieveFund(anag.getFund()));
        l.setNotes(anag.getNotes());

        //per la nazionalità se il valore inviato dal client è un numero >0 allora rappresenta l'id della nazione
        try {
            Integer idNationality = Integer.parseInt(anag.getNationality());
            if (idNationality != null)
                if (idNationality > 0){
                    Country c = geosvc.getCountryById(idNationality);
                    if (c!= null)
                        l.setNationality(c.getDescription());
                }

        } catch(NumberFormatException e){

            try{
                Country p = geosvc.getCountryByName(anag.getNationality());
                if (p!= null)
                    l.setNationality(p.getDescription());

            }catch(Exception e1){
                l.setNationality(null);
            }

        }

        //per la provincia se il valore inviato dal client è un numero >0 allora rappresenta l'id della provincia
        try {
            Integer idProvince = Integer.parseInt(anag.getBirthProvince());
            if (idProvince != null)
                if (idProvince > 0){
                    Province p = geosvc.getProvinceById(idProvince);
                    if (p!= null)
                        l.setBirthProvince(p.getDescription());
                }

        } catch(NumberFormatException e){



            try{
                Province p = geosvc.getProvinceByName(anag.getBirthProvince());
                if (p!= null)
                    l.setBirthProvince(p.getDescription());

            }catch(Exception e1){
                l.setBirthProvince(null);
            }



        }

        //per la città se il valore inviato dal client è un numero >0 allora rappresenta l'id della città
        try {
            Integer idBirthPlace = Integer.parseInt(anag.getBirthPlace());
            if (idBirthPlace != null)
                if (idBirthPlace > 0){
                    City c = geosvc.getCityById(idBirthPlace);
                    if (c!= null)
                        l.setBirthPlace(c.getDescription());
                }
        } catch(NumberFormatException e){


            try{
                City c = geosvc.getCityByName(anag.getBirthPlace());
                if (c!= null)
                    l.setBirthPlace(c.getDescription());

            }catch(Exception e1){
                l.setBirthPlace(null);
            }





        }

        //per la provincia di residenza se il valore inviato dal client è un numero >0 allora rappresenta l'id della provincia
        try {
            Integer idLivingProvince = Integer.parseInt(anag.getLivingProvince());
            if (idLivingProvince != null)
                if (idLivingProvince > 0){
                    Province p = geosvc.getProvinceById(idLivingProvince);
                    if (p!= null)
                        l.setLivingProvince(p.getDescription());
                }

        } catch(NumberFormatException e){

            try{
                Province p = geosvc.getProvinceByName(anag.getLivingProvince());
                if (p!= null)
                    l.setLivingProvince(p.getDescription());

            }catch(Exception e1){
                l.setLivingProvince(null);
            }



        }

        //per la città di residenza se il valore inviato dal client è un numero >0 allora rappresenta l'id della città
        try {
            Integer idLivingCity = Integer.parseInt(anag.getLivingCity());
            if (idLivingCity != null)
                if (idLivingCity > 0){
                    City c = geosvc.getCityById(idLivingCity);
                    if (c!= null)
                        l.setLivingCity(c.getDescription());
                }
        } catch(NumberFormatException e){



            try{
                City c = geosvc.getCityByName(anag.getLivingCity());
                if (c!= null)
                    l.setLivingCity(c.getDescription());

            }catch(Exception e1){
                l.setLivingCity(null);
            }


        }


        return l;
    }


    private UiIscrittoAnnoInCorso convertIscrittoDataToUIIscrittoAnnoInCorso(IscrittoAnnoInCorso iscritto) {

        UiIscrittoAnnoInCorso uiIscrittoAnnoInCorso = new UiIscrittoAnnoInCorso();

        uiIscrittoAnnoInCorso.setAzienda(iscritto.getAzienda());
        uiIscrittoAnnoInCorso.setEnte(iscritto.getEnte());
        uiIscrittoAnnoInCorso.setIscritto(iscritto.iscritto());
        uiIscrittoAnnoInCorso.setPeriodo(iscritto.getPeriodo());
        uiIscrittoAnnoInCorso.setSettore(iscritto.getSettore());

        return uiIscrittoAnnoInCorso;
    }

    private UiPrintedTessera convertTesseraToUiTessera(Tessera tessera) {

        UiPrintedTessera uiPrintedTessera = new UiPrintedTessera();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Company company = companyRep.find(LoadRequest.build().id(tessera.getCompanyId())).findFirst().orElse(null);

        uiPrintedTessera.setCompany(company);
        uiPrintedTessera.setDate(df.format(tessera.getDate()));
        uiPrintedTessera.setFiscalCode(tessera.getFiscalCode());
        uiPrintedTessera.setPrintedFrom(tessera.getPrintedFrom());
        uiPrintedTessera.setYear(tessera.getYear());

        return uiPrintedTessera;
    }

    public void deleteLavoratore(long id) throws Exception {
        svc.delete(((User) security.getLoggedUser()).getLid(), id);
    }

    public List<Lavoratore> findLocalLavoratori(LavoratoreSearchParams s) {
        return svc.findLocalLavoratori(((User) security.getLoggedUser()).getLid(), s);
    }

    public List<UtenteDbNazionale> findRemoteLavoratori(LavoratoreSearchParams s) throws ParseException {
        return svc.findRemoteLavoratori(((User) security.getLoggedUser()).getLid(), s);
    }

    public boolean isLavoratoreSignedToFenealProvince(String provinceName, String fiscalcode) {
        Lavoratore lavoratore = svc.findLavoratoreForProvince(provinceName, fiscalcode);

        return (lavoratore == null ? false : true);
    }

    public boolean existSmsCredentials() {
        return comSvc.existSmsCredentials();
    }

    private Fondo retrieveFund(String fund) {
        if (StringUtils.isEmpty(fund))
            return null;

        return fundRepository.get(Long.parseLong(fund)).orElse(null);
    }

    public long getIdLavoratoreByFiscalCodeOrCreateItIfNotexist(String fiscalCode) throws Exception {
//        Lavoratore l = svc.findLavoratoreByFiscalCode(fiscalCode);
//
//        if (l == null) {
//
//            //lo recupero dal database nazionale
//            UtenteDbNazionale n = svc.findRemoteLavoratoreByFiscalCode(fiscalCode);
//            if (n== null)
//                return -1;
//
//
//
//            //se cè lo salvo nel db
//            Lavoratore l1 = new Lavoratore(n);
//            svc.saveOrUpdate(((User) security.getLoggedUser()).getLid(), l1);
//            return l1.getLid();
//        }
//
//        return l.getLid();

        Lavoratore ll = svc.getLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode);
        if (ll == null)
            return -1;

        return ll.getLid();
    }

    public long getIdLavoratoreByFiscalCodeOrCreateItIfNotexist(String fiscalCode, String province) throws Exception {
//        //cerco nel database della provincia
//        Lavoratore l = svc.findLavoratoreByFiscalCode(fiscalCode);
//
//        if (l == null) {
//            //se cè lo salvo nel db
//            Lavoratore l1 = null;
//            //lo recupero dal database nazionale
//            UtenteDbNazionale n = svc.findRemoteLavoratoreByFiscalCode(fiscalCode);
//            if (n== null)
//            {
//                //se non cè lo recupero dalla lista dei non iscritti
//                LiberoDbNazionale lib = svc.findRemoteLavoratoreLiberoByFiscalCodeAndProvince(fiscalCode, province);
//                if (lib == null)
//                    return -1;
//                else{
//                    l1 = new Lavoratore(lib);
//                }
//            }else{
//                l1 = new Lavoratore(n);
//            }
//
//            svc.saveOrUpdate(((User) security.getLoggedUser()).getLid(), l1);
//            return l1.getLid();
//        }
//
//        return l.getLid();
        Lavoratore ll = svc.getLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode, province);
        if (ll == null)
            return -1;

        return ll.getLid();
    }

    public UiWorkerIscrizioniChart getIscrizioniChart(long id) throws Exception {
        //ottengo il lavoratore
        Lavoratore l = svc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), id);
        if (l == null)
            throw new Exception("Lavoratore non trovato");

        //adesso recupero tutte le iscrizioni per il lavoartore
        List<Iscrizione> list = iscrRep.findIscrizioniByFiscalCodeWithQuoteDetailsMerge(l.getFiscalcode());



        //adesso devo convertire tutti i dati delle iscrizioni un una classe uiworkeriscrizionichart
        return converToUiIscrizioniChart(l, list);

    }

    public FiscalData getFiscalData(String fiscalcode) throws RemoteException {
        return geosvc.getFiscalData(fiscalcode);
    }

    public void printTessera(long workerId, String province, String sector, HttpServletResponse response) throws Exception {

        //ottengo il lavoratore
        Lavoratore l = svc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), workerId);
        if (l == null)
            throw new Exception("Lavoratore non trovato");

        String provinceStr = null;

        try {
            Integer idProvince = Integer.parseInt(province);
            if (idProvince != null)
                if (idProvince > 0){
                    Province p = geosvc.getProvinceById(idProvince);
                    if (p!= null)
                        provinceStr = p.getDescription();
                }

        } catch(NumberFormatException e){
            provinceStr = null;
        }

        String pathFileTessera = tessereSvc.printTesseraForWorker(workerId, sector, provinceStr);
        downloadTesseraFile(l, pathFileTessera, response);
    }


    /* Chiama il servizio per inviare la mail di richiesta info ai destinatari indicati nel form */
    public void sendRequestInfoMail(String destinatario, String content, String provinceInfo) throws Exception {

        // N.B.: 'destinatario' può anche essere una lista di destinatari divisi da ';'
        List<String> recipients = new ArrayList<>();

        recipients.addAll(Arrays.asList(destinatario.split(";")));

        MessageInput message = new MessageInput(recipients, ((User) security.getLoggedUser()).getMail(), content, provinceInfo);
        message.setSubject("Richiesta info al territorio di " + provinceInfo);

        richSvc.requireInfoWorker(message, provinceInfo);

    }


    private void downloadTesseraFile(Lavoratore lav, String pathFileTessera, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(pathFileTessera);

        response.setHeader("Content-Disposition", "attachment;filename=Tessera_" + lav.getSurname() + "_" + lav.getName() + ".zip");
        //response.setContentType("application/zip");
        response.setStatus(200);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        is.close();
        outStream.close();
    }


    private UiWorkerIscrizioniChart converToUiIscrizioniChart(Lavoratore l, List<Iscrizione> list) {
        UiWorkerIscrizioniChart result = new UiWorkerIscrizioniChart();
        result.setWorkerName(l.getNamesurname());

        if (list.size() == 0)
            return result;


        //recupero la lista degli anni e delle province legate alle iscrizioni
        result.setAnni(retrieveAnniFromIscrizioni(list));
        result.setProvinces(retrieveProvincesFromIscrizioni(list));
        //recupero la lista degli id delle province delle iscrizioni
        result.setProvincesIds(retrieveProvincesIds(result.getProvinces()));

        //recupero la lista delle province dell'utente loggato
        List<Province> provs = ((User) security.getLoggedUser()).getCompany().getProvinces();
        result.setLoggedUserProvinceIds(retrieveProvinceForLoggedUser(provs));

        result.setChartElements(convertIscrizioniToUiChartElements(list, result.getAnni(), result.getProvinces(), result.getProvincesIds()));
        return result;
    }

    private List<UiWorkerChartElement> convertIscrizioniToUiChartElements(List<Iscrizione> list, Integer[] anni, String[] provinces, Integer[] provinceIds) {
        List<UiWorkerChartElement> result = new ArrayList<>();

        for (int i = 0; i < anni.length; i++) {
            Integer anno = anni[i];

            for (int j = 0; j < provinces.length; j++) {
                String provincia = provinces[j];

                UiWorkerChartElement elem = new UiWorkerChartElement();
                elem.setX(i);
                elem.setY(j);
                elem.setValue(provinceIds[j]);

                //recupero la lista dei settori
                elem.setSettori(retrieveSettori(anno, provinceIds[j], list));

                result.add(elem);
            }

        }

        return result;
    }

    private List<String> retrieveSettori(Integer anno, Integer provincia, List<Iscrizione> list) {
        HashMap<String, Object> l = new HashMap<>();

        for (Iscrizione iscrizione : list) {
            if (iscrizione.getId_Provincia() == provincia && iscrizione.getAnno() == anno )
                if (!l.containsKey(iscrizione.getSettore()))
                    l.put(iscrizione.getSettore(), "");
        }

        String[] a = l.keySet().toArray(new String[l.keySet().size()]);
        return new ArrayList<String>(Arrays.asList(a));
    }

    private Integer[] retrieveProvincesIds( String[] provinceNames) {
//        HashMap<Integer, Object> l = new HashMap<>();
//
//        for (Iscrizione iscrizione : list) {
//            if (!l.containsKey(iscrizione.getId_Provincia()))
//                l.put(iscrizione.getId_Provincia(), "");
//        }
//
//        Integer[] a = l.keySet().toArray(new Integer[l.keySet().size()]);
//        return a;

        if (provinceNames == null)
            return new Integer[]{};
        if (provinceNames.length == 0)
            return new Integer[]{};

        Integer[] a = new Integer[provinceNames.length];
        int i = 0;
        for (String provinceName : provinceNames) {

            a[i] = geosvc.getProvinceByName(provinceName).getIid();


            i++;
        }
        return a;

    }

    private Integer[] retrieveProvinceForLoggedUser(List<Province> provs) {
        List<Integer> a =  new ArrayList<Integer>();

        for (Province prov : provs) {
            a.add(prov.getIid());
        }


        return a.toArray(new Integer[a.size()]);
    }


    private String[] retrieveProvincesFromIscrizioni(List<Iscrizione> list) {
        HashMap<String, Object> l = new HashMap<>();

        for (Iscrizione iscrizione : list) {
            if (!l.containsKey(iscrizione.getNomeProvincia()))
                l.put(iscrizione.getNomeProvincia(), "");
        }

        String[] a = l.keySet().toArray(new String[l.keySet().size()]);
        return a;

    }

    private Integer[] retrieveAnniFromIscrizioni(List<Iscrizione> list) {

        HashMap<Integer, Object> l = new HashMap<>();

        for (Iscrizione iscrizione : list) {
            if (!l.containsKey(iscrizione.getAnno()))
                l.put(iscrizione.getAnno(), "");
        }

        Integer[] a = l.keySet().toArray(new Integer[l.keySet().size()]);
        return a;




    }

    public List<UiIscrizione> getIscrizioniDetails(long id) {
        Lavoratore l = svc.getLavoratoreById(((User) security.getLoggedUser()).getLid(), id);
        if (l == null)
            return new ArrayList<>();

        List<Iscrizione> iscriziones = iscrRep.findIscrizioniByFiscalCodeWithQuoteDetailsMerge(l.getFiscalcode());

        return libFac.convertIscrizioniToUiiscrizioni(iscriziones);

    }

    public List<UiLibero> getNonIscrizioniDetails(long id) throws ParseException {
        return libFac.retrieveNonIscrizioniForWorker(id);
    }


    public AppLavoratore getAppLavoratoreDataOrCreateItIfNotExist(String fiscalCode) throws Exception {

        Lavoratore lav = svc.getLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode);
        if (lav == null)
            throw new Exception("Codice fiscale non trovato");

        //una volta ottenuto il lavoratore devo recuperare le seguenti informaizoni:
        //--Deleghe, Iscrizioni, Deleghe ini magazzino
        //segnalazioni come non iscrfitto, quote
        List<UIDelega> deleghe = delegheFacade.getAllWorkerDeleghe(lav.getLid());
        List<MagazzinoDelega> magDeleghe = getListaMagazzinoPerLavoratore(lav.getLid());
        List<Iscrizione> iscriziones = iscrRep.findIscrizioniByFiscalCode(lav.getFiscalcode());
        List<UiDettaglioQuota> versamenti = verFac.getStoricoVersamenti(lav.getLid());
        List<LiberoDbNazionale> lib = libService.retrieveLiberiForWorker(lav.getLid());


        //adesso posso creare il view model per inviare tutto all'app
        AppLavoratore l = convertLavoratoreToAppLavoratore(lav);

        //adesso posso inserire tutte le altre info
        l.setIscrizioni(convertToIscrizioniForApp(iscriziones, lav));
        l.setMagazzino(convertToAppMagazzinoDeleghe(magDeleghe, lav));
        l.setDeleghe(convertToAppDeleghe(deleghe, lav));
        l.setQuote(convertToAppQuote(versamenti, lav));
        l.setNonIscrizioni(convertToAppNonIscritti(lib, lav));

        l.setTesseraStampata(calculateIfTesseraStampata(lav));
        l.setIscrizioneCorrente(calculateIscrizioneCorrente(lav.getLid()));
        l.setStampeTessera(calculateOtherStampe(lav));
        return l;
    }

    public AppLavoratore convertLavoratoreToAppLavoratore(Lavoratore lav) {
        AppLavoratore l = new AppLavoratore();
        l.setId(String.valueOf(lav.getLid()));
        l.setMail(lav.getMail());
        l.setCap(lav.getCap());
        String cell = lav.getCellphone();
        if (cell == null)
            l.setCellulare("");
        else{
            //rimuovo tutti i caratteri
            cell = cell.replace("+39", "");
            cell = cell.replaceAll("[^\\d.]", "").replace(".","");
            if (cell.length() > 10 && cell.startsWith("39"))
                cell = cell.substring(2);

            l.setCellulare(cell);
        }
        l.setCognome(lav.getSurname());
        l.setComuneNascita(lav.getBirthPlace());
        l.setComuneResidenza(lav.getLivingCity());
        SimpleDateFormat gg = new SimpleDateFormat("dd/MM/yyyy");
        l.setDataNascita(gg.format(lav.getBirthDate()));

        l.setDataNascitaTime(lav.getBirthDate().getTime());
        l.setFiscale(lav.getFiscalcode());
        l.setId(String.valueOf(lav.getLid()));
        l.setIndirizzo(lav.getAddress());
        l.setNazione(lav.getNationality());
        l.setNome(lav.getName());
        l.setProvinciaNascita(lav.getBirthProvince());
        l.setProvinciaResidenza(lav.getLivingProvince());
        l.setSesso(lav.getSex());
        l.setTelefono(lav.getPhone());
        l.setVisible(true);
        return l;
    }

    private String[] calculateOtherStampe(Lavoratore l) {

        // Presenza altre tessere
        List<Tessera> otherTessere = tessereSvc.findOtherPrintedTessere(l.getFiscalcode(), Calendar.getInstance().get(Calendar.YEAR));
        String[] list = new String[otherTessere.size()];
        for (int i = 0; i < otherTessere.size(); i++) {
            Tessera s = otherTessere.get(i);
            Company c = (Company) companyRep.get(s.getCompanyId()).orElse(null);
            list[i] = c.getDescription();
        }
        return list;

    }

    private boolean calculateIfTesseraStampata(Lavoratore l) {
        // Presenza tessera
        Tessera tessera = tessereSvc.findPrintedTessera(l.getFiscalcode(), Calendar.getInstance().get(Calendar.YEAR));
        if (tessera != null) {
            return true;
        }
        return false;
    }

    private IscrittoLavoratore calculateIscrizioneCorrente(long lid) throws Exception {

        IscrittoAnnoInCorso iscritto = svc.checkIfIscrittoAnnoInCorso(lid);
        if (iscritto.iscritto()){
            IscrittoLavoratore l = new IscrittoLavoratore();

            if (iscritto.getSettore().equals(Sector.sector_edile))
                l.setEnte(iscritto.getEnte());
            else if (iscritto.getSettore().equals(Sector.sector_IMPIANTIFISSI))
                l.setEnte(Sector.sector_IMPIANTIFISSI);
            else
                l.setEnte(Sector.sector_inps);
            l.setAzienda(iscritto.getAzienda());
            l.setStringPeriodo(iscritto.getPeriodo());

            return l;
        }
        return null;
    }

    private List<NonIscrittoLavoratore> convertToAppNonIscritti(List<LiberoDbNazionale> lib, Lavoratore lav) {
        List<NonIscrittoLavoratore> result = new ArrayList<>();

        for (LiberoDbNazionale liberoDbNazionale : lib) {
            NonIscrittoLavoratore a = new NonIscrittoLavoratore();

            a.setAzienda(liberoDbNazionale.getCurrentAzienda());
            a.setNome(String.format("%s %s", lav.getSurname(), lav.getName()));
            a.setEnte(liberoDbNazionale.getEnte());
            a.setFiscale(lav.getFiscalcode());
            a.setIscrittoA(liberoDbNazionale.getIscrittoA());
            a.setLiberoAl(new SimpleDateFormat("dd/MM/yyyy").format(liberoDbNazionale.getLiberoAl()));
            a.setProvincia(liberoDbNazionale.getNomeProvinciaFeneal());

            result.add(a);
        }


        return result;
    }

    private List<QuotaLavoratore> convertToAppQuote(List<UiDettaglioQuota> versamenti, Lavoratore lav) {
        List<QuotaLavoratore> result = new ArrayList<>();

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        for (UiDettaglioQuota aa : versamenti) {
            QuotaLavoratore a = new QuotaLavoratore();

            a.setProvincia(aa.getProvincia());
            a.setSettore(aa.getSettore());
            a.setEnte(aa.getEnte());
            a.setAzienda(aa.getAziendaRagioneSociale());

            a.setFiscale(lav.getFiscalcode());
            a.setNome(String.format("%s %s", lav.getSurname(), lav.getName()));
            a.setCompetenza(String.format("%s - %s", f.format(aa.getDataInizio()), f.format(aa.getDataFine())));

            a.setData(f.format(aa.getDataDocumento()));
            a.setDataRegistrazione(f.format(aa.getDataRegistrazione()));

            a.setQuota(String.valueOf(aa.getQuota()));
            a.setContratto(aa.getContratto());
            a.setLivello(aa.getLivello());
            a.setTipo(aa.getTipoDocumento());

            result.add(a);
        }

        return result;
    }

    private List<DelegaLavoratore> convertToAppDeleghe(List<UIDelega> deleghe, Lavoratore lav) {
        List<DelegaLavoratore> result = new ArrayList<>();

        for (UIDelega aa : deleghe) {
            DelegaLavoratore a = new DelegaLavoratore();

            a.setId(aa.getId());
            a.setProvincia(aa.getProvince());
            a.setSettore(aa.getSector());
            a.setEnte(aa.getParitethic());
            a.setAzienda(aa.getWorkerCompany());
            a.setCausale(aa.getSubscribeReason());
            a.setCausaleAnnullamento(aa.getCancelReason());
            a.setCausaleRevoca(aa.getRevokeReason());
            a.setCollaboratore(aa.getCollaborator());
            a.setData(aa.getDocumentDate());
            a.setNote(aa.getNotes());
            a.setSendDate(aa.getSendDate());
            a.setAcceptDate(aa.getAcceptDate());
            a.setActivationDate(aa.getActivationDate());
            a.setCancelDate(aa.getCancelDate());
            a.setRevokeData(aa.getRevokeDate());
            a.setStato(aa.getStateDescription());

            a.setFiscale(lav.getFiscalcode());
            a.setNome(String.format("%s %s", lav.getSurname(), lav.getName()));



            result.add(a);
        }

        return result;
    }

    private List<MagazzinoLavoratore> convertToAppMagazzinoDeleghe(List<MagazzinoDelega> magDeleghe, Lavoratore lav) {
        List<MagazzinoLavoratore> result = new ArrayList<>();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        for (MagazzinoDelega aa : magDeleghe) {
            MagazzinoLavoratore a = new MagazzinoLavoratore();

            a.setId(String.valueOf(aa.getId()));
            a.setProvincia(aa.getProvince().getDescription());
            a.setEnte(aa.getParitethic().getDescription());
            a.setCollaboratore(aa.getCollaboratore() != null ?aa.getCollaboratore().getDescription() : "");
            a.setData(f.format(aa.getData()));
            result.add(a);
        }

        return result;
    }

    private List<IscrittoLavoratore> convertToIscrizioniForApp(List<Iscrizione> iscriziones, Lavoratore lav) {
        List<IscrittoLavoratore> result = new ArrayList<>();

        for (Iscrizione aa : iscriziones) {
            IscrittoLavoratore a = new IscrittoLavoratore();


            a.setProvincia(aa.getNomeProvincia());
            a.setSettore(aa.getSettore());
            a.setEnte(aa.getEnte());
            a.setAzienda(aa.getAzienda());


            a.setFiscale(lav.getFiscalcode());

            a.setNome(String.format("%s %s", lav.getSurname(), lav.getName()));
            a.setPeriodo(aa.getPeriodo());
            a.setAnno(aa.getAnno());
            a.setPiva(aa.getPiva());

            a.setQuota(String.valueOf(aa.getQuota()));
            a.setContratto(aa.getContratto());
            a.setLivello(aa.getLivello());

            a.setShowChevron(true);




            result.add(a);
        }

        return result;
    }


    private List<MagazzinoDelega> getListaMagazzinoPerLavoratore(long workerId){

        LoadRequest req = LoadRequest.build().filter("lavoratore", workerId);

        return magRep.find(req).getRows();

    }

    public AppLavoratore getAppLavoratoreDataOrCreateItIfNotExist(String fiscalCode, String province) throws Exception {
        Lavoratore lav = svc.getLavoratoreByFiscalCodeOrCreateItIfNotexist(fiscalCode, province);
        if (lav == null)
            throw new Exception("Codice fiscale non trovato");

        //una volta ottenuto il lavoratore devo recuperare le seguenti informaizoni:
        //--Deleghe, Iscrizioni, Deleghe ini magazzino
        //segnalazioni come non iscrfitto, quote
        List<UIDelega> deleghe = delegheFacade.getAllWorkerDeleghe(lav.getLid());
        List<MagazzinoDelega> magDeleghe = getListaMagazzinoPerLavoratore(lav.getLid());
        List<Iscrizione> iscriziones = iscrRep.findIscrizioniByFiscalCode(lav.getFiscalcode());
        List<UiDettaglioQuota> versamenti = verFac.getStoricoVersamenti(lav.getLid());
        List<LiberoDbNazionale> lib = libService.retrieveLiberiForWorker(lav.getLid());


        //adesso posso creare il view model per inviare tutto all'app
        AppLavoratore l = convertLavoratoreToAppLavoratore(lav);

        //adesso posso inserire tutte le altre info
        l.setIscrizioni(convertToIscrizioniForApp(iscriziones, lav));
        l.setMagazzino(convertToAppMagazzinoDeleghe(magDeleghe, lav));
        l.setDeleghe(convertToAppDeleghe(deleghe, lav));
        l.setQuote(convertToAppQuote(versamenti, lav));
        l.setNonIscrizioni(convertToAppNonIscritti(lib, lav));

        l.setTesseraStampata(calculateIfTesseraStampata(lav));
        l.setIscrizioneCorrente(calculateIscrizioneCorrente(lav.getLid()));
        l.setStampeTessera(calculateOtherStampe(lav));
        return l;
    }
}
