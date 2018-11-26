package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.data.core.RevocationReasonRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.model.Filters;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.*;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.deleghe.states.*;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.servizi.search.UiQuoteImpiantiFissiSearchParams;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
import applica.feneal.domain.validation.DelegaValidator;
import applica.feneal.services.AziendaService;
import applica.feneal.services.DelegheService;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.impl.deleghe.bariimport.BariImportDelegheFactory;
import applica.feneal.services.impl.deleghe.bariimport.DelegheBariNewImport;
import applica.feneal.services.impl.deleghe.bariimport.IImportDelegaBari;
import applica.framework.Conjunction;
import applica.framework.Disjunction;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.fileserver.FileServer;
import applica.framework.management.csv.RowData;
import applica.framework.management.excel.ExcelInfo;
import applica.framework.management.excel.ExcelReader;
import applica.framework.management.zip.ZipFacade;
import applica.framework.security.Security;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fgran on 05/04/2016.
 */
@Component
public class DelegaServiceImpl implements DelegheService {

    @Autowired
    private FileServer server;

    @Autowired
    private ApplicationOptionRepository appRep;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private SectorRepository secRep;
    @Autowired
    private DelegheRepository rep;

    @Autowired
    private DelegheBariNewImport bariNewImport;

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRep;

    @Autowired
    private RevocationReasonRepository causaleRevocaRep;

    @Autowired
    protected AziendaService azServ;


    @Autowired
    private Security sec;

    @Autowired
    private DelegaValidator delegaValidator;


    @Autowired
    private BariImportDelegheFactory bariImportFactory;

    private DelegaState constructStateManager(Delega delega) {
        DelegaState stateManager = null;
        if (delega.getState() != null) {
            switch (delega.getState()) {
                case Delega.state_accepted:
                    stateManager = new AcceptState();
                    break;
                case Delega.state_subscribe:
                    stateManager = new SubscribeState();
                    break;
                case Delega.state_sent:
                    stateManager = new SendState();
                    break;
                case Delega.state_activated:
                    stateManager = new ActivateState();
                    break;
                case Delega.state_cancelled:
                    stateManager = new CancelledState();
                    break;
                case Delega.state_revoked:
                    stateManager = new RevokedState();
                    break;
            }
        }
        return stateManager;

    }


    @Override
    public String importaDelegheBariCassaEdile(ImportDeleghe importData) throws Exception {
        baseValidate(importData);

        //creo una cartella temporanea dove inserirò tutti i dati per fare l'analisi
        File temp1 = File.createTempFile("import_deleghe","");
        temp1.delete();
        temp1.mkdir();
        //creo una cartella corrante temporanea che alla fine sarà zippata
        //e restituita
        File temp = File.createTempFile("LogImportazioneDeleghe","");
        temp.delete();
        temp.mkdir();

        ExcelInfo data = importData(importData, temp1);
        validateExcelData(data);


        //se non ci sono errori posso importare i lavoratori e quindi importare
        importAllDataNew(data, temp);
        //importAllData(data, temp);
        String zippedFile = compressData(temp);


        //questa funxione restituiraà l'url della cartella zippata
        //pertanto dovro' inviare tramite fileserver il file zippato
        //al file server appunto
        String pathToFile =  server.saveFile("files/bari_importazioneDeleghe/", "zip", new FileInputStream(new File(zippedFile)));
        return pathToFile;
    }

    private String compressData(File temp) throws IOException {
        //adesso posso zippare l'intera cartella
        ZipFacade zipper = new ZipFacade();
        //zippo la cartella in un file nella cartella stessa
        String zippedFile = temp.getAbsolutePath() + "/logImportazione.zip";
        zipper.CompressFolder(temp.getAbsolutePath() , zippedFile);
        return zippedFile;
    }

