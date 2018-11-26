package applica.feneal.services.impl.report;

import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheSearchParams;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.services.DelegheService;
import applica.feneal.services.ReportMagazzinoDelegheService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.xalan.xsltc.runtime.Hashtable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import java.io.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fgran on 11/05/2016.
 */
@Service
public class ReportMagazzinoDelegheServiceImpl implements ReportMagazzinoDelegheService {

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private MagazzinoDelegheRepository magazzDelRep;

    @Autowired
    private DelegheService delServ;


    @Autowired
    private SectorRepository secRep;

    @Autowired
    private Security sec;

    @Override
    public List<MagazzinoDelega> retrieveMagazzinoDeleghe(UiMagazzinoDelegheSearchParams params) {

        String province = params.getProvince();
        String parithetic = params.getParithetic();
        String collaborator = params.getCollaborator();


        //las richiesta dell'abruzzo
        //consiste nel notificare accanto ad ogni delega trovata quali sono quelle
        //che per uno stesso ente sono registrate in piu province
        //pertanto eseguo la prima query per identificare i risultati finali

        //ed una seconda query che non terrà conto della provincia per effettuare un confronto
        //con i risultati prima ottenuti:
        //voglio tutte le deleghe di teramo per l'edilcassa
        //e su ognuna di queste dleeghe voglio poter sapere se ce anche una sola altra delega
        //a pescara, cieti o laquila

        //posso adesso fare la query tenedo conto della provincia
        LoadRequest req = LoadRequest.build();

        if (!StringUtils.isEmpty(province)){
            Integer proId = Integer.parseInt(province);
            Filter f1 = new Filter("province.id", proId, Filter.EQ);
            req.getFilters().add(f1);
        }

        if (!StringUtils.isEmpty(parithetic)){
            Filter f2 = new Filter("paritethic", Long.parseLong(parithetic), Filter.EQ);
            req.getFilters().add(f2);
        }

        if (!StringUtils.isEmpty(collaborator)){
            Filter f3 = new Filter("collaboratore", Long.parseLong(collaborator), Filter.EQ);
            req.getFilters().add(f3);
        }

        //creo adesso la seconda query che richiede i dati per le altre province
        //nota il filtro NE
        LoadRequest req1 = LoadRequest.build();

        if (!StringUtils.isEmpty(province)){
            Integer proId = Integer.parseInt(province);
            Filter f1 = new Filter("province.id", proId, Filter.NE);
            req1.getFilters().add(f1);
        }

        if (!StringUtils.isEmpty(parithetic)){
            Filter f2 = new Filter("paritethic", Long.parseLong(parithetic), Filter.EQ);
            req1.getFilters().add(f2);
        }

        if (!StringUtils.isEmpty(collaborator)){
            Filter f3 = new Filter("collaboratore", Long.parseLong(collaborator), Filter.EQ);
            req1.getFilters().add(f3);
        }



        List<MagazzinoDelega> del = magazzDelRep.find(req).getRows();
        List<MagazzinoDelega> del1 = magazzDelRep.find(req1).getRows();


        //adesso ciclando sui risultati (del) per ognuno di loro cerco nella lista delle deleghe delle altre
        //provicne se hanno lo stesso lavoratore...
        for (MagazzinoDelega magazzinoDelega : del) {

            //creo una variabile hash che contenga se gia non esistono le chiavi (province)
            java.util.Hashtable<String,String> t = new java.util.Hashtable<>();

            for (MagazzinoDelega delega : del1) {
                if (delega.getLavoratore().getLid() == magazzinoDelega.getLavoratore().getLid()){
                    if (!t.containsKey(delega.getProvince().getDescription())){
                        t.put(delega.getProvince().getDescription(), "");
                    }
                }

                //eseguito l'intero ciclo nella hash table mi ritrovo tra le chiavi tutte le possibili province
                //che hanno una delega per lo stesso lavoratore e lo stesso ente
                magazzinoDelega.setOtherparithetics(extractStringFromHash(t));


            }

        }


        return del;

    }

    private String extractStringFromHash(java.util.Hashtable<String,String> t) {



        List<String> list = t.keySet().stream().collect(Collectors.toList());

        if (list.size() == 0)
            return "";

        Collections.sort(list);



        return String.join(", ", list);
    }

    @Override
    public List<MagazzinoDelega> retrieveDistinctMagazzinoDeleghe(UiMagazzinoDelegheSearchParams params) {
        List<MagazzinoDelega> l = retrieveMagazzinoDeleghe(params);


        //devo adesso recuperare una sola bozza delega per utente
        HashMap<Long, MagazzinoDelega> delegheDaGenerare = new HashMap<>();
        for (MagazzinoDelega magazzinoDelega : l) {
            if (!delegheDaGenerare.containsKey(magazzinoDelega.getLavoratore().getLid()))
                delegheDaGenerare.put(magazzinoDelega.getLavoratore().getLid(), magazzinoDelega);
        }

        List<MagazzinoDelega> result = new ArrayList<>();

        //adesso ho una hashap con tutte le deleghe da generare
        for (MagazzinoDelega magazzinoDelega : delegheDaGenerare.values()) {
            result.add(magazzinoDelega);
        }

        return result;

    }

