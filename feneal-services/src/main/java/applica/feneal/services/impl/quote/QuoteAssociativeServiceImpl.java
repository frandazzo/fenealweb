package applica.feneal.services.impl.quote;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.data.dbnazionale.ImportazioniRepository;
import applica.feneal.domain.data.dbnazionale.IscrizioniRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.UpdatableDettaglioQuota;
import applica.feneal.domain.model.core.quote.fenealgestImport.DettaglioQuotaDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.FirmDTO;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.domain.model.core.servizi.search.UiIqaReportSearchParams;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.feneal.domain.model.dbnazionale.Iscrizione;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
import applica.feneal.services.*;
import applica.feneal.services.impl.importData.ImportDataDeleghe;
import applica.feneal.services.impl.importData.ImportDataQuote;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.AuthenticationException;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 20/05/2016.
 */
@Service
public class QuoteAssociativeServiceImpl implements QuoteAssociativeService {

    @Autowired
    private ImportQuoteLogger logger;

    @Autowired
    private Security security;

    @Autowired
    private ImportDataDeleghe importDataDeleghe;

    @Autowired
    private IscrizioniRepository iscRep;

    @Autowired
    private ImportazioniRepository impRep;

    @Autowired
    private QuoteAssocRepository quoteRep;

    @Autowired
    private DettaglioQuoteAssociativeRepository dettRep;

    @Autowired
    private LavoratoreService lavRep;

    @Autowired
    private LavoratoriRepository lavRepository;

    @Autowired
    private AziendaService  azSvc;

    @Autowired
    private  ImportQuoteHelper importHelper;

    @Autowired
    private OptionsManager optManager;

    @Autowired
    private ApplicationOptionRepository appOptRep;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private DelegheService delServ;

    @Autowired
    private ImportDataQuote importQuote;

    @Autowired
    private GeoService geo;

    @Autowired
    private SectorRepository sectorRep;

    @Autowired
    private ParitheticRepository paritheticRepository;


    @Autowired
    private UsersRepository compRep;


    @Autowired
    private SignupDelegationReasonRepository subscribeReasonRep;


    @Override
    public String importQuote(Importazione impotazioneDbNazionale) throws Exception {
        final ErrorsCounter errors = new ErrorsCounter();

        //per prma cosa recupero l'oggetto importaizone per verificarne l'esistenza
        Importazione i = impRep.get(impotazioneDbNazionale.getIid()).orElse(null);
        if (i == null)
            throw new Exception("Importazione inesistente");


        if (impotazioneDbNazionale == null)
            throw new Exception( "Importazione non specificata");

        //adesso verifico che l'utente logato abbia tra i territori la provincia presente nell'importazione
        Company c = ((User) security.getLoggedUser()).getCompany();
        if (!c.containProvince(impotazioneDbNazionale.getNomeProvincia()))
            throw new Exception( "Importazione non permessa perchè riferita ad un territorio non proprio");

        if (impotazioneDbNazionale.isSynched())
            throw new Exception( "Importazione già sincronizzata");

        //recupero adesso la lista di tutte le iscrizioni che fanno riferimento all'importazione
        List<Iscrizione> iscrizioni = retrieveIscrizioni(impotazioneDbNazionale.getIid());
        if (iscrizioni.size() == 0)
            throw new Exception( "Nulla da importare");

        SimpleDateFormat f = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Company cc = ((User) security.getLoggedUser()).getCompany();
        String filename = String.format("%s_%s_%s", impotazioneDbNazionale.getSettore(), cc.getDescription().replace(" ", "_"), f.format(new Date()));


        //recupero l'ente, se si tratta di importazione settore edile dalla prima delle iscrizioni...
        String ente = "";
        if (impotazioneDbNazionale.getSettore().equals("EDILE"))
            ente = iscrizioni.get(0).getEnte();

        //adesso creo una struttura che mi consente di associare un id_lavoratore_db_nazionale
        // adu n lavoratore in modo da non fare più query successivamente nella creazione dei dettagli
        //delle quote associative

        //inizio con l'anagrafare i lavoartori
        ImportCache cache = new ImportCache();

        populateLavoratoriCache(iscrizioni, cache, filename);

        //anagrafo le aziedne che hanno una descrizione

        populateAziendeCache(iscrizioni, cache, filename);

        //adesso posso creare tutto in transazione un riepilogo quota associativa
        //insieme con tutti i dettagli
        RiepilogoQuoteAssociative r = RiepilogoQuoteAssociative.createFromImportazione(impotazioneDbNazionale, ente, filename, ((User) security.getLoggedUser()).getCompany().getLid());
        List<DettaglioQuotaAssociativa> quote = retrieveDettagliFromIscrizioni(iscrizioni, cache , filename);

        //adesso posso inserire tutto con un comando
        quoteRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = quoteRep.getSession();
                Transaction tx = s.beginTransaction();
                try {

                    logger.log(filename, "StartImport", "Avvio importazione dettagli quote: "  + iscrizioni.size());
                    //inserisco tutto nel db
                    s.saveOrUpdate(r);

                    //imposto la chiave esterna per ogni dettaglio
                    for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quote) {
                        dettaglioQuotaAssociativa.setIdRiepilogoQuotaAssociativa(r.getLid());
                    }

                    int j = 0;
                    for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quote) {
                        try{
                            j++;
                            logger.log(filename, "ImportData", "Importazione quota num : "  + String.valueOf(j));

                            s.saveOrUpdate(dettaglioQuotaAssociativa);
                        }catch(Exception ex){
                            errors.incrementErrorNumber();
                            ex.printStackTrace();
                            logger.log(filename, "", "Errore in importazione dettagli quote con id iscrizione " + String.valueOf(dettaglioQuotaAssociativa.getId_Iscrizione()) + " -- " + ex.getMessage() );
                        }

                    }

                    //prima di terminare imposto a synched la importaizone
                    impotazioneDbNazionale.setSynched(true);
                    s.saveOrUpdate(impotazioneDbNazionale);