    //nuova implentazione deleghe bar
    private void importAllDataNew(ExcelInfo data, File tempDir) throws IOException {





        String filename = tempDir + "\\logImportazioneDeleghe.txt";

        File f = new File(filename);
        f.createNewFile();

        writeToFile(f,  "Avvio import deleghe: num ( " + data.getOnlyValidRows().size() + " )");
        writeToFile(f,  "*****************************");
        writeToFile(f,  "*****************************");





        //Importo il lavoratore se non esiste e ne creo la relativa delega
        int i = 1; //numro di riga
        for (RowData rowData : data.getOnlyValidRows()) {
            Lavoratore l = null;
            DelegaBari summ = null;
            boolean lavoratoreOk = false;
            try {
                l = constructLavoratore(rowData, f);
                lavoratoreOk = true;
            } catch (Exception e) {
                writeToFile(f, "ERRORE nella costruzuione del lavoratore riga " + String.valueOf(i) + "; " + e.getMessage() );
            }

            if (lavoratoreOk){
                //posso procedere al salvataggio della delega


                try {
                    summ = constructSummaryNew(rowData, f, l);

                } catch (Exception e) {
                    writeToFile(f, "ERRORE nella costruzuione della delega alla riga " + String.valueOf(i) + "; " + e.getMessage() );
                }

                try {

                    saveDelegheForBariNew(l, summ, f);
                } catch (Exception e) {
                    writeToFile(f, "ERRORE nel salvataggio della delega riga " + String.valueOf(i) + "; " + e.getMessage() );
                }
            }
            i++;

        }



    }



    private void importAllData(ExcelInfo data, File tempDir) throws IOException {

        //prima di avviare l'importazione creo se gia non esistono
        // tutte le causali revoca e la caisali iscrizione

        //le causali revoca sono: REVOCA/DEL,REVOCA/BIA
        //ISCRIZIONE, ISCR.INEFF, DOPPIONE
        createIfNotExistCausaleIscrizioneDelega("ISCRIZIONE");
        createIfNotExistCausaleRevocaDelega("REVOCA/DEL");
        createIfNotExistCausaleRevocaDelega("REVOCA/BIA");
        createIfNotExistCausaleRevocaDelega("ISCR.INEFF");
        createIfNotExistCausaleRevocaDelega("DOPPIONE");
        createIfNotExistCausaleRevocaDelega("ANOMALIA");



        String filename = tempDir + "\\logImportazioneDeleghe.txt";

        File f = new File(filename);
        f.createNewFile();

        writeToFile(f,  "Avvio import deleghe: num ( " + data.getOnlyValidRows().size() + " )");
        writeToFile(f,  "*****************************");
        writeToFile(f,  "*****************************");





        //Importo il lavoratore se non esiste e ne creo la relativa delega
        int i = 1; //numro di riga
        for (RowData rowData : data.getOnlyValidRows()) {
            Lavoratore l = null;
            ImportDelegheSummaryForBari summ = null;
            boolean lavoratoreOk = false;
            try {
                l = constructLavoratore(rowData, f);
                lavoratoreOk = true;
            } catch (Exception e) {
                writeToFile(f, "ERRORE nella costruzuione del lavoratore riga " + String.valueOf(i) + "; " + e.getMessage() );
            }

            if (lavoratoreOk){
                //posso procedere al salvataggio della delega


                try {
                    summ = constructSummary(rowData, f);

                } catch (Exception e) {
                    writeToFile(f, "ERRORE nella costruzuione della delega alla riga " + String.valueOf(i) + "; " + e.getMessage() );
                }

                try {

                    saveDelegheForBari(l, summ, f);
                } catch (Exception e) {
                    writeToFile(f, "ERRORE nel salvataggio della delega riga " + String.valueOf(i) + "; " + e.getMessage() );
                }
            }
            i++;

        }



    }

    private void createIfNotExistCausaleRevocaDelega(String s) {
        CausaleRevoca d = causaleRevocaRep.find(LoadRequest.build().filter("description", s)).findFirst().orElse(null);
        if (d == null){
            d = new CausaleRevoca();
            d.setDescription(s);

            causaleRevocaRep.save(d);
        }
    }

    private void createIfNotExistCausaleIscrizioneDelega(String iscrizione) {
        CausaleIscrizioneDelega d = causaleIscrizioneDelegaRep.find(LoadRequest.build().filter("description", iscrizione)).findFirst().orElse(null);
        if (d == null){
            d = new CausaleIscrizioneDelega();
            d.setDescription(iscrizione);

            causaleIscrizioneDelegaRep.save(d);
        }

    }

