package applica.feneal.services.impl.importData;

import applica.feneal.domain.data.core.*;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Contract;
import applica.feneal.domain.model.core.ImportData;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.ImportDelegheSummaryForBari;
import applica.feneal.domain.model.core.importData.ImportAnagraficaPrevediValidator;
import applica.feneal.domain.model.core.importData.ImportAnagraficheValidator;
import applica.feneal.domain.model.core.importData.ImportDelegaSummary;
import applica.feneal.domain.model.core.importData.ImportDelegheValidator;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.feneal.domain.model.geo.City;
import applica.feneal.domain.model.geo.Country;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.services.*;
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
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fgran on 19/04/2017.
 */
@Service
public class ImportDataServiceImpl implements ImportDataService{

    @Autowired
    private ListaLavoroService listaSvc;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRep;

    @Autowired
    private RevocationReasonRepository causaleRevocaRep;

    @Autowired
    private Security sec;

    @Autowired
    private FileServer server;

    @Autowired
    private GeoService geo;

    @Autowired
    private SectorRepository sectorRep;

    @Autowired
    private ParitheticRepository pariteticRep;


    @Autowired
    private DelegheService delSvc;

    @Autowired
    private AziendaService azSvc;


    @Override
    public String importaDeleghe(ImportData importData) throws Exception {
        baseValidateDeleghe(importData);

        String user = ((User) sec.getLoggedUser()).getUsername();

        //creo una cartella temporanea dove inserirò tutti i dati per fare l'analisi
        File temp1 = File.createTempFile("import_deleghe_" + user,"");
        temp1.delete();
        temp1.mkdir();
        //creo una cartella corrante temporanea che alla fine sarà zippata
        //e restituita
        File temp = File.createTempFile("LogImportazioneDeleghe_" + user,"");
        temp.delete();
        temp.mkdir();

        ExcelInfo data = importDataForDeleghe(importData, temp1);
        validateExcelDataForDeleghe(data);


        //se non ci sono errori posso importare i lavoratori e quindi importare
        importAllDeleghe(data, temp, importData);

        String zippedFile = compressData(temp);


        //questa funxione restituiraà l'url della cartella zippata
        //pertanto dovro' inviare tramite fileserver il file zippato
        //al file server appunto
        String pathToFile =  server.saveFile("files/importazionedeleghe/", "zip", new FileInputStream(new File(zippedFile)));
        return pathToFile;
    }

    private void validateExcelDataForDeleghe(ExcelInfo data) throws Exception {
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            throw new Exception(String.format("Un file contiene errori: %s  <br>",  data.getError()));
        }

        //prendo l'intestazione e verifico che sia la stessa che mi aspetto
        List<String> template  = new ArrayList<>();
        template.add("COGNOME_UTENTE");
        template.add("NOME_UTENTE");
        template.add("DATA_NASCITA_UTENTE");
        template.add("SESSO");
        template.add("FISCALE");
        template.add("COMUNE_NASCITA");
        template.add("COMUNE");
        template.add("INDIRIZZO");
        template.add("CAP");
        template.add("TELEFONO1");
        template.add("TELEFONO2");
        template.add("NOTE_UTENTE");

        template.add("SETTORE");

        template.add("AZIENDA");
        template.add("PARTITA_IVA");
        template.add("COMUNE_AZIENDA");
        template.add("INDIRIZZO_AZIENDA");
        template.add("CAP_AZIENDA");
        template.add("TELEFONO_AZIENDA");
        template.add("NOTE_AZIENDA");
        template.add("CONTRATTO");

        template.add("DATA");
        template.add("DATA_ACCETTAZIONE");
        template.add("DATA_ANNULLAMENTO");
        template.add("DATA_REVOCA");
        template.add("CAUSALE_ISCRIZIONE");
        template.add("CAUSALE_REVOCA");
        template.add("NOTE");
        template.add("COLLABORATORE");

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

    private void baseValidateDeleghe(ImportData importData) throws Exception {
        baseValidate(importData);

        if (StringUtils.isEmpty(importData.getProvince()))
            throw new Exception("Territorio non specificato");
    }

