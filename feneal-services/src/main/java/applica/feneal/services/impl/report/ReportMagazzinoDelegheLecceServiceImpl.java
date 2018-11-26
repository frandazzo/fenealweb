package applica.feneal.services.impl.report;

import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheLecceRepository;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheLecceSearchParams;
import applica.feneal.domain.model.core.servizi.MagazzinoDelegaLecce;
import applica.feneal.services.DelegheService;
import applica.feneal.services.ReportMagazzinoDelegheLecceService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class ReportMagazzinoDelegheLecceServiceImpl  implements ReportMagazzinoDelegheLecceService{

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private MagazzinoDelegheLecceRepository magazzDelRep;

    @Autowired
    private DelegheService delServ;


    @Autowired
    private SectorRepository secRep;

    @Autowired
    private Security sec;



    @Override
    public List<MagazzinoDelegaLecce> retrieveMagazzinoDeleghe(UiMagazzinoDelegheLecceSearchParams params) {
        String province = params.getProvince();
        String parithetic = params.getParithetic();
        String collaborator = params.getCollaborator();


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

        List<MagazzinoDelegaLecce> del = magazzDelRep.find(req).getRows();
        return del;
    }

    @Override
    public List<MagazzinoDelegaLecce> retrieveDistinctMagazzinoDeleghe(UiMagazzinoDelegheLecceSearchParams params) {
        List<MagazzinoDelegaLecce> l = retrieveMagazzinoDeleghe(params);


        //devo adesso recuperare una sola bozza delega per utente
        HashMap<Long, MagazzinoDelegaLecce> delegheDaGenerare = new HashMap<>();
        for (MagazzinoDelegaLecce magazzinoDelega : l) {
            if (!delegheDaGenerare.containsKey(magazzinoDelega.getLavoratore().getLid()))
                delegheDaGenerare.put(magazzinoDelega.getLavoratore().getLid(), magazzinoDelega);
        }

        List<MagazzinoDelegaLecce> result = new ArrayList<>();

        //adesso ho una hashap con tutte le deleghe da generare
        for (MagazzinoDelegaLecce magazzinoDelega : delegheDaGenerare.values()) {
            result.add(magazzinoDelega);
        }

        return result;
    }

    @Override
    public String generateDeleghe(List<MagazzinoDelegaLecce> del) throws Exception {
        for (MagazzinoDelegaLecce magazzinoDelega : del) {
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
    private String createExcelFile(List<MagazzinoDelegaLecce> del) throws IOException {


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

    private ExportDocumentToExcel createExcelDocument(List<MagazzinoDelegaLecce> del) {

        ExportDocumentToExcel doc = new ExportDocumentToExcel();

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRows(del));

        doc.setDocument(document);
        return  doc;
    }

    private ArrayOfExcelRow createRows(List<MagazzinoDelegaLecce> del) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (MagazzinoDelegaLecce magazzinoDelega : del) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createProperties(magazzinoDelega));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelProperty createProperties(MagazzinoDelegaLecce magazzinoDelega) {
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

    private void generateDelegaFromMagazzino(MagazzinoDelegaLecce magazzinoDelega) throws Exception {

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