    private void saveDelegheForBari(Lavoratore l, ImportDelegheSummaryForBari summ, File f) throws Exception {

        IImportDelegaBari importer = bariImportFactory.createImporter(summ);
        importer.importDelega(l, summ, f);



    }

    private void saveDelegheForBariNew(Lavoratore l, DelegaBari summ, File f) throws Exception {


        bariNewImport.importDelega(summ, f);

    }


    private DelegaBari constructSummaryNew(RowData rowData, File f, Lavoratore w) throws ParseException {
        writeToFile(f, "Creo il summary per il lavoratore: " + rowData.getData().get("COD. FISCALE"));


        String[] split = rowData.getData().get("DECORRENZA").trim().split("/");

        String decorrenzaAnno = split[0];
        String decorrenzaMese = split[1];


        DelegaBari l = new DelegaBari();
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        l.setProtocolDate(ff.parse(rowData.getData().get("DATA PROT.").trim()));
        l.setProtocolNumber(rowData.getData().get("NUM. PROT.").trim());
        l.setEffectDate(ff.parse("01/" + decorrenzaMese + "/" + decorrenzaAnno));
        l.setSignup(rowData.getData().get("ISCRIZIONE / ISCR.INEFF").trim());
        l.setRevocation(rowData.getData().get("REVOCA").trim());
        l.setDuplicate(rowData.getData().get("DOPPIONE").trim());
        l.setAnomaly(rowData.getData().get("ANOMALE").trim());
        try{
            l.setLastMovement(rowData.getData().get("ULT.MOV.").trim());
        }catch(Exception ex){
            l.setLastMovement("");
        }

        l.setWorker(w);



        if (rowData.getData().containsKey("AZIENDA")){
            String azienda = rowData.getData().get("AZIENDA").trim();
            if (!StringUtils.isEmpty(azienda)){
                try {
                    l.setWorkerCompany(azServ.getAziendaByDescriptionorCreateIfNotExist(azienda));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

//        if (rowData.getData().containsKey("ULT.MOV.")){
//            l.setUltimoMovimento(rowData.getData().get("ULT.MOV.").trim());
//        }

        return l;
    }



    private ImportDelegheSummaryForBari constructSummary(RowData rowData, File f) throws ParseException {
        writeToFile(f, "Creo il summary per il lavoratore: " + rowData.getData().get("COD. FISCALE"));

        ImportDelegheSummaryForBari l = new ImportDelegheSummaryForBari();
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        l.setProtocolDate(ff.parse(rowData.getData().get("DATA PROT.").trim()));
        l.setProtocol(rowData.getData().get("NUM. PROT.").trim());
        l.setDecorrenza(rowData.getData().get("DECORRENZA").trim());
        l.setSubscription(rowData.getData().get("ISCRIZIONE / ISCR.INEFF").trim());
        l.setRevoke(rowData.getData().get("REVOCA").trim());
        l.setDoubleDel(rowData.getData().get("DOPPIONE").trim());
        l.setAnomalia(rowData.getData().get("ANOMALE").trim());


       if (rowData.getData().containsKey("AZIENDA")){
           l.setAzienda(rowData.getData().get("AZIENDA").trim());
       }

        if (rowData.getData().containsKey("ULT.MOV.")){
            l.setUltimoMovimento(rowData.getData().get("ULT.MOV.").trim());
        }

        return l;
    }

    private Lavoratore constructLavoratore(RowData rowData, File f) throws Exception {


        Lavoratore l = lavSvc.findLavoratoreByFiscalCode(rowData.getData().get("COD. FISCALE").trim());
        if (l == null){
            writeToFile(f, "Creo il lavoratore: " + rowData.getData().get("COD. FISCALE").trim());
            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            //creo il lavoratore
            l = new Lavoratore();
            l.setSex("M");
            l.setFiscalcode(rowData.getData().get("COD. FISCALE").trim());
            l.setBirthDate(ff.parse(rowData.getData().get("NATO").trim()));
            l.setLivingProvince(rowData.getData().get("PROV.").trim());
            l.setLivingCity(rowData.getData().get("COMUNE").trim());
            l.setCap(rowData.getData().get("CAP").trim());
            l.setCe(rowData.getData().get("CODICE").trim());
            l.setName(rowData.getData().get("NOME").trim());
            l.setSurname(rowData.getData().get("COGNOME").trim());
            l.setAddress(rowData.getData().get("INDIRIZZO").trim());

            if (rowData.getData().containsKey("NUM. CELLULARE"))
                if (rowData.getData().get("NUM. CELLULARE") != null)
                    l.setCellphone(rowData.getData().get("NUM. CELLULARE").toString().trim());


            lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);
            writeToFile(f, "Lavoratore creato: (" + rowData.getData().get("COD. FISCALE") + ")");
        }
        else{

            //inserisco il cell solo se esiste il campo e non è nullo
            if (rowData.getData().containsKey("NUM. CELLULARE"))
                if (rowData.getData().get("NUM. CELLULARE") != null)
                    if (!StringUtils.isEmpty(rowData.getData().get("NUM. CELLULARE").toString().trim()))
                        l.setCellphone(rowData.getData().get("NUM. CELLULARE").toString().trim());

            lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);

            writeToFile(f, "Lavoratore esistente: (" + rowData.getData().get("COD. FISCALE") + ")");
        }
        return l;


    }

    public void writeToFile(File myFile, String content) {
        BufferedWriter bufferedWriter = null;
        try{
            Writer writer = new FileWriter(myFile,true);
            bufferedWriter = new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bufferedWriter);
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(bufferedWriter != null) bufferedWriter.close();
            } catch(Exception ex){

            }
        }

    }


    private void validateExcelData(ExcelInfo data) throws Exception {
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            throw new Exception(String.format("Un file contiene errori: %s  <br>",  data.getError()));
        }

        //prendo l'intestazione e verifico che sia la stessa che mi aspetto
        List<String> template  = new ArrayList<>();
        template.add("PROV.");
        template.add("COMUNE");
        template.add("CODICE");
        template.add("COGNOME");
        template.add("NOME");
        template.add("NATO");
        template.add("COD. FISCALE");
        template.add("INDIRIZZO");
        template.add("CAP");
        template.add("DATA PROT.");
        template.add("NUM. PROT.");
        template.add("DECORRENZA");
        template.add("ISCRIZIONE / ISCR.INEFF");
        template.add("REVOCA");
        template.add("DOPPIONE");
        template.add("ANOMALE");

        List<String> headers = data.getHeaderFields();
        String name = FilenameUtils.getName(data.getSourceFile());
        String err = headersContainsTemplate(headers, template);
        boolean equal = StringUtils.isEmpty(err);
        if (!equal){
            //recupero il nome del file

            throw new Exception(String.format("Il file %s non contiene le intestazioni richieste<br>: Campi mancanti: %s", name, err));
        }
        if (equal){
            //verifico che ci sia almeno una riga
            if (data.getOnlyValidRows().size() == 0){
                throw new Exception(String.format("Il file %s non contiene informazioni<br>", name));
            }

        }
    }

    private String headersContainsTemplate(List<String> headers, List<String> template) {
        String errors = "";
        for (String s : template) {
            if (!headers.contains(s)){
                errors = errors + ";" + s;
            }
        }
        return errors;

    }


    private ExcelInfo importData(ImportDeleghe importData, File temp) throws IOException {
        String file = getTempFile(importData.getFile1(), temp);

        ExcelReader reader = new ExcelReader(file, 0, 1 ,new ImportDelegheValidatorForBari());

        return reader.readFile();
    }

    private String getTempFile(String  filePath, File temp ) throws IOException {
        if (fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(filePath))
            return null;

        InputStream file =server.getFile(filePath);
        String mime = "." + FilenameUtils.getExtension(filePath);
        String  a=FilenameUtils.getName(filePath);


        return addToTempFolder(file, a, mime, temp);
    }

    private String addToTempFolder(InputStream inputStream, String name, String mime, File temp ) throws IOException {

        //aggiungo il file alla direcotry
        String filename = temp.getAbsolutePath() + "\\" + name;
        File nn = new File(filename);
        nn.createNewFile();

        //copio il file inviato nella cartella temporanea
        OutputStream outputStream = new FileOutputStream(nn);

        try{

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        return nn.getAbsolutePath();
    }

    private void baseValidate(ImportDeleghe importData) throws Exception {

        if (importData == null){
            throw new Exception("Nessun file indicato per l'importazione; Null");
        }

        //verifico che ci sia almeno un file
        if (fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(importData.getFile1())){
            throw new Exception("Nessun file indicato per l'importazione");
        }
    }

    @Override
    public void accettaDeleghe(List<Delega> deleghe, Date date) {
        for (Delega delega : deleghe) {
            acceptDelega(date, delega);
        }
    }

    @Override
    public void inoltraDeleghe(List<Delega> deleghe, Date date) {
        for (Delega delega : deleghe) {
            sendDelega(date, delega);
        }
    }

    @Override
    public void subscribeDelega(Delega del) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.subscribeDelega(del);

        rep.save(del);

        //accetto se si parla di impianti fissi
        if (del.getSector().getType().equals(Sector.sector_IMPIANTIFISSI)){
            doAccept(del);
        }else{

            //se si tratta di settore edile devo verificare le  opzioni dell'applicazione per vedere se devo
            //accettare immediatmente la delega
            //recuoero le opzioni
            List<ApplicationOptions> opts = appRep.find(null).getRows();

            if (opts.size()>0)
            {
                ApplicationOptions opt = opts.get(0);
                if (opt.isCreateDelegaAsAccettata()) {
                    doAccept(del);
                }

            }


        }

    }

    private void doAccept(Delega del) {
        DelegaState stateManager;
        stateManager = constructStateManager(del);
        stateManager.acceptDelega(del.getDocumentDate(),del);
        rep.save(del);

        cancelAfterAccept(del);
    }


    @Override
    public void sendDelega(Date date, Delega del) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.sendDelega(date, del);

        rep.save(del);
    }


    @Override
    public void acceptDelega(Date date, Delega del) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.acceptDelega(date, del);

        rep.save(del);

        //dopo aver accettato la delga devo recuperare tutte le deleghe attive o accettate e annullarle
        cancelAfterAccept(del);


    }

    private void cancelAfterAccept(Delega del) {
        List<Delega> d = rep.getDelegheByLavoratore(del.getWorker().getLid());
        for (Delega item : d) {
            DelegaState sm = constructStateManager(item);
            if (item.checkIfActivateOrAccepted()) {

                //se la delega è accettata o attiva devo verificare per poterla annullare se si stratta
                //dello stesso settore, ente ed eventualemnte azienda.... e provincia


                //se la delega eè della stessa provincia
                if (del.getProvince().getIid() == item.getProvince().getIid()) {
                    //se sono lo stesso settore....
                    if (del.getSector().getLid() == (item.getSector().getLid())) {

                        //se si tratta di settore edile l'ente deve essere lo stesso..
                        //altrimenti l'azienda
                        boolean proceedWithCancel = false;
                        if (del.getSector().getType().equals(Sector.sector_edile)) {

                            if (del.getParitethic().getLid() == item.getParitethic().getLid())
                                if (del.getLid() != item.getLid()) //se ovviamente non è la stessa delega
                                    proceedWithCancel = true;

                        } else {
                            if (del.getWorkerCompany().getLid() == item.getWorkerCompany().getLid())
                                if (del.getLid() != item.getLid()) //se ovviamente non è la stessa delega
                                    proceedWithCancel = true;
                        }
                        if (proceedWithCancel){
                            sm.cancelDelega(new Date(),item, null, del);
                            rep.save(item);
                        }

                    }
                }

            }
        }
    }


    @Override
    public void cancelDelega(Date date, Delega del, CausaleRevoca reason) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.cancelDelega(date,del, reason, null);

        rep.save(del);
    }


    @Override
    public void revokeDelega(Date date, Delega del, CausaleRevoca reason) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.revokeDelega(date,del, reason);

        rep.save(del);
    }


    @Override
    public void activateDelega(Date date, Delega del) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.activateDelega(del);

        rep.save(del);
    }

    @Override
    public void goBack(Delega del) {
        DelegaState stateManager = constructStateManager(del);

        stateManager.goBack(del);

        rep.save(del);
    }

    @Override
    public List<Delega> getAllWorkerDeleghe(long workerId) {
        return rep.find(LoadRequest.build().id(Filters.DELEGA_ID_LAVORATORE, workerId)).getRows();
    }

    @Override
    public List<Delega> getWorkerDelegheEdiliByDataAndEnte(long workerId, Date date, String ente) {

        List<Delega> del = getAllWorkerDeleghe(workerId);

        return del.stream()
                .filter(a -> a.getDocumentDate().getTime() == date.getTime())
                .filter(a -> a.getParitethic().getDescription().toLowerCase().equals(ente.toLowerCase()))
                .collect(Collectors.toList());


    }

    @Override
    public boolean hasDelegaEdile(long workerId) {

        List<Delega>deleghe = getAllWorkerDeleghe(workerId);

        return deleghe.stream().anyMatch(a -> a.getSector().getDescription().equals(Sector.sector_edile));


    }

    @Override
    public void deleteDelega(long user, long delegaId) {

        rep.delete(delegaId);
    }

    @Override
    public void saveOrUpdate(long userId, Delega l) throws Exception {

        String error = delegaValidator.validate(l);
        if (org.apache.commons.lang.StringUtils.isEmpty(error)) {
            if (l.getLid() == 0) {

                //creo
                subscribeDelega(l);
            }else{

                //recupero la delega dal db
                Delega del = rep.get(l.getLid()).get();
                //adesso in base allo stato posso aggiornarne i paramertri
                DelegaState stateManager = constructStateManager(del);
                stateManager.updateDelega(del, l);
                //aggiorno
                del.setFirstAziendaEdile(l.getFirstAziendaEdile());
                rep.save(del);
            }

            return;
        }

        throw new Exception(error);
    }

    @Override
    public Delega getDelegaById(Long id) {
        return rep.get(id).orElse(null);
    }

    @Override
    public List<Integer> getDelegaPermittedNextStates(Delega delega, List<ApplicationOptions> opt) {
        if (opt.size()> 0)
            return constructStateManager(delega).getSupportedNextStates(delega, opt.get(0));

        return constructStateManager(delega).getSupportedNextStates(delega,null);
    }

    @Override
    public List<Delega> getDelegheImpiantiFissi(UiQuoteImpiantiFissiSearchParams params) {

        String azienda = params.getFirm();
        String province = params.getProvince();

        //posso adesso fare la query

        LoadRequest req = LoadRequest.build();

        Conjunction andClause = new Conjunction();





        // Filtro per recuperare le deleghe accettate (stato 3) o attivate (stato 4)
        Disjunction d = new Disjunction();
        List<Filter> list = new ArrayList<>();

        Filter fd1 = new Filter("state", 3);
        Filter fd2 = new Filter("state", 4);
        list.add(fd1);
        list.add(fd2);
        d.setChildren(list);
        req.getFilters().add(d);


        Disjunction d1 = new Disjunction();
        List<Filter> list1 = new ArrayList<>();

        Filter fd11 =new Filter("validityDate",new Date(), Filter.LTE);
        Filter fd21 = new Filter("validityDate","", Filter.NULL);
        list1.add(fd11);
        list1.add(fd21);
        d1.setChildren(list1);

        req.getFilters().add(d1);



        // Filtro per recuperare le deleghe del settore Impianti fissi
        Sector s = secRep.find(LoadRequest.build().filter("type", Sector.sector_IMPIANTIFISSI)).findFirst().orElse(null);

        if (s != null){
            Filter f2 = new Filter("sector", s.getId(), Filter.EQ);
            req.getFilters().add(f2);
        } else{
            // potrei già restituire risultato vuoto
            return new ArrayList<>();
        }


        if (!StringUtils.isEmpty(azienda)){
            Filter f3 = new Filter("workerCompany", Long.parseLong(azienda), Filter.EQ);
            req.getFilters().add(f3);
        }

        if (!StringUtils.isEmpty(province)){
            Integer proId = Integer.parseInt(province);
            Filter f4 = new Filter("province.id", proId, Filter.EQ);
            req.getFilters().add(f4);
        }


        List<Delega> del = rep.find(req).getRows();

        List<Delega> delegheLavoratoriSenzaDuplicati = new ArrayList<>();
        for (Delega delega : del) {

            if (delegheLavoratoriSenzaDuplicati.stream().filter(q -> q.getWorker().getId() == delega.getWorker().getId()).count() == 0)
                delegheLavoratoriSenzaDuplicati.add(delega);
        }

        return delegheLavoratoriSenzaDuplicati;
    }

    @Override
    public boolean hasWorkerDelegaAttivaOAccettata(long workerId, String sector, String ente, String azienda, String provincia) {

        //recupero la lista di tutte le deleghe attive o accettte per un lavoratore
        LoadRequest req = LoadRequest.build().filter("worker", workerId);
        List<Delega> del = rep.find(req).getRows();


        //ciclo su tutte le deleghe
        for (Delega delega : del) {
            if (delega.getState() == Delega.state_accepted || delega.getState() == Delega.state_activated)
                if (delega.getProvince().getDescription().toUpperCase().equals(provincia.toUpperCase()))
                    if (delega.getSector().getDescription().toUpperCase().equals(sector.toUpperCase())){


                        //se tutte queste condiziobin sono verificate
                        //a seconda del settore posso verificare ente e azienda
                        if (sector.equals(Sector.sector_IMPIANTIFISSI)){
                            //devo verificare l'azienda
                            if (delega.getWorkerCompany().getDescription().equals(azienda))
                                return true;

                        }
                        else{

                            //se si tratta di settore edile devo verificare l'ente
                            if (delega.getParitethic().getType().equals(ente))
                                return true;

                        }




                    }
        }

        return false;

    }

    @Override
    public Delega retrieveActivableWorkerDelega(long idLavoratore, int provinceId, String settore, String ente, long idAzienda) {
        LoadRequest req = LoadRequest.build().filter("worker", idLavoratore);
        List<Delega> del = rep.find(req).getRows();

        List<Delega> result = new ArrayList<>();

        //ciclo su tutte le deleghe
        for (Delega delega : del) {
            if (delega.getState() == Delega.state_accepted || delega.getState() == Delega.state_activated || delega.getState() == Delega.state_sent || delega.getState() == Delega.state_subscribe)
                if (delega.getProvince().getIid() == provinceId)
                    if (delega.getSector().getDescription().equals(settore)) {


                        //se tutte queste condiziobin sono verificate
                        //a seconda del settore posso verificare ente e azienda
                        if (settore.equals(Sector.sector_IMPIANTIFISSI)) {
                            //devo verificare l'azienda
                            if (delega.getWorkerCompany().getLid() == idAzienda)
                                result.add(delega);

                        } else {

                            //se si tratta di settore edile devo verificare l'ente
                            if (ente != null){
                                if (delega.getParitethic().getType().equals(ente.toUpperCase()))
                                    result.add(delega);
                            }


                        }


                    }
        }
        if (result.size() == 0)
            return null;


        //se ho trovato piu deleghe
        //tento di restituirne in ordine di stato
        Delega d1 = result.stream().filter(d->d.getState() == Delega.state_activated).findFirst().orElse(null);
        if (d1 == null)
        {
            //tento di recuperare una delega accettata
            d1 = result.stream().filter(d->d.getState() == Delega.state_accepted).findFirst().orElse(null);


            if (d1 == null)
            {
                //tento di recuperare una delega inoltrata
                d1 = result.stream().filter(d->d.getState() == Delega.state_sent).findFirst().orElse(null);


                if (d1 == null)
                {
                    //tento di recuperare una delega sottoscritta
                    d1 = result.stream().filter(d->d.getState() == Delega.state_subscribe).findFirst().orElse(null);
                }
            }

        }

        return d1;

    }

    @Override
    public List<Lavoratore> findLavoratoriConDelegaEdilePerAzienda(long id) {

        LoadRequest req = LoadRequest.build().filter("firstAziendaEdile", id);
        List<Delega> d1 = rep.find(req).getRows().stream().filter(d -> d.getState() != Delega.state_cancelled && d.getState() != Delega.state_revoked ).collect(Collectors.toList());


        HashMap<Long, Lavoratore> h = new HashMap<>();
        for (Delega delega : d1) {
            if (!h.containsKey(delega.getWorker().getLid()))
                h.put(delega.getWorker().getLid(), delega.getWorker());
        }

        return  new ArrayList<>(h.values());


    }
}