    private void importAllDeleghe(ExcelInfo data, File tempdir, ImportData importData) throws Exception {
        String filename = tempdir + "\\logImportazioneDeleghe.txt";

        File f = new File(filename);
        f.createNewFile();

        writeToFile(f,  "Avvio import deleghe: num ( " + data.getOnlyValidRows().size() + " )");
        writeToFile(f,  "*****************************");
        writeToFile(f,  "*****************************");





        //Importo il lavoratore se non esiste e ne creo la relativa delega
        int i = 1; //numro di riga
        for (RowData rowData : data.getOnlyValidRows()) {
            //costruisco i dati del lavoratore
            Lavoratore l = null;
            ImportDelegaSummary summ = null;

            boolean lavoratoreOk = false;
            try {
                l = constructLavoratore(rowData, f, importData);
                lavoratoreOk = true;
            } catch (Exception e) {
                writeToFile(f, "ERRORE nella costruzione del lavoratore riga " + String.valueOf(i) + "; " + e.getMessage() );
            }

            if (lavoratoreOk){
                //posso procedere al salvataggio della delega


                try {
                    summ = constructDelega(rowData, f);

                } catch (Exception e) {
                    writeToFile(f, "ERRORE nella costruzuione della delega alla riga " + String.valueOf(i) + "; " + e.getMessage() );
                }

                try {

                    Delega d = createDelega(l, summ, importData);

                    //posso adesso salvare la delega
                    delSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(), d);



                    if (summ.getDataAccettazione() != null)
                        delSvc.acceptDelega(summ.getDataAccettazione(), d);
                    else if (summ.getDataAnnullamento() != null){
                        CausaleRevoca r = null;
                        if (!StringUtils.isEmpty(summ.getCausaleRevoca())){
                            r = causaleRevocaRep.find(LoadRequest.build().filter("description", summ.getCausaleRevoca())).findFirst().get();
                        }
                        delSvc.cancelDelega(summ.getDataAnnullamento(),d, r);
                    }
                    else if (summ.getDataRevoca() != null){
                        CausaleRevoca r = null;
                        if (!StringUtils.isEmpty(summ.getCausaleRevoca())){
                            r = causaleRevocaRep.find(LoadRequest.build().filter("description", summ.getCausaleRevoca())).findFirst().get();
                        }
                        delSvc.revokeDelega(summ.getDataRevoca(),d, r);
                    }



                } catch (Exception e) {
                    writeToFile(f, "ERRORE nel salvataggio della delega riga " + String.valueOf(i) + "; " + e.getMessage() );
                }
            }
            i++;

        }
    }

    private Date tryParse(String s, Date defaultDate){
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");


        try {
            return ff.parse(s);
        } catch (ParseException e) {
            return defaultDate;
        }

    }

    private ImportDelegaSummary constructDelega(RowData rowData, File f) throws ParseException {
        writeToFile(f, "Creo il summary per il lavoratore: " + rowData.getData().get("FISCALE"));

        ImportDelegaSummary  l = new ImportDelegaSummary();

        if (rowData.getData().get("DATA") != null)
            l.setData(tryParse(rowData.getData().get("DATA").trim(), new Date()));

        if (rowData.getData().get("DATA_ACCETTAZIONE") != null)
            l.setDataAccettazione(tryParse(rowData.getData().get("DATA_ACCETTAZIONE").trim(), null));

        if (rowData.getData().get("DATA_ANNULLAMENTO") != null)
            l.setDataAnnullamento(tryParse(rowData.getData().get("DATA_ANNULLAMENTO").trim(), null));

        if (rowData.getData().get("DATA_REVOCA") != null)
            l.setDataRevoca(tryParse(rowData.getData().get("DATA_REVOCA").trim(), null));

        if (rowData.getData().get("CAUSALE_ISCRIZIONE") != null)
            l.setCausaleIscrizione(rowData.getData().get("CAUSALE_ISCRIZIONE").trim());

        if (StringUtils.isEmpty(l.getCausaleIscrizione())){
            createIfNotExistCausaleIscrizioneDelega("RIPRESA DATI");
            l.setCausaleIscrizione("RIPRESA DATI");
        }else{
            createIfNotExistCausaleIscrizioneDelega(l.getCausaleIscrizione());
        }

        if (rowData.getData().get("CAUSALE_REVOCA") != null)
            if (!StringUtils.isEmpty(rowData.getData().get("CAUSALE_REVOCA").trim()))
            {
                String rev = rowData.getData().get("CAUSALE_REVOCA").trim();
                createIfNotExistCausaleRevocaDelega(rev);
                l.setCausaleRevoca(rev);
            }





        l.setNote(rowData.getData().get("NOTE").trim());
        l.setSettore(rowData.getData().get("SETTORE").trim());

        if (l.getSettore().equals(Sector.sector_edile))
            l.setParitetic(rowData.getData().get("AZIENDA").trim());
        else{
            l.setAzienda(rowData.getData().get("AZIENDA").trim());
            l.setPartita_iva(rowData.getData().get("PARTITA_IVA").trim());
            l.setAziendaComune(rowData.getData().get("COMUNE_AZIENDA").trim());
            l.setAziendaIndirizzo(rowData.getData().get("INDIRIZZO_AZIENDA").trim());
            l.setAziendaCap(rowData.getData().get("CAP_AZIENDA").trim());
            l.setAziendaTelefono(rowData.getData().get("TELEFONO_AZIENDA").trim());
            l.setAziendaNote(rowData.getData().get("NOTE_AZIENDA").trim());
        }


        if (rowData.getData().get("COLLABORATORE") != null){
            if (!StringUtils.isEmpty(rowData.getData().get("COLLABORATORE").trim()))
            {
                String rev = rowData.getData().get("COLLABORATORE").trim();
                createIfNotExistCollaboratore(rev);
                l.setCollaboratore(rev);
            }
        }

        if (rowData.getData().get("CONTRATTO") != null){
            if (!StringUtils.isEmpty(rowData.getData().get("CONTRATTO").trim()))
            {
                String rev = rowData.getData().get("CONTRATTO").trim();
                createIfNotExistContratto(rev);
                l.setContratto(rev);
            }
        }


        return l;
    }


    private Delega createDelega(Lavoratore l, ImportDelegaSummary summary, ImportData data) throws Exception {
        Delega d;
        d = new Delega();        //creo la delega.....
        d.setDocumentDate(summary.getData());

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");

        d.setImportGuid(s.format(new Date()));

        d.setWorker(l);
        d.setProvince(geo.getProvinceById(Integer.parseInt(data.getProvince())));
        d.setSubscribeReason(causaleIscrizioneDelegaRep.find(LoadRequest.build().filter("description", summary.getCausaleIscrizione())).findFirst().get());


        LoadRequest req = LoadRequest.build().filter("type", summary.getSettore());
        d.setSector(sectorRep.find(req).findFirst().orElse(null));


        if (d.getSector() != null && d.getSector().getType().equals(Sector.sector_edile)){
            //carico l'ente
            LoadRequest req1 = LoadRequest.build().filter("type", summary.getParitetic());
            d.setParitethic(pariteticRep.find(req1).findFirst().orElse(null));
        }else{
            //carico l'azienda trattandosi di impianti fissi
            Azienda a = constructAzienda(summary, data);
            d.setWorkerCompany(a);

        }

        if (!StringUtils.isEmpty(summary.getCollaboratore()))
            d.setCollaborator(collaboratorRepository.find(LoadRequest.build().filter("description", summary.getCollaboratore())).findFirst().get());

        d.setNotes(summary.getNote());



        return d;
    }

    private Azienda constructAzienda(ImportDelegaSummary summary, ImportData data) throws Exception {
        Azienda l = azSvc.getAziendaByDescription(summary.getAzienda());
        if (l == null){


            //creo il lavoratore
            l = new Azienda();


            l.setDescription(summary.getAzienda());
            if (!StringUtils.isEmpty(summary.getAziendaComune())){

                City cc1 = geo.getCityByName(summary.getAziendaComune());
                if (cc1!= null){
                    l.setCity(cc1.getDescription());
                    l.setProvince(geo.getProvinceById(cc1.getIdProvince()).getDescription());
                }
            }

            l.setAddress(summary.getAziendaIndirizzo());
            l.setCap(summary.getAziendaCap());
            l.setPhone(summary.getAziendaTelefono());
            l.setNotes(summary.getAziendaNote());

            azSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);

        }
        else{
            //devo verificare se aggiornare i dati dell'azienda
            if (data.isUpdateAzienda() != null)
                if (data.isUpdateAzienda() == 1){

                    if (!StringUtils.isEmpty(summary.getAziendaComune())){

                        City cc1 = geo.getCityByName(summary.getAziendaComune());
                        if (cc1!= null){
                            l.setCity(cc1.getDescription());
                            l.setProvince(geo.getProvinceById(cc1.getIdProvince()).getDescription());
                        }
                    }
                    l.setAddress(summary.getAziendaIndirizzo());
                    l.setCap(summary.getAziendaCap());
                    l.setPhone(summary.getAziendaTelefono());
                    l.setNotes(summary.getAziendaNote());
                }

            azSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);


        }
        return l;
    }


    @Override
    public String importaAnagrafichePrevedi(ImportData importData) throws Exception {

        baseValidate(importData);
        //creo una cartella temporanea dove inserirò tutti i dati per fare l'analisi
        File temp1 = File.createTempFile("import_anagrafiche_prevedi","");
        temp1.delete();
        temp1.mkdir();
        //creo una cartella corrante temporanea che alla fine sarà zippata
        //e restituita
        File temp = File.createTempFile("LogImportazioneAnagrafiche","");
        temp.delete();
        temp.mkdir();


        ExcelInfo data = importDataForAnagrafichePrevedi(importData, temp1);
        validateExcelDataForAnagrafichePrevedi(data);


        importAllAnagrafichePrevedi(data, temp, importData);

        String zippedFile = compressData(temp);

        //questa funxione restituiraà l'url della cartella zippata
        //pertanto dovro' inviare tramite fileserver il file zippato
        //al file server appunto
        String pathToFile =  server.saveFile("files/importazioneAnagrafichePrevedi/", "zip", new FileInputStream(new File(zippedFile)));
        return pathToFile;


    }

    private void importAllAnagrafichePrevedi(ExcelInfo data, File tempDir, ImportData importData) throws IOException {
        String filename = tempDir + "\\logImportazioneAnagrafiche.txt";

        File f = new File(filename);
        f.createNewFile();

        writeToFile(f,  "Avvio import anagrafiche prevedi: num ( " + data.getOnlyValidRows().size() + " )");
        writeToFile(f,  "*****************************");
        writeToFile(f,  "*****************************");





        //Importo il lavoratore se non esiste
        int i = 1; //numero di riga
        List<LavoratorePrevedi> lavs = new ArrayList<>();
        for (RowData rowData : data.getOnlyValidRows()) {
            LavoratorePrevedi l = null;
            try {
                l = constructLavoratorePrevedi(rowData, f, importData);
                lavs.add(l);
            } catch (Exception e) {
                writeToFile(f, "ERRORE nella costruzione del lavoratore riga " + String.valueOf(i) + "; " + e.getMessage() );
            }

            i++;

        }

        //qui posso inviare tutto al db.....
    }

    private LavoratorePrevedi constructLavoratorePrevedi(RowData rowData, File f, ImportData importData) {

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
        //creo il lavoratore
        LavoratorePrevedi l = new LavoratorePrevedi();

        if (StringUtils.isEmpty(importData.getAnno())){
            GregorianCalendar d = new GregorianCalendar();
            d.setTime(new Date());
            importData.setAnno(String.valueOf(d.get(Calendar.YEAR)));
        }
        l.setAnno(Integer.parseInt(importData.getAnno()));
        l.setName(rowData.getData().get("den_nome").trim());
        l.setSurname(rowData.getData().get("den_cognome").trim());
        try {
            l.setBirthDate(ff.parse(rowData.getData().get("data_nascita").trim()));
        }catch (Exception ex){
            GregorianCalendar c = new GregorianCalendar();
            c.set(Calendar.YEAR, 1900);
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND,0);
            l.setBirthDate(c.getTime());
        }

        l.setFiscalcode(rowData.getData().get("cod_fiscale"));
        l.setLivingCity(rowData.getData().get("den_localita_residenza"));
        l.setLivingProvince(rowData.getData().get("provincia_residenza"));
        l.setAddress(rowData.getData().get("den_indirizzo_residenza"));
        l.setCap(rowData.getData().get("cod_cap_residenza"));
        l.setCodCassa(rowData.getData().get("cod_cassa"));
        l.setInquadramento(rowData.getData().get("cod_den_inquadramentocassa"));
        l.setCassaEdile(rowData.getData().get("cassa_edile"));
        l.setCassaEdileRegione(rowData.getData().get("regione_cassa_edile"));
        l.setTipoAdesione(rowData.getData().get("desc_tipo_adesione"));

        return l;
    }

    private void validateExcelDataForAnagrafichePrevedi(ExcelInfo data) throws Exception {
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            throw new Exception(String.format("Un file contiene errori: %s  <br>",  data.getError()));
        }

        //prendo l'intestazione e verifico che sia la stessa che mi aspetto
        List<String> template  = new ArrayList<>();
        template.add("den_cognome");
        template.add("den_nome");
        template.add("cod_fiscale");
        template.add("data_nascita");
        template.add("den_indirizzo_residenza");
        template.add("cod_cap_residenza");
        template.add("den_localita_residenza");
        template.add("cod_sigla_provincia_residenza");
        template.add("provincia_residenza");
        template.add("den_inquadramento");
        template.add("cod_cassa");
        template.add("cassa_edile");
        template.add("regione_cassa_edile");
        template.add("desc_tipo_adesione");




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

    private ExcelInfo importDataForAnagrafichePrevedi(ImportData importData, File temp1) throws IOException {
        String file = getTempFile(importData.getFile1(), temp1);

        ExcelReader reader = new ExcelReader(file, 0,0 ,new ImportAnagraficaPrevediValidator());

        return reader.readFile();
    }


    @Override
    public String importaAnagrafiche(ImportData importData) throws Exception {
        baseValidate(importData);

        String user = ((User) sec.getLoggedUser()).getUsername();

        //creo una cartella temporanea dove inserirò tutti i dati per fare l'analisi
        File temp1 = File.createTempFile("import_anagrafiche_" + user,"");
        temp1.delete();
        temp1.mkdir();
        //creo una cartella corrante temporanea che alla fine sarà zippata
        //e restituita
        File temp = File.createTempFile("LogImportazioneAnagrafiche_" + user,"");
        temp.delete();
        temp.mkdir();

        ExcelInfo data = importDataForAnagrafiche(importData, temp1);
        validateExcelDataForAnagrafiche(data);


        //se non ci sono errori posso importare i lavoratori e quindi importare
        importAllAnagrafiche(data, temp, importData);

        String zippedFile = compressData(temp);


        //questa funxione restituiraà l'url della cartella zippata
        //pertanto dovro' inviare tramite fileserver il file zippato
        //al file server appunto
        String pathToFile =  server.saveFile("files/importazioneAnagrafiche/", "zip", new FileInputStream(new File(zippedFile)));
        return pathToFile;
    }


    private void importAllAnagrafiche(ExcelInfo data, File tempDir, ImportData importData) throws Exception {
        String filename = tempDir + "\\logImportazioneAnagrafiche.txt";

        File f = new File(filename);
        f.createNewFile();

        writeToFile(f,  "Avvio import anagrafiche: num ( " + data.getOnlyValidRows().size() + " )");
        writeToFile(f,  "*****************************");
        writeToFile(f,  "*****************************");





        //Importo il lavoratore se non esiste
        int i = 1; //numero di riga
        List<Lavoratore> lavs = new ArrayList<>();
        for (RowData rowData : data.getOnlyValidRows()) {
            Lavoratore l = null;
            try {
                l = constructLavoratore(rowData, f, importData);
                lavs.add(l);
            } catch (Exception e) {
                writeToFile(f, "ERRORE nella costruzione del lavoratore riga " + String.valueOf(i) + "; " + e.getMessage() );
            }

            i++;

        }

        if (importData.isCreateLista() != null )
            if (importData.isCreateLista() == 1 ){
                if (lavs.size() > 0){
                    //posso creare la lista di lavoro.....

                    ListaLavoro l1 = new ListaLavoro();
                    l1.setLavoratori(lavs);

                    listaSvc.saveListaLavoro(l1);


                }
            }

    }

    private Lavoratore constructLavoratore(RowData rowData, File f, ImportData importData) throws Exception {
        Lavoratore l = lavSvc.findLavoratoreByFiscalCode(rowData.getData().get("FISCALE").trim());
        if (l == null){
            writeToFile(f, "Creo il lavoratore: " + rowData.getData().get("FISCALE").trim());
            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            //creo il lavoratore
            l = new Lavoratore();
            if (rowData.getData().get("SESSO") != null){
                String sex = rowData.getData().get("SESSO").trim();
                if (!sex.equals("M") && ! sex.equals("F"))
                    l.setSex("M");
                else
                    l.setSex(sex);
            }else{
                l.setSex("M");
            }

            l.setName(rowData.getData().get("NOME_UTENTE").trim());
            l.setSurname(rowData.getData().get("COGNOME_UTENTE").trim());
            try {
                l.setBirthDate(ff.parse(rowData.getData().get("DATA_NASCITA_UTENTE").trim()));
            }catch (Exception ex){
                GregorianCalendar c = new GregorianCalendar();
                c.set(Calendar.YEAR, 1900);
                c.set(Calendar.MONTH, 0);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND,0);
                l.setBirthDate(c.getTime());
            }

            l.setFiscalcode(rowData.getData().get("FISCALE").trim());

            if (rowData.getData().get("COMUNE_NASCITA") != null){
                City cc = geo.getCityByName(rowData.getData().get("COMUNE_NASCITA"));
                if (cc!= null){
                    l.setBirthPlace(cc.getDescription());
                    l.setBirthProvince(geo.getProvinceById(cc.getIdProvince()).getDescription());
                    l.setNationality("ITALIA");
                }else{
                    //altrimenti provo con la nazione di nascita
                    Country cou = geo.getCountryByName(rowData.getData().get("COMUNE_NASCITA"));
                    if (cou != null)
                        l.setNationality(cou.getDescription());

                }
            }

            if (rowData.getData().get("COMUNE") != null){

                City cc1 = geo.getCityByName(rowData.getData().get("COMUNE"));
                if (cc1!= null){
                    l.setLivingCity(cc1.getDescription());
                    l.setLivingProvince(geo.getProvinceById(cc1.getIdProvince()).getDescription());
                }
            }


            if (rowData.getData().get("INDIRIZZO") != null)
                l.setAddress(rowData.getData().get("INDIRIZZO").trim());

            if (rowData.getData().get("CAP") != null)
                l.setCap(rowData.getData().get("CAP").trim());

            if (rowData.getData().get("TELEFONO1") != null)
                l.setCellphone(rowData.getData().get("TELEFONO1").toString().trim());

            if (rowData.getData().get("TELEFONO2") != null)
                l.setPhone(rowData.getData().get("TELEFONO2").toString().trim());

            if (rowData.getData().get("NOTE_UTENTE") != null)
                l.setNotes(rowData.getData().get("NOTE_UTENTE").toString().trim());

            lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);
            writeToFile(f, "Lavoratore creato: (" + rowData.getData().get("COD. FISCALE") + ")");
        }
        else{


            //devo verificare se aggiornare i dati anagrafici  oppure i dati di telefono
            if (importData.isUpdateTelefoni() != null)
                if (importData.isUpdateTelefoni() == 1){
                    //aggiorno i num di telefono
                    if (rowData.getData().get("TELEFONO1") != null)
                        if (!StringUtils.isEmpty(rowData.getData().get("TELEFONO1").toString().trim()))
                            l.setCellphone(rowData.getData().get("TELEFONO1").toString().trim());

                    if (rowData.getData().get("TELEFONO2") != null)
                        if (!StringUtils.isEmpty(rowData.getData().get("TELEFONO2").toString().trim()))
                            l.setPhone(rowData.getData().get("TELEFONO2").toString().trim());


                }


            //devo verificare se aggiornare i dati anagrafici  oppure i dati di residenza
            if (importData.isUpdateResidenza() != null)
                if (importData.isUpdateResidenza() == 1){
                    //aggiorno la residenza se esiste il comune
                    if (rowData.getData().get("COMUNE") != null)
                        if (!StringUtils.isEmpty(rowData.getData().get("COMUNE").toString().trim())){

                            City city = geo.getCityByName(rowData.getData().get("COMUNE").toString().trim());
                            if (city != null){
                                l.setLivingCity(city.getDescription());
                                l.setLivingProvince(geo.getProvinceById(city.getIdProvince()).getDescription());

                                //qui impost indirizzo e cap

                                if (rowData.getData().get("INDIRIZZO") != null)
                                    l.setAddress(rowData.getData().get("INDIRIZZO").trim());

                                if (rowData.getData().get("CAP") != null)
                                    l.setCap(rowData.getData().get("CAP").trim());

                            }
                        }
                }

            lavSvc.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);

            writeToFile(f, "Lavoratore esistente: (" + rowData.getData().get("FISCALE") + ")");
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


    private String compressData(File temp) throws IOException {
        //adesso posso zippare l'intera cartella
        ZipFacade zipper = new ZipFacade();
        //zippo la cartella in un file nella cartella stessa
        String zippedFile = temp.getAbsolutePath() + "/logImportazione.zip";
        zipper.CompressFolder(temp.getAbsolutePath() , zippedFile);
        return zippedFile;
    }


    private void validateExcelDataForAnagrafiche(ExcelInfo data) throws Exception {
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            throw new Exception(String.format("Un file contiene errori: %s  <br>",  data.getError()));
        }

        //prendo l'intestazione e verifico che sia la stessa che mi aspetto
        List<String> template  = new ArrayList<>();
        template.add("COGNOME_UTENTE");
        template.add("NOME_UTENTE");
        template.add("DATA_NASCITA_UTENTE");
        template.add("SESSO");
        template.add("FISCALE");
        template.add("COMUNE_NASCITA");
        template.add("COMUNE");
        template.add("INDIRIZZO");
        template.add("CAP");
        template.add("TELEFONO1");
        template.add("TELEFONO2");
        template.add("NOTE_UTENTE");

