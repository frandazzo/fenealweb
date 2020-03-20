package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ristorniEdilizia.ReferentiRepository;
import applica.feneal.domain.data.core.ristorniEdilizia.RistornoRepository;
import applica.feneal.domain.model.core.deleghe.ImportDeleghe;
import applica.feneal.domain.model.core.deleghe.ImportRistorniCassaEdileValidatorForBari;
import applica.feneal.domain.model.core.deleghe.ImportRistorniEdilCassaValidatorFoBari;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.deleghe.bari.ImportRistorniDelegheBari;
import applica.feneal.domain.model.core.deleghe.bari.RistornoCassaEdileFilter;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.ristorniEdilizia.*;
import applica.feneal.services.LavoratoreService;
import applica.feneal.services.impl.quote.ErrorsCounter;
import applica.framework.LoadRequest;
import applica.framework.fileserver.FileServer;
import applica.framework.management.excel.ExcelInfo;
import applica.framework.management.excel.ExcelReader;
import applica.framework.management.csv.RowData;
import applica.feneal.domain.model.User;
import applica.framework.security.Security;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class DelegheBariRistorniService {

    @Autowired
    private FileServer server;

    @Autowired
    private LavoratoreService lavServ;

    @Autowired
    private DelegheBariService delBariServ;

    @Autowired
    private RistornoRepository ristornoRep;

    @Autowired
    private Security sec;

    @Autowired
    private ReferentiRepository refRep;

    public RistornoBariObject retriveListaRiepilogoRistorni(RistornoCassaEdileFilter params) throws Exception {
        ImportDeleghe file1 = new ImportDeleghe();
        file1.setFile1(params.getFile1());
        String ente = params.getParithetic();

        //VALIDO IL FILE
        baseValidate(file1);

        //creo una cartella temporanea dove inserirò tutti i dati per fare l'analisi
        File temp2 = File.createTempFile("import_ristorni_cassaedile","");
        temp2.delete();
        temp2.mkdir();


        //creo una cartella corrente temporanea che alla fine sarà zippata
        //e restituita


        File temp = File.createTempFile("LogImportazioneRistornoCassaEdileBari","");
        temp.delete();
        temp.mkdir();
        List<QuotaAssociativaBari> list = new ArrayList<>();
        ExcelInfo data = new ExcelInfo();


        if(ente.equals("CASSA EDILE")){

            data = importDataCassaEdile(file1, temp2);
            validateExcelDataCassaEdile(data);

            list = createListCassaEdile(data);
        }else
            if(ente.equals("EDILCASSA")){
            data = importDataEdilCassa(file1, temp2);

            validateExcelDataEdilCassa(data);
            
            list = createListEdilcassa(data);
        }

       //CREO LA LISTA DELLE DELEGHE "VINCENTI",
        // OVVERO LE DELEGHE SU SUI VERRANO CALCOLATE LE VARIE QUOTE DA IMPORTARE

        List<QuotaAssociativaBari> quoteSelezionate = new ArrayList<>();

            for( QuotaAssociativaBari del : list){
                if(del.getUltimaDelega() != null && del.getUltimaDelega().getManagementContact() != null)
                    quoteSelezionate.add(del);
            }

        //CREO LA LISTA DEI REFERENTI DA MOSTRARE NELLA VIEW
            List<UiReferenti> listReferenti = new ArrayList<>();

            for(QuotaAssociativaBari del : quoteSelezionate){

                    Referenti ref = del.getUltimaDelega().getManagementContact();
                    float quotaAssoc = del.getQuotaAssoc();
                    boolean exist = isCurrentReferenteInList(ref, listReferenti);

                    if(exist){
                        for(UiReferenti listRef : listReferenti){
                            String r = listRef.getNominativo();

                            if(r.equals(ref.getCompleteName())){
                                float currentImp = listRef.getImportoTot();
                                listRef.setImportoTot(currentImp + getNewImport(ref, quotaAssoc));

                                List<QuotaAssociativaBari> d = listRef.getListQuote();
                                d.add(del);
                                listRef.setListQuote(d);
                            }
                        }
                    }else {
                        UiReferenti newRef = new UiReferenti();

                        List<QuotaAssociativaBari> d = new ArrayList<>();
                        d.add(del);

                        newRef.setNominativo(ref.getCompleteName());
                        newRef.setListQuote(d);
                        newRef.setComune(ref.getCity());
                        newRef.setImportoTot(getNewImport(ref,quotaAssoc));
                        listReferenti.add(newRef);
                    }
            }


         RistornoBariObject obj = new RistornoBariObject();
            obj.setListaQuote(list);
            obj.setListaReferenti(listReferenti);

        return obj;
    }

    private float getNewImport(Referenti ref, float quotaAssoc) {
        float NewImpot = ((quotaAssoc*ref.getProRataShare())/100);
        return NewImpot;
    }

    private boolean isCurrentReferenteInList(Referenti ref, List<UiReferenti> listReferenti) {
        boolean flag = false;

        for(UiReferenti lista : listReferenti){
            String r = lista.getNominativo();

            if(r.equals(ref.getCompleteName())){
                flag = true;
            }
        }

        return flag;
    }


    //FUNZIONE PER L'IMPORTAZIONE FILE CASSA EDILE
    private ExcelInfo importDataCassaEdile(ImportDeleghe importData, File temp) throws IOException {
        String file = getTempFile(importData.getFile1(), temp);

        ExcelReader reader = new ExcelReader(file, 0, 1 ,new ImportRistorniCassaEdileValidatorForBari());

        return reader.readFile();
    }

    //FUNZIONE PER LA VALIDAZIONE DEL FILE EXCEL CASSA EDILE
    private void validateExcelDataCassaEdile(ExcelInfo data) throws Exception {
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            throw new Exception(String.format("Un file contiene errori: %s  <br>",  data.getError()));
        }

        //prendo l'intestazione e verifico che sia la stessa che mi aspetto
        List<String> template  = new ArrayList<>();
        template.add("FISCALE");
        template.add("COGNOME");
        template.add("NOME");
        template.add("DATA NASCITA");
        template.add("LUOGO NASCITA");
        template.add("INDIRIZZO");
        template.add("CAP");
        template.add("COMUNE");
        template.add("PROVINCIA");
        template.add("TELEFONO");
        template.add("PROVENIENZA");
        template.add("SEMESTRE");
        template.add("ANNO");
        template.add("LIVELLO");
        template.add("MANSIONE");
        template.add("NUM.CELLULARE");
        template.add("ULT.MOV.");
        template.add("AZIENDA");
        template.add("CODICE FISCALE AZIENDA");
        template.add("RITENUTA ARRETRATA");
        template.add("RITENUTA CORRENTE");
        template.add("TOTALE");

        List<String> headers = data.getHeaderFields();
        String name = FilenameUtils.getName(data.getSourceFile());
        String err = headersContainsTemplate(headers, template);
        boolean equal = StringUtils.isEmpty(err);
        if (!equal){
            //recupero il nome del file

            throw new Exception(String.format("Inserire il file con la giusta intestazione,il file inserito non contiene le intestazioni richieste<br>: Campi mancanti: %s", err));
        }
        if (equal){
            //verifico che ci sia almeno una riga
            if (data.getOnlyValidRows().size() == 0){
                throw new Exception(String.format("Il file %s non contiene informazioni<br>", name));
            }

        }
    }


    //CREA LISTA PER CASSAEDILE
    private List<QuotaAssociativaBari> createListCassaEdile(ExcelInfo data) throws Exception {
        List<QuotaAssociativaBari> list = new ArrayList<>();

        for(RowData row : data.getOnlyValidRows()){
            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            String cf = row.getData().get("FISCALE").trim();
            float quota = Float.parseFloat(row.getData().get("TOTALE").trim());
            Lavoratore l = lavServ.findLavoratoreByFiscalCode(cf);
            if(l == null) {
                //se non esiste il lavoratore lo creo
                l = new Lavoratore();
                l.setSex("M");
                l.setFiscalcode(row.getData().get("FISCALE").trim());
                l.setBirthDate(ff.parse(row.getData().get("DATA NASCITA").trim()));
                l.setLivingProvince(row.getData().get("PROVINCIA").trim());
                l.setLivingCity(row.getData().get("COMUNE").trim());
                l.setCap(row.getData().get("CAP").trim());
                l.setName(row.getData().get("NOME").trim());
                l.setSurname(row.getData().get("COGNOME").trim());
                l.setAddress(row.getData().get("INDIRIZZO").trim());

                if (row.getData().containsKey("TELEFONO"))
                    if (row.getData().get("TELEFONO") != null)
                        l.setCellphone(row.getData().get("TELEFONO").toString().trim());


                lavServ.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);
            }

            List<DelegaBari> delBar = delBariServ.getAllWorkerDeleghe(l.getLid());
            QuotaAssociativaBari r = new QuotaAssociativaBari();
            if(delBar.size() > 0)
                r.setUltimaDelega(delBar.get(0));
            r.setLavoratore(l);
            r.setDelegheBari(delBar);
            r.setQuotaAssoc(quota);

            list.add(r);
        }

        return list;
    }



    //IMPORT DATA FOR EDILCASSSA
    private ExcelInfo importDataEdilCassa(ImportDeleghe importData, File temp) throws IOException {
        String file = getTempFile(importData.getFile1(), temp);

        ExcelReader reader = new ExcelReader(file, 0, 0 ,new ImportRistorniEdilCassaValidatorFoBari());

        return reader.readFile();
    }


    //VALIDATORE EXCEL DATA PER EDILCASSA
    private void validateExcelDataEdilCassa(ExcelInfo data) throws Exception {
        if (!fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(data.getError())){
            //recupero il nome del file
            String name = FilenameUtils.getName(data.getSourceFile());

            throw new Exception(String.format("Un file contiene errori: %s  <br>",  data.getError()));
        }

        //prendo l'intestazione e verifico che sia la stessa che mi aspetto
        List<String> template  = new ArrayList<>();
        template.add("Codice Lavoratore");
        template.add("Cognome");
        template.add("Nome");
        template.add("Data di nascita");
        template.add("Cod.Fisc.");
        template.add("Indirizzo");
        template.add("cap");
        template.add("citta");
        template.add("provincia");
        template.add("Sindacato");
        template.add("Descr. sind.");
        template.add("Importo grat");
        template.add("Cellulare");

        List<String> headers = data.getHeaderFields();
        String name = FilenameUtils.getName(data.getSourceFile());
        String err = headersContainsTemplate(headers, template);
        boolean equal = StringUtils.isEmpty(err);
        if (!equal){
            //recupero il nome del file

            throw new Exception(String.format("Inserire il file con la giusta intestazione,il file inserito non contiene le intestazioni richieste<br>: Campi mancanti: %s", err));
        }
        if (equal){
            //verifico che ci sia almeno una riga
            if (data.getOnlyValidRows().size() == 0){
                throw new Exception(String.format("Il file %s non contiene informazioni<br>", name));
            }

        }
    }


    //CREA LISTA PER EDILCASSA
    private List<QuotaAssociativaBari> createListEdilcassa(ExcelInfo data) throws Exception {
        List<QuotaAssociativaBari> list = new ArrayList<>();

        for(RowData row : data.getOnlyValidRows()){
            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            String cf = row.getData().get("Cod.Fisc.").trim();
            float quota = Float.parseFloat(row.getData().get("Importo grat").trim());
            Lavoratore l = lavServ.findLavoratoreByFiscalCode(cf);
            if(l == null) {
                //se non esiste il lavoratore lo creo
                l = new Lavoratore();
                l.setSex("M");
                l.setFiscalcode(row.getData().get("Cod.Fisc.").trim());
                l.setBirthDate(ff.parse(row.getData().get("Data di nascita").trim()));
                l.setLivingProvince(row.getData().get("provincia").trim());
                l.setLivingCity(row.getData().get("citta").trim());
                l.setCap(row.getData().get("cap").trim());
                l.setName(row.getData().get("Nome").trim());
                l.setSurname(row.getData().get("Cognome").trim());
                l.setAddress(row.getData().get("Indirizzo").trim());

                if (row.getData().containsKey("Cellulare"))
                    if (row.getData().get("Cellulare") != null)
                        l.setCellphone(row.getData().get("Cellulare").toString().trim());


                lavServ.saveOrUpdate(((User) sec.getLoggedUser()).getLid(),l);
            }

            List<DelegaBari> delBar = delBariServ.getAllWorkerDeleghe(l.getLid());
            QuotaAssociativaBari r = new QuotaAssociativaBari();
            if(delBar.size() > 0)
                r.setUltimaDelega(delBar.get(0));
            r.setLavoratore(l);
            r.setDelegheBari(delBar);
            r.setQuotaAssoc(quota);

            list.add(r);
        }

        return list;
    }


    //FUNZIONI PER INSERIRE IL FILE ED OTTENERE IL PATH
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
    //

    //FUNZIONE PER VALIDATE FILE BARI RISTORNO
    private void baseValidate(ImportDeleghe importData) throws Exception {

        if (importData == null){
            throw new Exception("Nessun file indicato per l'importazione; Null");
        }

        //verifico che ci sia almeno un file
        if (fr.opensagres.xdocreport.core.utils.StringUtils.isEmpty(importData.getFile1())){
            throw new Exception("Nessun file indicato per l'importazione");
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



    public String importRistorno(ImportRistorniDelegheBari impotazioneRistorno) throws Exception {
        final ErrorsCounter errors = new ErrorsCounter();

        LoadRequest req = LoadRequest.build().
                filter("ente", impotazioneRistorno.getParithetic())
                .filter("anno", impotazioneRistorno.getCompetenceYear());

        Ristorno r = ristornoRep.find(req)
                .findFirst().orElse(null);
        if (r != null)
            throw new Exception("Ristorno già importato per il l'anno scelto");

//        List<UiReferenti> list = impotazioneRistorno.getReferentiList();

        final ObjectMapper mapper = new ObjectMapper();
        String serialized = mapper.writeValueAsString(impotazioneRistorno.getQuoteAssocList());
        int a = serialized.length();

        Ristorno ristorno = Ristorno.createFromImportazione(impotazioneRistorno,((User) sec.getLoggedUser()).getCompany().getLid(), serialized);

        List<RistornoItem> list = retriveListaRistornoItem(impotazioneRistorno.getReferentiList());



        ristornoRep.executeCommand(new Command() {
            @Override
            public void execute() {

                Session s = ristornoRep.getSession();
                Transaction tx = s.beginTransaction();
                try {

                    //inserisco tutto nel db
                    s.saveOrUpdate(ristorno);

                    for (RistornoItem dettaglioRefente : list) {

                        dettaglioRefente.setIdRistorno(ristorno.getLid());
                    }

                    //imposto la chiave esterna per ogni dettaglio
                    for (RistornoItem dettaglioRefente : list) {
                        try{
                            s.saveOrUpdate(dettaglioRefente);
                        }catch(Exception ex){
                            errors.incrementErrorNumber();
                            ex.printStackTrace();
                        }
                    }

                    tx.commit();

                } catch (Exception ex) {
                    errors.incrementErrorNumber();
                    ex.printStackTrace();
                    try {
//                        logger.log(filename, "", "Errore in importazione : " + ex.getMessage() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tx.rollback();
                } finally {
                    s.close();
                }




            }
        });
        return "Importazione terminata con " + errors.getErrors() + " errori";

    }

    private List<RistornoItem> retriveListaRistornoItem(List<UiReferenti> referentiList) throws JsonProcessingException {
        List<RistornoItem> lista = new ArrayList<>();
        for(UiReferenti ref : referentiList){
            Referenti r = refRep.find(LoadRequest.build().filter("completeName", ref.getNominativo())).findFirst().orElse(null);

            RistornoItem item = new RistornoItem();
            item.setImportoTot(ref.getImportoTot());
            item.setReferente(r);
            final ObjectMapper mapper = new ObjectMapper();
            String serialized = mapper.writeValueAsString(ref.getListQuote());
            item.setListQuote(serialized);

            lista.add(item);
        }
       return lista;
    }


    public void deleteRistorno(long idRiepilogoRistorno) {

//        ottengo il ristorno
        Ristorno r = ristornoRep.get(idRiepilogoRistorno).orElse(null);
        if (r != null){
            //procedo alla cancellazione
            ristornoRep.executeCommand(new Command() {
                @Override
                public void execute() {
                    Session s = ristornoRep.getSession();
                    Transaction tx = s.beginTransaction();
                    try {
                        //rimuovo tutti i dettagli per un determinato ristorno
                        s.createSQLQuery(String.format("Delete from fenealweb_ristornibari where id = %d", idRiepilogoRistorno)).executeUpdate();

                        s.createSQLQuery(String.format("Delete from fenealweb_ristornobariitem where idRistorno = %d", idRiepilogoRistorno)).executeUpdate();



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





}