    @Override
    public String generateDeleghe(List<MagazzinoDelega> del) throws Exception {

        for (MagazzinoDelega magazzinoDelega : del) {
            generateDelegaFromMagazzino(magazzinoDelega);
        }

        //per ogni elemento nel magazzoino deleghe creo una riga excel per popolarne il file
        return createExcelFile(del);

    }

    private FenealgestUtils createFenealgestUtilsService() {
        FenealgestUtils svc = null;
        ExportTessereResponse result = null;
        try {
            svc = new FenealgestUtilsStub(null,optMan.get("applica.fenealgestutils"));
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        return svc;
    }
    private String createExcelFile(List<MagazzinoDelega> del) throws IOException {


        FenealgestUtils svc = createFenealgestUtilsService();

        ExportDocumentToExcel f = createExcelDocument(del);

        ExportDocumentToExcelResponse result = svc.exportDocumentToExcel(f);

        DataHandler webResult = result.getExportDocumentToExcelResult();


        return extractFile(webResult);
    }

    private String extractFile(DataHandler webResult) throws IOException {
        InputStream inputStream = webResult.getInputStream();
        //recupro una cartella temporanera
        File nn = File.createTempFile("generaDeleghe", ".xlsx");



        nn.createNewFile();
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

    private ExportDocumentToExcel createExcelDocument(List<MagazzinoDelega> del) {

        ExportDocumentToExcel doc = new ExportDocumentToExcel();

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRows(del));

        doc.setDocument(document);
        return  doc;
    }

    private ArrayOfExcelRow createRows(List<MagazzinoDelega> del) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (MagazzinoDelega magazzinoDelega : del) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createProperties(magazzinoDelega));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelProperty createProperties(MagazzinoDelega magazzinoDelega) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Cognome");
        name.setValue(magazzinoDelega.getLavoratore().getSurname());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Nome");
        name1.setValue(magazzinoDelega.getLavoratore().getName());
        name1.setPriority(2);
        props.addExcelProperty(name1);


        ExcelProperty fisclaCode = new ExcelProperty();
        fisclaCode.setName("Codice Fiscale");
        fisclaCode.setValue(magazzinoDelega.getLavoratore().getFiscalcode());
        fisclaCode.setPriority(3);
        props.addExcelProperty(fisclaCode);

        ExcelProperty data = new ExcelProperty();
        data.setName("Data di nascita");
        data.setValue(magazzinoDelega.getLavoratore().getBirthDate().toString());
        data.setPriority(4);
        props.addExcelProperty(data);

        ExcelProperty com1 = new ExcelProperty();
        com1.setName("Provincia residenza");
        com1.setValue(magazzinoDelega.getLavoratore().getLivingProvince());
        com1.setPriority(5);
        props.addExcelProperty(com1);


        ExcelProperty com = new ExcelProperty();
        com.setName("Comune residenza");
        com.setValue(magazzinoDelega.getLavoratore().getLivingCity());
        com.setPriority(6);
        props.addExcelProperty(com);

        ExcelProperty add = new ExcelProperty();
        add.setName("Indirizzo");
        add.setValue(magazzinoDelega.getLavoratore().getAddress());
        add.setPriority(7);
        props.addExcelProperty(add);

        ExcelProperty cap = new ExcelProperty();
        cap.setName("Cap");
        cap.setValue(magazzinoDelega.getLavoratore().getCap());
        cap.setPriority(8);
        props.addExcelProperty(cap);


        return props;
    }

    private void generateDelegaFromMagazzino(MagazzinoDelega magazzinoDelega) throws Exception {

        //qui devo creare una nuova delega e cancellare quella attuale
        Delega d = new Delega();
        d.setWorker(magazzinoDelega.getLavoratore());
        d.setCollaborator(magazzinoDelega.getCollaboratore());
        d.setProvince(magazzinoDelega.getProvince());
        d.setParitethic(magazzinoDelega.getParitethic());

        LoadRequest req = LoadRequest.build().filter("type", "EDILE");
        Sector s = secRep.find(req).findFirst().orElseThrow(() ->  new Exception("Settore edile non esistente"));
        d.setSector(s);

        d.setDocumentDate(new Date());

        //salvo la delega e cancello il magazzino

        delServ.saveOrUpdate(((User) sec.getLoggedUser()).getLid(), d);
        //cancello il magazzinoDelega
        magazzDelRep.delete(magazzinoDelega.getLid());

    }


}