//        template.add("SETTORE");
//        template.add("AZIENDA");
//        template.add("PARTITA_IVA");
//        template.add("COMUNE_AZIENDA");
//        template.add("INDIRIZZO_AZIENDA");
//        template.add("CAP_AZIENDA");
//        template.add("TELEFONO_AZIENDA");
//        template.add("NOTE_AZIENDA");
//        template.add("CONTRATTO");
//
//        template.add("DATA");
//        template.add("DATA_ACCETTAZIONE");
//        template.add("DATA_ANNULLAMENTO");
//        template.add("DATA_REVOCA");
//        template.add("CAUSALE_REVOCA");
//        template.add("NOTE");
//        template.add("COLLABORATORE");

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
                errors = errors + ";" + errors;
            }
        }
        return errors;

    }

    private ExcelInfo importDataForAnagrafiche(ImportData importData, File temp1) throws IOException {
        String file = getTempFile(importData.getFile1(), temp1);

        ExcelReader reader = new ExcelReader(file, 0,0 ,new ImportAnagraficheValidator());

        return reader.readFile();
    }

    private ExcelInfo importDataForDeleghe(ImportData importData, File temp1) throws IOException {
        String file = getTempFile(importData.getFile1(), temp1);

        ExcelReader reader = new ExcelReader(file, 0,0 ,new ImportDelegheValidator());

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


    private void createIfNotExistCausaleRevocaDelega(String s) {
        CausaleRevoca d = causaleRevocaRep.find(LoadRequest.build().filter("description", s)).findFirst().orElse(null);
        if (d == null){
            d = new CausaleRevoca();
            d.setDescription(s);

            causaleRevocaRep.save(d);
        }
    }


    private void createIfNotExistContratto(String s) {
        Contract d = contractRepository.find(LoadRequest.build().filter("description", s)).findFirst().orElse(null);
        if (d == null){
            d = new Contract();
            d.setDescription(s);

            contractRepository.save(d);
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


    private void createIfNotExistCollaboratore(String collaboratore) {
        Collaboratore d = collaboratorRepository.find(LoadRequest.build().filter("description", collaboratore)).findFirst().orElse(null);
        if (d == null){
            d = new Collaboratore();
            d.setDescription(collaboratore);

            collaboratorRepository.save(d);
        }

    }

    private void baseValidate(ImportData importData) throws Exception {

        if (importData == null){
            throw new Exception("Nessun file indicato per l'importazione; Null");
        }

        //verifico che ci sia almeno un file
        if (fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(importData.getFile1())){
            throw new Exception("Nessun file indicato per l'importazione");
        }
    }
}
