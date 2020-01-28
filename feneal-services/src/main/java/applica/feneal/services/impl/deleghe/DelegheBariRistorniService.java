package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.model.core.deleghe.ImportDeleghe;
import applica.feneal.domain.model.core.deleghe.ImportRistorniCassaEdileValidatorForBari;
import applica.feneal.domain.model.core.deleghe.ImportRistorniEdilCassaValidatorFoBari;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.deleghe.bari.RistornoCassaEdileFilter;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.ristorniEdilizia.RiepilogoRistornoPerLavoratore;
import applica.feneal.services.LavoratoreService;
import applica.framework.fileserver.FileServer;
import applica.framework.management.excel.ExcelInfo;
import applica.framework.management.excel.ExcelReader;
import applica.framework.management.csv.RowData;
import applica.feneal.domain.model.User;
import applica.framework.security.Security;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
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
    private Security sec;

    public List<RiepilogoRistornoPerLavoratore> retriveListaRiepilogoRistorni(RistornoCassaEdileFilter params) throws Exception {
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
        List<RiepilogoRistornoPerLavoratore> list = new ArrayList<>();
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
       

        return list;
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
    private List<RiepilogoRistornoPerLavoratore> createListCassaEdile(ExcelInfo data) throws Exception {
        List<RiepilogoRistornoPerLavoratore> list = new ArrayList<>();

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
            RiepilogoRistornoPerLavoratore r = new RiepilogoRistornoPerLavoratore();
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
    private List<RiepilogoRistornoPerLavoratore> createListEdilcassa(ExcelInfo data) throws Exception {
        List<RiepilogoRistornoPerLavoratore> list = new ArrayList<>();

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
            RiepilogoRistornoPerLavoratore r = new RiepilogoRistornoPerLavoratore();
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
}