                    tx.commit();
                    logger.log(filename, "EndImport", "Termine importazione iscrizioni");


                } catch (Exception ex) {
                    errors.incrementErrorNumber();
                    ex.printStackTrace();
                    try {
                        logger.log(filename, "", "Errore in importazione : " + ex.getMessage() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tx.rollback();
                } finally {
                    s.close();
                }




            }
        });


        if (errors.getErrors() == 0)
        {
            logger.log(filename, "EndAllImport", "Importazione terminata senza errori" );
            return "Importazione terminata senza errori";
        }
        logger.log(filename, "EndAllImport", "Importazione terminata con " + errors.getErrors() + " errori" );
        return "Importazione terminata con " + errors.getErrors() + " errori";
    }

    @Override
    public String importQuoteAssociativeFromDatiTerritori(int companyId, RiepilogoQuotaDTO dto) throws AuthenticationException {

        if (dto == null)
            return "";

        //definisco il nnome del file che farà il log dell'importazione
        String filename = "importData_ " + dto.getGuid() + ".txt";
        User user = null;
        try {
            logger.log(filename, "StartImport", "Ricerco le credenziali di segratario per la company: " + companyId, false);

            LoadRequest req = LoadRequest.build().filter("company", companyId);
            user = compRep.find(req).getRows().stream().filter(u -> ((Role) u.retrieveUserRole()).getRole().equals("SEGRETARIO")).findFirst().orElse(null);

            if (user == null){
                logger.log(filename, "StartImport", "Nessuna credenziale trovata", false);
                return "";
            }

            logger.log(filename, "StartImport", "Avvio il login", false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Security.manualLogin(user.getUsername(), user.getDecPass());


        try {

            logger.log(filename, "StartImport", "Avvio import num " + dto.getExportNumber(), false);
            logger.log(filename, "StartImport", "*****************************", false);
            logger.log(filename, "StartImport", "*****************************", false);
            //poichè questo servizio viene richiamato senza essere loggati imposto la secure strategy
            //con il dato proveniente dal server Fenealgest
            //secStrategy.setOwnerProvider(new NonProtectedOwnerProvider(companyId));
//
            //a questo punto è tutto pronto per iniziare l'importazione
            //la strategia è quella che verranno inviate quote associative in pacchetti da max 500 righe
            //per sfruttare il meccanimo di messa in coda del server fenealgest.
            //poichè potrebbero esserci incassi quote con migliaia di righe dovro' eseguire
            //un merge li dove il guid del riepilogo quota è lo stesso

            //pertannto la prima cosa da fare è recuperare la quota associativa che h lo stesso guid
            //ovviamente se non esiste ne creo una ex novo altrimenti la materializzo e in base al suo id
            //creo tutti i dettagli mancanto a partire dal dto

            //al termine dell'inserimento della lista dei dettagli quota verifico se sè necessario attivare le deleghe
            //per gli utenti o addirittura crearle se non esistono
            ValidateDTO(dto);





            if (StringUtils.isEmpty(dto.getGuid())){

                logger.log(filename, "Riepilogo quota con guid Nullo", "La prcedura si stoppa", false);
                return "";
            }
            logger.log(filename, "Recupero riepilogo quota", String.format("Recupero quota per esportazione num %s con responsabile " +
                    "esportazione %s ", dto.getExportNumber(), dto.getExporterMail()), false);
            RiepilogoQuoteAssociative q = getRiepilogoQuotaByGuid(dto.getGuid());
            if (q == null)
            {
                logger.log(filename, "Recupero riepilogo quota", "Quota non trovata: creo una quota dal DTO", false);
                String logFile = optManager.get("applica.fenealquote.logfolder") + filename;

                q = importHelper.createRiepilogoQuota(dto, logFile);
                quoteRep.save(q);
                logger.log(filename, "Salvataggio riepilogo quota", "Nuovo Riepilogo quote salvato", false);
            }

            //adesso non devo fare altro che creare tutti i prerequisiti
            //"Aziende" e "Lavoratori" prima di ciclare ed inserire tutte le quote
            ApplicationOptions appOpt = appOptRep.find(null).findFirst().orElse(null);
            //anagrafo le aziedne e i lavoratori
            ImportCache cache = new ImportCache();
            importLavoratori(dto, cache, filename, appOpt);
            importAziende(dto, cache, filename , appOpt);

            //recupero la lista delle quote associative a partrire cdal dto
            List<DettaglioQuotaAssociativa> quote = saveDettagliQuote(filename, q, cache, dto);

            //se devo creare le deleghe o le devo attivare ciclo su tutte le quote
            if (dto.getTipoDocumento().equals(RiepilogoQuoteAssociative.IQI)|| dto.getTipoDocumento().equals(RiepilogoQuoteAssociative.IQP)){
                logger.log(filename, "Nessuna attivazione deleghe", String.format("Poichè si tratta di documento %s non ci sarà nessuna attivazione delle deleghe", dto.getTipoDocumento()) , false);
                return "";
            }
            try{

                if (appOpt == null)
                    activateDeleghe(filename, quote, dto);
                else{
                    dto.setCreaDelegaIfNotExist(appOpt.isCreaDelegaIfNotExistDuringImport());
                    dto.setAssociaDelega(appOpt.isAssociaDelegaDuringImport());
                    activateDeleghe(filename, quote, dto);
                }
            }catch(Exception e){
                logger.log(filename, "Errore Attivazione deleghe", String.format("Si è verificato il seguente errore nella attivazione delle deleghe:  %s " , e.getMessage()) , false);
            }

        } catch (Exception e) {
            try {
                logger.log(filename, "Errore irreversibile","Errore irreveribile nella importazione dei dati: " + e.getMessage(), false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return "";



    }

    @Override
    public RiepilogoQuoteAssociative creaQuoteManuali(RiepilogoQuotaDTO dto) throws Exception {
        return importQuote.doImportQuoteManuali(dto);
    }

    private void ValidateDTO(RiepilogoQuotaDTO dto) throws Exception {
        if (StringUtils.isEmpty(dto.getSettore())){
            throw  new Exception("Settore nullo");
        }

        if (!dto.getSettore().toUpperCase().equals("EDILE") &&
                !dto.getSettore().toUpperCase().equals("IMPIANTI FISSI") &&
                !dto.getSettore().toUpperCase().equals("INPS")){
            throw  new Exception("Settore non riconosciuto");
        }

        if (dto.getSettore().toUpperCase().equals("IMPIANTI FISSI") ||
                dto.getSettore().toUpperCase().equals("INPS")){
            dto.setEnte("");
        }

        if (dto.getSettore().toUpperCase().equals("EDILE")){
            if (StringUtils.isEmpty(dto.getEnte()))
                throw new Exception("Ente nullo");

            if (!dto.getEnte().toUpperCase().equals(Paritethic.ente_calec) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cassaedile) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cea) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cec) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_ceda) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cedaf) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cedaiier) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cedam) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_ceva) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_celcof) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cassaedile) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_cert) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_edilcassa) &&
                    !dto.getEnte().toUpperCase().equals(Paritethic.ente_falea)) {
                throw new Exception("Ente non riconosciuto");
            }
        }

        if (dto.getDataDocumento() == null)
            throw new Exception("Data documento non impostata");
        if (dto.getDataRegistrazione() == null)
            throw new Exception("Data registrazione non impostata");
        if (StringUtils.isEmpty(dto.getTipoDocumento())){
            throw new Exception("Tipo documento non impostato");
        }

        if (!dto.getTipoDocumento().toUpperCase().equals("IQA") &&
                !dto.getTipoDocumento().toUpperCase().equals("IQP") &&
                !dto.getTipoDocumento().toUpperCase().equals("IQI")){
            throw new Exception("Tipo documento non riconosciuto");
        }

        if (dto.getTipoDocumento().toUpperCase().equals("IQA"))
            if (dto.getSettore().toUpperCase().equals("INPS"))
                throw new Exception("Non posso avere un tipo documento IQA per il settore inps");

        if (dto.getTipoDocumento().toUpperCase().equals("IQI"))
            if (!dto.getSettore().toUpperCase().equals("INPS"))
                throw new Exception("Non posso avere un tipo documento IQI per un settore diverso inps");

        if (dto.getTipoDocumento().toUpperCase().equals("IQP"))
            if (!dto.getSettore().toUpperCase().equals("EDILE"))
                throw new Exception("Quote previsionali ammesse solo per il settore edile!");


        if (StringUtils.isEmpty(dto.getProvincia())){
            throw new Exception("Provincia non impostata");
        }

        if (geo.getProvinceByName(dto.getProvincia()) == null){
            throw new Exception("Provincia non riconosciuta");
        }


        //adesso verifico che ogni item se si tratta di impianti fissi deve avere una azienda
        for (DettaglioQuotaDTO dettaglioQuotaDTO : dto.getDettagli()) {
            if (dto.getSettore().toUpperCase().equals("IMPIANTI FISSI")){
                //siccome il db nazionale ammette la possibilità di inserire impianti fissi
                //senza specificare lazienda allora ne  creo una fittizia nel caso sia assenter
                if (dettaglioQuotaDTO.getFirm() == null){
                    dettaglioQuotaDTO.setFirm(new FirmDTO());
                    dettaglioQuotaDTO.getFirm().setDescription("AZIENDA NON SPECIFICATA");
                }


                if (StringUtils.isEmpty(dettaglioQuotaDTO.getFirm().getDescription())){
                    dettaglioQuotaDTO.getFirm().setDescription("AZIENDA NON SPECIFICATA");
                }
            }
        }

        //pulisco le aziende se si tratta di inps
        for (DettaglioQuotaDTO dettaglioQuotaDTO : dto.getDettagli()) {
            if (dto.getTipoDocumento().toUpperCase().equals("IQI")){
                dettaglioQuotaDTO.setFirm(null);
            }
        }
    }

    public List<DettaglioQuotaAssociativa> saveDettagliQuote(String filename, RiepilogoQuoteAssociative q, ImportCache cache, RiepilogoQuotaDTO dto) throws Exception {
        logger.log(filename, "Recupero lista quote", "Recupero la lista dei dettagli quote associative a partrire dal dto", false);
        List<DettaglioQuotaAssociativa> quote = retrieveDettagliFroDtos(dto, cache);
        logger.log(filename, "Recupero lista quote", "Lista dei dettagli quote associative recuperata con " + quote.size() + " righe", false);

        int j = 0;
        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quote) {
            try{
                j++;
                logger.log(filename, "ImportData", "Importazione dettaglio quota num : "  + String.valueOf(j), false);
                dettaglioQuotaAssociativa.setIdRiepilogoQuotaAssociativa(q.getLid());
                dettRep.save(dettaglioQuotaAssociativa);
            }catch(Exception ex){

                ex.printStackTrace();
                logger.log(filename, "Errore Importazione dettaglio quota", String.format("Errore in importazione dettagli quote num %s. Errore: %s", String.valueOf(j), ex.getMessage() ) , false);
            }

        }
        return quote;
    }

    public void activateDeleghe(String filename, List<DettaglioQuotaAssociativa> quote, RiepilogoQuotaDTO dto) throws Exception {
        logger.log(filename, "Attivazione deleghe", String.format("Avvio procedura attivazione delle deleghe") , false);
        if (dto.isCreaDelegaIfNotExist() || dto.isAssociaDelega()){
            logger.log(filename, "Attivazione deleghe", String.format("Avvio procedura attivazione delle deleghe con i seguenti parametri:" +
                    "CreaDelegaIfNotExist = %s; --" +
                    "AssociaDelega = %s; --",String.valueOf(dto.isCreaDelegaIfNotExist()), String.valueOf(dto.isAssociaDelega())) , false);

            int jj = 0;
            for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quote) {
                //recupero le deleghe
                Delega d =delServ.retrieveActivableWorkerDelega(dettaglioQuotaAssociativa.getIdLavoratore(),geo.getProvinceByName( dto.getProvincia()).getIid(),
                        dto.getSettore().toUpperCase(), dto.getEnte().toUpperCase(), dettaglioQuotaAssociativa.getIdAzienda() );

                logger.log(filename, "Attivazione delega", "Attivazione delega per dettaglio quota num " + String.valueOf(jj) + " per id lavoratore : "  + dettaglioQuotaAssociativa.getIdLavoratore(), false);
                if (dettaglioQuotaAssociativa.getIdLavoratore() > 0){
                    if (d == null){
                        logger.log(filename, "Attivazione delega", "Delega attivabile non trovata!", false);
                        if (dto.isCreaDelegaIfNotExist()){
                            logger.log(filename, "Attivazione delega", "Creazione delega nuova!", false);
                            //creo la delega
                            d = CreateDelega(dettaglioQuotaAssociativa, dto);
                            delServ.saveOrUpdate(0, d);
                        }else{
                            logger.log(filename, "Attivazione delega", "Nessuna creazione delega poichè il flag CreaDelegaIfNotExist è falso!", false);
                        }
                    }else{
                        logger.log(filename, "Attivazione delega", "Delega attivabile trovata. Si procede alla attivazione se il flag Activate Delega è impostato a true", false);
                    }



                    if (dto.isAssociaDelega()){
                        if (d != null){
                            //la attivo

                            //ho adesso una potenziale delega da attivare
                            //se è attivata non faccio nulla
                            //se è accettata attivo
                            //se è inoltrata o sottoscritta prima accetto e poi attivo


                            if (d.getState() == Delega.state_subscribe || d.getState() == Delega.state_sent){
                                logger.log(filename, "Attivazione delega", "Attivo delega sottoscritta o inoltrata previa accettazione", false);
                                delServ.acceptDelega(new Date(), d);
                                delServ.activateDelega(new Date(), d);
                            }else if (d.getState() == Delega.state_accepted ){
                                logger.log(filename, "Attivazione delega", "Attivo delega accettata", false);
                                delServ.activateDelega(new Date(), d);
                            }else{
                                logger.log(filename, "Attivazione delega", "La delega è gia attva", false);
                            }


                        }else{
                            logger.log(filename, "Attivazione delega", "Non si procede alla attivazione poichè non ci sono deleghe attivabili", false);
                        }
                    }else{
                        logger.log(filename, "Attivazione delega", "Non si procede alla attivazione poichè il flag Activate Delega è impostato a false", false);
                    }
                }else{
                    logger.log(filename, "Attivazione delega", "Nessuna operazione eseguita poichè non esiste l'id del lavoratore", false);
                }

                jj++;
            }
        }
    }

    public Delega CreateDelega(DettaglioQuotaAssociativa dettaglioQuotaAssociativa, RiepilogoQuotaDTO dto) {
        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(new Date());
        d.setImportGuid(dto.getGuid());
        Lavoratore l = lavRep.getLavoratoreById(0,dettaglioQuotaAssociativa.getIdLavoratore());
        if (l != null){
            d.setWorker(l);
            d.setProvince(geo.getProvinceByName(dto.getProvincia()));
            //settore
            try {
                LoadRequest req = LoadRequest.build().filter("type", dettaglioQuotaAssociativa.getSettore());
                d.setSector(sectorRep.find(req).findFirst().orElse(null));
            }catch (Exception e) {
                e.printStackTrace();
            }

            if (org.springframework.util.StringUtils.hasLength(dettaglioQuotaAssociativa.getEnte())) {
                try {
                    LoadRequest req = LoadRequest.build().filter("type", dettaglioQuotaAssociativa.getEnte());
                    d.setParitethic(paritheticRepository.find(req).findFirst().orElse(null));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (dettaglioQuotaAssociativa.getIdAzienda() > 0) {
                try {
                    d.setWorkerCompany(azRep.get(dettaglioQuotaAssociativa.getIdAzienda()).orElse(null));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }


            try {


                LoadRequest req = LoadRequest.build().filter("description", "RIPRESA DATI");
                CausaleIscrizioneDelega ds = subscribeReasonRep.find(req).findFirst().orElse(null);

                if (ds == null)
                {
                    ds = new CausaleIscrizioneDelega();
                    ds.setDescription("RIPRESA DATI");
                    subscribeReasonRep.save(ds);
                }

                d.setSubscribeReason(ds);
            }catch (Exception e) {
                e.printStackTrace();
            }








        }
        return d;
    }

    private List<DettaglioQuotaAssociativa> retrieveDettagliFroDtos(RiepilogoQuotaDTO dto, ImportCache cache) {

        List<DettaglioQuotaAssociativa> result = new ArrayList<>();

        for (DettaglioQuotaDTO dettaglioQuotaDTO : dto.getDettagli()) {

            DettaglioQuotaAssociativa dett = new DettaglioQuotaAssociativa();

            dett.setDataRegistrazione(dto.getDataRegistrazione());
            dett.setDataInizio(dettaglioQuotaDTO.getDataInizio());
            dett.setDataFine(dettaglioQuotaDTO.getDataFine());
            dett.setDataDocumento(dto.getDataDocumento());
            dett.setContratto(dettaglioQuotaDTO.getContratto());
//
//            if (dettaglioQuotaDTO.getEnte() != null)
//                dett.setEnte(dettaglioQuotaDTO.getEnte().toUpperCase());
//            else
            dett.setEnte(dto.getEnte().toUpperCase());

            dett.setLivello(dettaglioQuotaDTO.getLivello());
            dett.setNote(dettaglioQuotaDTO.getNote());

//            if (dettaglioQuotaDTO.getProvincia() != null)
//                dett.setProvincia(dettaglioQuotaDTO.getProvincia().toUpperCase());
//            else
            dett.setProvincia(dto.getProvincia().toUpperCase());
            dett.setQuota(dettaglioQuotaDTO.getQuota());

//            if (dettaglioQuotaDTO.getTipoDocumento() != null)
//                dett.setTipoDocumento(dettaglioQuotaDTO.getTipoDocumento().toUpperCase());
//            else
            dett.setTipoDocumento(dto.getTipoDocumento().toUpperCase());

//            if (dettaglioQuotaDTO.getSettore() != null)
//                dett.setSettore(dettaglioQuotaDTO.getSettore().toUpperCase());
//            else
            dett.setSettore(dto.getSettore().toUpperCase());

            if (dettaglioQuotaDTO.getTipoPrestazione() != null)
                dett.setTipoPrestazione(dettaglioQuotaDTO.getTipoPrestazione().toUpperCase());

            Azienda a = null;
            if (dettaglioQuotaDTO.getFirm() != null){
                if (!StringUtils.isEmpty(dettaglioQuotaDTO.getFirm().getDescription()))
                    a = cache.getAziende().get(dettaglioQuotaDTO.getFirm().getDescription());
            }

            if (a != null)
                dett.setIdAzienda(a.getLid());

            Lavoratore la = cache.getLavoratoriFiscalCode().get(dettaglioQuotaDTO.getWorker().getFiscalcode());
            if (la != null){
                dett.setIdLavoratore(la.getLid());
                result.add(dett);
            }

        }

        return result;
    }

    private void importAziende(RiepilogoQuotaDTO dto, ImportCache cache, String filename, ApplicationOptions appOpt) throws Exception {
        logger.log(filename, "StartAnagraficheAz", "Avvio importazione anagrafiche aziende", false);
        int i = 0;
        for (DettaglioQuotaDTO iscrizione : dto.getDettagli()) {
            if (iscrizione.getFirm() != null){
                String azienda = iscrizione.getFirm().getDescription();
                if(!StringUtils.isEmpty(azienda)){
                    if (!StringUtils.isEmpty(azienda.trim())){

                        if (!cache.getAziende().containsKey(azienda.trim())){
                            try {
                                i++;
                                logger.log(filename, "AnagraficheAz", "Inserimento anagrafica num. " + String.valueOf(i) + " per azienda " + iscrizione.getFirm().getDescription(), false );

                                Azienda a = azSvc.getAziendaByDescription(iscrizione.getFirm().getDescription());
                                if (a == null)
                                {
                                    a = new Azienda();
                                    a.setDescription(iscrizione.getFirm().getDescription());
                                    a.setCity(iscrizione.getFirm().getCity());

                                    a.setAddress(iscrizione.getFirm().getAddress());
                                    a.setCap(iscrizione.getFirm().getCap());
                                    a.setPhone(iscrizione.getFirm().getPhone());
                                    a.setPiva(iscrizione.getFirm().getPiva());
                                    if (!StringUtils.isEmpty(iscrizione.getFirm().getCity())) {
                                        City c = geo.getCityByName(iscrizione.getFirm().getCity());

                                        if (c != null)
                                        {
                                            Province cc = geo.getProvinceById(c.getIdProvince());
                                            if (cc != null)
                                                a.setProvince(cc.getDescription());

                                        }

                                    }
                                    azSvc.saveOrUpdate(0, a);
                                }else{
                                    if (dto.isUpdateFirmas()){

                                        boolean executeUpdate = true;
                                        if (appOpt == null)
                                            executeUpdate = true;
                                        else if (appOpt.isUpdateFirmasDuringImport())
                                            executeUpdate = true;
                                        else
                                            executeUpdate = false;

                                        if (executeUpdate) {
                                            a.setCity(iscrizione.getFirm().getCity());
                                            a.setAddress(iscrizione.getFirm().getAddress());
                                            a.setCap(iscrizione.getFirm().getCap());
                                            a.setPhone(iscrizione.getFirm().getPhone());
                                            a.setPiva(iscrizione.getFirm().getPiva());
                                            if (!StringUtils.isEmpty(iscrizione.getFirm().getCity())) {
                                                City c = geo.getCityByName(iscrizione.getFirm().getCity());

                                                if (c != null)
                                                {
                                                    Province cc = geo.getProvinceById(c.getIdProvince());
                                                    if (cc != null)
                                                        a.setProvince(cc.getDescription());

                                                }

                                            }
                                            azSvc.saveOrUpdate(0, a);
                                        }
                                    }
                                }
                                cache.getAziende().put(iscrizione.getFirm().getDescription(), a);

                            } catch (Exception e) {
                                logger.log(filename, "", "Errore nell'inserimento dell'azienda: " + iscrizione.getFirm().getDescription() + " -- " + e.getMessage(), false);
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }
        logger.log(filename, "EndAnagraficheAz", "Termine importazione anagrafiche aziende", false);

    }

    private void importLavoratori(RiepilogoQuotaDTO dto, ImportCache cache, String filename, ApplicationOptions appOpt) throws Exception {
        logger.log(filename, "StartAnagraficheLav", "Avvio importazione anagrafiche lavoratori", false);
        int i = 0;
        for (DettaglioQuotaDTO iscrizione : dto.getDettagli()) {
            i++;
            logger.log(filename, "AnagraficheLav", "Inserimento anagrafica num. " + String.valueOf(i) + " per il lavoratore " + iscrizione.getWorker().getFiscalcode() + " -- " + iscrizione.getWorker().getName() + " " + iscrizione.getWorker().getSurname(), false);
            try{
                if (!cache.getLavoratoriFiscalCode().containsKey(iscrizione.getWorker().getFiscalcode()))
                {
                    LoadRequest req = LoadRequest.build().filter("fiscalcode", iscrizione.getWorker().getFiscalcode());
                    Lavoratore lav = lavRepository.find(req).findFirst().orElse(null);
                    if (lav == null){
                        //lo creo e lo inserisco nella cache
                        lav = new Lavoratore();
                        lav.setSex(Lavoratore.MALE);
                        lav.setNationality(iscrizione.getWorker().getNationality());
                        lav.setBirthDate(iscrizione.getWorker().getBirthDate());
                        lav.setLivingCity(iscrizione.getWorker().getLivingPlace());
                        lav.setBirthPlace(iscrizione.getWorker().getBirthPlace());
                        if (!StringUtils.isEmpty(iscrizione.getWorker().getLivingPlace())){
                            City cc = geo.getCityByName(iscrizione.getWorker().getLivingPlace());
                            if (cc != null)
                            {
                                Province cc1 = geo.getProvinceById(cc.getIdProvince());
                                if (cc1!=null)
                                    lav.setLivingProvince(cc1.getDescription());
                            }
                        }
                        if (!StringUtils.isEmpty(iscrizione.getWorker().getBirthPlace())){
                            City cc = geo.getCityByName(iscrizione.getWorker().getBirthPlace());
                            if (cc != null)
                            {
                                Province cc1 = geo.getProvinceById(cc.getIdProvince());
                                if (cc1!=null)
                                    lav.setBirthProvince(cc1.getDescription());
                            }
                        }
                        lav.setFiscalcode(iscrizione.getWorker().getFiscalcode());
                        lav.setName(iscrizione.getWorker().getName());
                        lav.setSurname(iscrizione.getWorker().getSurname());
                        lav.setAddress(iscrizione.getWorker().getAddress());
                        lav.setCap(iscrizione.getWorker().getCap());
                        lav.setCellphone(iscrizione.getWorker().getPhone());
                        lav.setPhone(iscrizione.getWorker().getPhone2());
                        lav.setCe(iscrizione.getWorker().getCE());
                        lav.setEc(iscrizione.getWorker().getEC());
                        lavRep.saveOrUpdate(0, lav);

                    }else{
                        if (dto.isUpdateWorkers()){

                            boolean executeUpdate = true;
                            if (appOpt == null)
                                executeUpdate = true;
                            else if (appOpt.isUpdateWorkersDuringImport())
                                executeUpdate = true;
                            else
                                executeUpdate = false;

                            if (executeUpdate){
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getNationality()))
                                    lav.setNationality(iscrizione.getWorker().getNationality());
                                if (iscrizione.getWorker().getBirthDate() != null)
                                    lav.setBirthDate(iscrizione.getWorker().getBirthDate());
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getLivingPlace()))
                                    lav.setLivingCity(iscrizione.getWorker().getLivingPlace());
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getBirthPlace()))
                                    lav.setBirthPlace(iscrizione.getWorker().getBirthPlace());


                                if (!StringUtils.isEmpty(iscrizione.getWorker().getLivingPlace())){
                                    City cc = geo.getCityByName(iscrizione.getWorker().getLivingPlace());
                                    if (cc != null)
                                    {
                                        Province cc1 = geo.getProvinceById(cc.getIdProvince());
                                        if (cc1!=null)
                                            lav.setLivingProvince(cc1.getDescription());
                                    }
                                }
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getBirthPlace())){
                                    City cc = geo.getCityByName(iscrizione.getWorker().getBirthPlace());
                                    if (cc != null)
                                    {
                                        Province cc1 = geo.getProvinceById(cc.getIdProvince());
                                        if (cc1!=null)
                                            lav.setBirthProvince(cc1.getDescription());
                                    }
                                }
                                lav.setName(iscrizione.getWorker().getName());
                                lav.setSurname(iscrizione.getWorker().getSurname());
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getAddress()))
                                    lav.setAddress(iscrizione.getWorker().getAddress());

                                if (!StringUtils.isEmpty(iscrizione.getWorker().getCap()))
                                    lav.setCap(iscrizione.getWorker().getCap());

                                if (!StringUtils.isEmpty(iscrizione.getWorker().getPhone()))
                                    lav.setCellphone(iscrizione.getWorker().getPhone());
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getPhone2()))
                                    lav.setPhone(iscrizione.getWorker().getPhone2());
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getCE()))
                                    lav.setCe(iscrizione.getWorker().getCE());
                                if (!StringUtils.isEmpty(iscrizione.getWorker().getEC()))
                                    lav.setEc(iscrizione.getWorker().getEC());
                                lavRep.saveOrUpdate(0, lav);
                            }



                        }
                    }

                    cache.getLavoratoriFiscalCode().put(lav.getFiscalcode(), lav);
                }

            }catch(Exception ex){
                logger.log(filename, "", "Errore nell'inserimento del lavoratore: " + iscrizione.getWorker().getFiscalcode() + " -- " + ex.getMessage(), false);
                ex.printStackTrace();

            }


        }
        logger.log(filename, "EndAnagraficheLav", "Termine importazione anagrafiche lavoratori", false);
    }

    private List<DettaglioQuotaAssociativa> retrieveDettagliFromIscrizioni(List<Iscrizione> iscrizioni, ImportCache cache, String logFile) throws Exception {
        List<DettaglioQuotaAssociativa> result = new ArrayList<>();
        int i = 0;
        for (Iscrizione iscrizione : iscrizioni) {
            try{
                DettaglioQuotaAssociativa q = DettaglioQuotaAssociativa.createFromIscrizione(iscrizione, cache.getLavoratori().get(iscrizione.getId_Lavoratore()), cache.getAziende().get(iscrizione.getAzienda()), ((User) security.getLoggedUser()).getCompany().getLid());
                result.add(q);
                i++;
            }catch(Exception ex){
                logger.log(logFile, "", ex.getMessage() );
            }

        }
        return result;
    }

    private void populateAziendeCache(List<Iscrizione> iscrizioni, ImportCache cache, String logFile) throws Exception {
        logger.log(logFile, "StartAnagraficheAz", "Avvio importazione anagrafiche aziende");
        int i = 0;
        for (Iscrizione iscrizione : iscrizioni) {
            String azienda = iscrizione.getAzienda();
            if(!StringUtils.isEmpty(azienda)){
                if (!StringUtils.isEmpty(azienda.trim())){

                    if (!cache.getAziende().containsKey(azienda.trim())){
                        try {
                            i++;
                            logger.log(logFile, "AnagraficheAz", "Inserimento anagrafica num. " + String.valueOf(i) + " per azienda " + iscrizione.getAzienda() );
                            cache.getAziende().put(iscrizione.getAzienda(), azSvc.getAziendaByDescriptionorCreateIfNotExist(iscrizione.getAzienda()));
                        } catch (Exception e) {
                            logger.log(logFile, "", "Errore nell'inserimento dell'azienda: " + iscrizione.getAzienda() + " -- " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        logger.log(logFile, "EndAnagraficheAz", "Termine importazione anagrafiche aziende");
    }

    private void populateLavoratoriCache(List<Iscrizione> iscrizioni, ImportCache cache, String logFile) throws Exception {
        logger.log(logFile, "StartAnagraficheLav", "Avvio importazione anagrafiche lavoratori");
        int i = 0;
        for (Iscrizione iscrizione : iscrizioni) {
            if (!cache.getLavoratori().containsKey(iscrizione.getId_Lavoratore()))
                try {
                    i++;
                    logger.log(logFile, "AnagraficheLav", "Inserimento anagrafica num. " + String.valueOf(i) + " per id_lavoratore " + iscrizione.getId_Lavoratore() + " -- " + iscrizione.getNomeCompleto());
                    cache.getLavoratori().put(iscrizione.getId_Lavoratore(), lavRep.getLavoratoreByRemoteIdOrCreateItIfNotexist(iscrizione.getId_Lavoratore()));
                } catch (Exception e) {
                    logger.log(logFile, "", "Errore nell'inserimento del lavoratore: " + iscrizione.getNomeCompleto() + " -- " + e.getMessage());
                    e.printStackTrace();
                }
        }
        logger.log(logFile, "EndAnagraficheLav", "Termine importazione anagrafiche lavoratori");
    }

    private List<Iscrizione> retrieveIscrizioni(int idImportazione) {

        LoadRequest req = LoadRequest.build().filter("id_Importazione", idImportazione);
        return iscRep.find(req).getRows();

    }

    @Override
    public void duplicaQuota(long quotaId, Date inizio, Date fine) {
        RiepilogoQuoteAssociative quotaDaDuplicare = quoteRep.get(quotaId).orElse(null);
        List<DettaglioQuotaAssociativa> quoteDaDuplicare = this.getDettagliQuota(quotaId,null);

        if (inizio.getTime() > fine.getTime()){
            Date temp = fine;
            fine = inizio;
            inizio = temp;

        }
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        String competenza = String.format("%s - %s",f.format(inizio), f.format(fine));
        //adesso rimuovo tutti gli id
        quotaDaDuplicare.setId(null);
        quotaDaDuplicare.setCompetenza(competenza);
        quotaDaDuplicare.setImportedLogFilePath("");
        quotaDaDuplicare.setOriginalFileServerPath("");
        quotaDaDuplicare.setXmlFileServerPath("");

        Date d = new Date();
        quotaDaDuplicare.setDataRegistrazione(d);
        quotaDaDuplicare.setDataDocumento(inizio);

        quoteRep.save(quotaDaDuplicare);

        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quoteDaDuplicare) {
            dettaglioQuotaAssociativa.setId(null);
            dettaglioQuotaAssociativa.setIdRiepilogoQuotaAssociativa(-1);
            dettaglioQuotaAssociativa.setDataInizio(inizio);
            dettaglioQuotaAssociativa.setDataFine(fine);
            dettaglioQuotaAssociativa.setDataRegistrazione(d);
            dettaglioQuotaAssociativa.setDataDocumento(inizio);
            dettaglioQuotaAssociativa.setIdRiepilogoQuotaAssociativa(quotaDaDuplicare.getLid());
            dettRep.save(dettaglioQuotaAssociativa);
        }

    }

    @Override
    public List<DettaglioQuotaAssociativa> retrieveQuote(UiIqaReportSearchParams params) {

        Date dateDocFrom = (StringUtils.isEmpty(params.getDataDocfromMonthReport()) || StringUtils.isEmpty(params.getDataDocfromYearReport()))? null : createDate(1, params.getDataDocfromMonthReport(), params.getDataDocfromYearReport());
        Date dateDocTo = (StringUtils.isEmpty(params.getDataDoctoMonthReport()) || StringUtils.isEmpty(params.getDataDoctoYearReport()))? null : createDate(0, params.getDataDoctoMonthReport(), params.getDataDoctoYearReport());
        Date competenceFrom = (StringUtils.isEmpty(params.getCompetencefromMonthReport()) || StringUtils.isEmpty(params.getCompetencefromYearReport()))? null : createDate(1, params.getCompetencefromMonthReport(), params.getCompetencefromYearReport());
        Date competenceTo = (StringUtils.isEmpty(params.getCompetencetoMonthReport()) || StringUtils.isEmpty(params.getCompetencetoYearReport()))? null : createDate(0, params.getCompetencetoMonthReport(), params.getCompetencetoYearReport());
        String province = params.getProvince();
        String typeQuoteCash = params.getTypeQuoteCash();
        String settore =  params.getSector();
        String ente = Sector.sector_edile.equals(settore) ? params.getParithetic() : null;
        String azienda = params.getFirm();


        //posso adesso fare la query

        LoadRequest req = LoadRequest.build();

        if (dateDocFrom != null) {
            Filter f1 = new Filter("dataDocumento", dateDocFrom, Filter.GTE);
            req.getFilters().add(f1);
        }

        if (dateDocTo != null) {
            Filter f2 = new Filter("dataDocumento", dateDocTo, Filter.LTE);
            req.getFilters().add(f2);
        }


        // filtro sulla data di competenza con i vari casi

        //        Disjunction d = new Disjunction();
//        List<Filter> orFilters = new ArrayList<>();
//
//        Conjunction c1 = new Conjunction();
//        List<Filter> andFilters1 = new ArrayList<>();
//
//        if (dateFrom != null) {
//            Filter f1 = new Filter("dataInizio", dateFrom, Filter.GTE);
//            andFilters1.add(f1);
//        }
//
//        if (dateTo != null) {
//            Filter f2 = new Filter("dataFine", dateTo, Filter.LTE);
//            andFilters1.add(f2);
//        }
//        c1.setChildren(andFilters1);
//        orFilters.add(c1);
//
//        Conjunction c2 = new Conjunction();
//        List<Filter> andFilters2 = new ArrayList<>();
//
//        if (dateFrom != null) {
//            Filter f1 = new Filter("dataInizio", dateFrom, Filter.LTE);
//            andFilters2.add(f1);
//        }
//
//        if (dateTo != null) {
//            Filter f2 = new Filter("dataInizio", dateTo, Filter.GTE);
//            andFilters2.add(f2);
//        }
//        c2.setChildren(andFilters2);
//        orFilters.add(c2);
//
//
//        Conjunction c3 = new Conjunction();
//        List<Filter> andFilters3 = new ArrayList<>();
//
//        if (dateFrom != null) {
//            Filter f1 = new Filter("dataFine", dateFrom, Filter.LTE);
//            andFilters3.add(f1);
//        }
//
//        if (dateTo != null) {
//            Filter f2 = new Filter("dataFine", dateTo, Filter.GTE);
//            andFilters3.add(f2);
//        }
//        c3.setChildren(andFilters3);
//        orFilters.add(c3);
//
//
//        d.setChildren(orFilters);
//        req.getFilters().add(d);

        if (competenceFrom != null && competenceTo != null) {
            if (!competenceFrom.before(competenceTo)){
                Date dd = competenceTo;
                competenceTo = competenceFrom;
                competenceFrom = dd;
            }
        }

        req.getFilters().add(new Filter("dataInizio", competenceTo, Filter.LTE));
        req.getFilters().add(new Filter("dataFine", competenceFrom, Filter.GTE));


        if (!StringUtils.isEmpty(province)){
            Filter f5 = new Filter("provincia", province, Filter.LIKE);
            req.getFilters().add(f5);
        }

        if (!StringUtils.isEmpty(typeQuoteCash)){
            Filter f6 = new Filter("tipoDocumento", typeQuoteCash, Filter.EQ);
            req.getFilters().add(f6);
        }

        // Solo se il tipo di quota non è quello INPS (IQI) tengo conto dell'eventuale campo di ricerca Settore, Ente, Azienda
        if (StringUtils.isEmpty(typeQuoteCash) || (!StringUtils.isEmpty(typeQuoteCash) && !RiepilogoQuoteAssociative.IQI.equals(typeQuoteCash))) {

            if (!StringUtils.isEmpty(settore)) {
                Filter f7 = new Filter("settore", settore, Filter.EQ);
                req.getFilters().add(f7);
            }

            if (ente != null) {
                if (!StringUtils.isEmpty(ente)) {
                    Filter f8 = new Filter("ente", ente, Filter.EQ);
                    req.getFilters().add(f8);
                }
            }

            if (!StringUtils.isEmpty(azienda)) {
                Filter f9 = new Filter("idAzienda", azienda, Filter.EQ);
                req.getFilters().add(f9);
            }
        }

        List<DettaglioQuotaAssociativa> quote = dettRep.find(req).getRows();

        return quote;
    }

    @Override
    public void deleteQuota(long idRiepilogoQuota) {

        //ottengo l quota
        RiepilogoQuoteAssociative r = quoteRep.get(idRiepilogoQuota).orElse(null);
        if (r != null){
            //procedo alla cancellazione
            quoteRep.executeCommand(new Command() {
                @Override
                public void execute() {
                    Session s = quoteRep.getSession();
                    Transaction tx = s.beginTransaction();
                    try {
                        //rimuovo tutti i dettagli per una determinata quota
                        s.createSQLQuery(String.format("Delete from fenealweb_dettaglioquote where idRiepilogoQuotaAssociativa = %d", idRiepilogoQuota)).executeUpdate();

                        s.createSQLQuery(String.format("Delete from fenealweb_riepilogoQuoteAssociative where id = %d", idRiepilogoQuota)).executeUpdate();



                        tx.commit();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        tx.rollback();
                    } finally {
                        s.close();
                    }
                }
            });
        }


    }


    @Override
    public Importazione getImportazioneById(int id) {
        return impRep.get(id).orElse(null);
    }

    @Override
    public List<DettaglioQuotaAssociativa> getStoricoVersamenti(long workerId) {

        return  dettRep.find(LoadRequest.build().filter("idLavoratore", workerId)).getRows();

//        List<DettaglioQuotaAssociativa> dettQuoteLavoratoriSenzaDuplicati = new ArrayList<>();
//        for (DettaglioQuotaAssociativa dett : dettagliQuoteWorker) {
//
//            if (dettQuoteLavoratoriSenzaDuplicati.stream().filter(q -> q.getIdLavoratore() == dett.getIdLavoratore()).count() == 0)
//                dettQuoteLavoratoriSenzaDuplicati.add(dett);
//        }
//
//        return dettQuoteLavoratoriSenzaDuplicati;
    }


    public List<DettaglioQuotaAssociativa> getDettagliQuota(long idRiepilogoQuota, Long idWorker) {

        LoadRequest req = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", idRiepilogoQuota).filter("idLavoratore", idWorker);
        return dettRep.find(req).getRows();

    }

    @Override
    public RiepilogoQuoteAssociative getRiepilogoQuotaById(long id) {
        RiepilogoQuoteAssociative r = quoteRep.get(id).orElse(null);

        return r;
    }

    @Override
    public RiepilogoQuoteAssociative getRiepilogoQuotaByGuid(String id) {
        LoadRequest req = LoadRequest.build().filter("guid", id);
        return quoteRep.find(req).findFirst().orElse(null);
    }

    @Override
    public String importLiberiFromDatiTerritori(int companyId, RiepilogoQuotaDTO dto) throws AuthenticationException {
        if (dto == null)
            return "";

        SimpleDateFormat f = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        //definisco il nnome del file che farà il log dell'importazione
        String filename = "importLiberi_" + f.format(new Date()) + ".txt";
        User user = null;
        try {
            logger.log(filename, "StartImport", "Ricerco le credenziali di segratario per la company: " + companyId, false);

            LoadRequest req = LoadRequest.build().filter("company", companyId);
            user = compRep.find(req).getRows().stream().filter(u -> ((Role) u.retrieveUserRole()).getRole().equals("SEGRETARIO")).findFirst().orElse(null);

            if (user == null){
                logger.log(filename, "StartImport", "Nessuna credenziale trovata", false);
                return "";
            }

            logger.log(filename, "StartImport", "Avvio il login", false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Security.manualLogin(user.getUsername(), user.getDecPass());


        try {

            logger.log(filename, "StartImport", "Avvio import liberi ", false);
            logger.log(filename, "StartImport", "*****************************", false);
            logger.log(filename, "StartImport", "*****************************", false);



            //adesso non devo fare altro che creare tutti i prerequisiti
            //"Aziende" e "Lavoratori" prima di ciclare ed inserire tutte le quote
            ApplicationOptions appOpt = appOptRep.find(null).findFirst().orElse(null);
            //anagrafo le aziedne e i lavoratori

            //se si tratta di bolzano disabilito l'aggiornamento degli utenti
            if (user.getCompany().containProvince("Bolzano")){
                if (appOpt != null)
                    appOpt.setUpdateWorkersDuringImport(false);
            }

            ImportCache cache = new ImportCache();
            importLavoratori(dto, cache, filename, appOpt);
            importAziende(dto, cache, filename , appOpt);


        } catch (Exception e) {
            try {
                logger.log(filename, "Errore irreversibile","Errore irreveribile nella importazione dei dati: " + e.getMessage(), false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return "";
    }

    /* Crea oggetto java Date dato il mese e l'anno */
    private Date createDate(int day, String month, String year) {

        if (month == null || year == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if (day == 0)
            calendar.set(Calendar.MONTH, Integer.valueOf(month));
        else
            calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);

        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        Date date = calendar.getTime();

        return date;
    }

    @Override
    public void deleteItem(long quotaId, long itemId) {
        //recupero la quota per verificare che non si tratti di id contraffatti
        RiepilogoQuoteAssociative r = quoteRep.get(quotaId).orElse(null);
        if (r == null)
            return;
        DettaglioQuotaAssociativa a = dettRep.get(itemId).orElse(null);
        if (a == null)
            return;

        dettRep.delete(itemId);
    }

    @Override
    public void updateItem(long quotaId, long itemId, UpdatableDettaglioQuota updatedData) {
        RiepilogoQuoteAssociative r = quoteRep.get(quotaId).orElse(null);
        if (r == null)
            return;
        DettaglioQuotaAssociativa a = dettRep.get(itemId).orElse(null);
        if (a == null)
            return;


        if (updatedData .getQuota() != null)
            a.setQuota(updatedData.getQuota());
        if (updatedData.getLivello() != null)
            a.setLivello(updatedData.getLivello());

        if (updatedData.getNote() != null)
            a.setNote(updatedData.getNote());


        dettRep.save(a);
    }


    @Override
    public void modifyCompetenceQuotaItems(long quotaId, Date inizio, Date fine) {
        RiepilogoQuoteAssociative quotaToModify = quoteRep.get(quotaId).orElse(null);
        List<DettaglioQuotaAssociativa> quoteDetailsToModify = this.getDettagliQuota(quotaId, null);

        if (inizio.getTime() > fine.getTime()){
            Date temp = fine;
            fine = inizio;
            inizio = temp;
        }

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        String competenza = String.format("%s - %s",f.format(inizio), f.format(fine));
        quotaToModify.setCompetenza(competenza);
        quoteRep.save(quotaToModify);

        for (DettaglioQuotaAssociativa dettaglioQuotaAssociativa : quoteDetailsToModify) {
            dettaglioQuotaAssociativa.setDataInizio(inizio);
            dettaglioQuotaAssociativa.setDataFine(fine);
            dettRep.save(dettaglioQuotaAssociativa);
        }

    }

    @Override
    public DettaglioQuotaAssociativa addItem(RiepilogoQuoteAssociative riepilogoQuoteAssociative, DettaglioQuotaDTO dettaglioQuotaDTO) throws Exception {


        DettaglioQuotaAssociativa a = importHelper.convertDettaglioFromDto(riepilogoQuoteAssociative, dettaglioQuotaDTO);

        dettRep.save(a);

        //una volta salvata creo e attivo la delega
        if (!riepilogoQuoteAssociative.getSettore().equals(Sector.sector_inps)){
            importDataDeleghe.activateDelega(riepilogoQuoteAssociative,a,true,true);
        }

        return a;

    }
}
