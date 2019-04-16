package applica.feneal.admin.utils;

import applica.feneal.admin.viewmodel.reports.UiIscrizione;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.model.dbnazionale.DelegaNazionale;
import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.framework.library.options.OptionsManager;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by fgran on 09/06/2016.
 */
@Component
public class LiberiExcelExporter {

    public static final String db_nazionale_type = "dbnazionale";
    public static final String delega_type = "delega";
    public static final String prevedi_type = "prevedi";
    public static final String altrosindacato_type = "altro";

    private String type = db_nazionale_type;


    @Autowired
    private OptionsManager optMan;
    public   String createExcelFile(List<UiLibero> liberi, String type) throws IOException {

        if (!type.equals(db_nazionale_type) && !type.equals(delega_type) &&
                !type.equals(prevedi_type) && !type.equals(altrosindacato_type))
            type = db_nazionale_type;


        FenealgestUtils svc = createFenealgestUtilsService();

        ExportDocumentToExcel f = null;

        f = createExcelDocument(liberi, type);

        ExportDocumentToExcelResponse result = svc.exportDocumentToExcel(f);

        DataHandler webResult = result.getExportDocumentToExcelResult();


        return extractFile(webResult);
    }

//    public   String createExcelFile(List<UiLibero> liberi) throws IOException {
//
//
//
//        FenealgestUtils svc = createFenealgestUtilsService();
//
//        ExportDocumentToExcel f = createExcelDocument(liberi);
//
//        ExportDocumentToExcelResponse result = svc.exportDocumentToExcel(f);
//
//        DataHandler webResult = result.getExportDocumentToExcelResult();
//
//
//        return extractFile(webResult);
//    }

    private ExportDocumentToExcel createExcelDocument(List<UiLibero> liberi, String type) {
        ExportDocumentToExcel doc = new ExportDocumentToExcel();

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRows(liberi, type));

        doc.setDocument(document);
        return  doc;
    }

    private ArrayOfExcelRow createRows(List<UiLibero> liberi, String type) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (UiLibero lib : liberi) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createProperties(lib));
            if (type.equals(LiberiExcelExporter.db_nazionale_type))
                row.setDocument(createIscrizioniSubDocument(lib.getIscrizioni()));
            else if (type.equals(LiberiExcelExporter.altrosindacato_type))
                row.setDocument(createAltriSindacatiSubDocument(lib.getNonIscrizioni()));
            else if (type.equals(LiberiExcelExporter.delega_type))
                row.setDocument(createDelegheSubDocument(lib.getDelegheNazionali()));
            else
            {
                row.setDocument(createPrevediSubDocument(lib.getPrevedi()));
            }
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ExcelDocument createIscrizioniSubDocument(List<UiIscrizione> iscrizioni) {
        if (iscrizioni == null)
            return null;

        if (iscrizioni.size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForIscrizioni(iscrizioni));
        return  document;


    }


    private ExcelDocument createDelegheSubDocument(List<DelegaNazionale> iscrizioni) {
        if (iscrizioni == null)
            return null;

        if (iscrizioni.size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForDeleghe(iscrizioni));
        return  document;


    }

    private ExcelDocument createAltriSindacatiSubDocument(List<LiberoDbNazionale> iscrizioni) {
        if (iscrizioni == null)
            return null;

        if (iscrizioni.size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForAltriSindacati(iscrizioni));
        return  document;


    }

    private ExcelDocument createPrevediSubDocument(List<LavoratorePrevedi> iscrizioni) {
        if (iscrizioni == null)
            return null;

        if (iscrizioni.size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForPrevedi(iscrizioni));
        return  document;


    }

    private ArrayOfExcelRow createRowsForIscrizioni(List<UiIscrizione> iscrizioni) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (UiIscrizione lib : iscrizioni) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createPropertiesForIscrizione(lib));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelRow createRowsForDeleghe(List<DelegaNazionale> iscrizioni) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (DelegaNazionale lib : iscrizioni) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createPropertiesForDeleghe(lib));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelRow createRowsForAltriSindacati(List<LiberoDbNazionale> iscrizioni) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (LiberoDbNazionale lib : iscrizioni) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createPropertiesForAltriSindacati(lib));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelRow createRowsForPrevedi(List<LavoratorePrevedi> iscrizioni) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (LavoratorePrevedi
                lib : iscrizioni) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createPropertiesForPrevedi(lib));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelProperty createPropertiesForIscrizione(UiIscrizione lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Regione");
        name.setValue(lib.getNomeRegione());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Provincia");
        name1.setValue(lib.getNomeProvincia());
        name1.setPriority(2);
        props.addExcelProperty(name1);


        ExcelProperty fisclaCode = new ExcelProperty();
        fisclaCode.setName("Settore");
        fisclaCode.setValue(lib.getSettore());
        fisclaCode.setPriority(3);
        props.addExcelProperty(fisclaCode);

        ExcelProperty data = new ExcelProperty();
        data.setName("Ente");
        data.setValue(lib.getEnte());
        data.setPriority(4);
        props.addExcelProperty(data);

        ExcelProperty com1 = new ExcelProperty();
        com1.setName("Azienda");
        com1.setValue(lib.getAzienda());
        com1.setPriority(5);
        props.addExcelProperty(com1);

        ExcelProperty com2 = new ExcelProperty();
        com2.setName("Partita Iva");
        com2.setValue(lib.getPiva());
        com2.setPriority(6);
        props.addExcelProperty(com2);


        ExcelProperty com = new ExcelProperty();
        com.setName("Livello");
        com.setValue(lib.getLivello());
        com.setPriority(7);
        props.addExcelProperty(com);

        ExcelProperty com3 = new ExcelProperty();
        com3.setName("Quota");
        com3.setValue(String.valueOf(lib.getQuota()));
        com3.setPriority(8);
        props.addExcelProperty(com3);


        ExcelProperty com4 = new ExcelProperty();
        com4.setName("Periodo");
        com4.setValue(String.valueOf(lib.getPeriodo()));
        com4.setPriority(9);
        props.addExcelProperty(com4);

        ExcelProperty add = new ExcelProperty();
        add.setName("Anno");
        add.setValue(String.valueOf(lib.getAnno()));
        add.setPriority(10);
        props.addExcelProperty(add);

        ExcelProperty cap = new ExcelProperty();
        cap.setName("Contratto");
        cap.setValue(lib.getContratto());
        cap.setPriority(11);
        props.addExcelProperty(cap);





        return props;
    }


    private ArrayOfExcelProperty createPropertiesForDeleghe(DelegaNazionale lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Provincia");
        name1.setValue(lib.getProvince());
        name1.setPriority(1);
        props.addExcelProperty(name1);

        ExcelProperty ente = new ExcelProperty();
        ente.setName("Ente");
        ente.setValue(lib.getEnte());
        ente.setPriority(2);
        props.addExcelProperty(ente);

        ExcelProperty stato = new ExcelProperty();
        stato.setName("Stato");
        stato.setValue(lib.getState());
        stato.setPriority(3);
        props.addExcelProperty(stato);

        ExcelProperty scan = new ExcelProperty();
        scan.setName("Scansione");
        scan.setValue(lib.getNomeattachment());
        scan.setPriority(4);
        props.addExcelProperty(scan);

        ExcelProperty operatore = new ExcelProperty();
        operatore.setName("Operatore");
        operatore.setValue(lib.getOperator());
        operatore.setPriority(5);
        props.addExcelProperty(operatore);

        ExcelProperty data_acc = new ExcelProperty();
        data_acc.setName("Data Accreditamento");
        data_acc.setValue(String.valueOf(lib.getAcceptDate()));
        data_acc.setPriority(6);
        props.addExcelProperty(data_acc);

        ExcelProperty data_canc = new ExcelProperty();
        data_canc.setName("Data Cancellazione");
        data_canc.setValue(String.valueOf(lib.getCancelDate()));
        data_canc.setPriority(7);
        props.addExcelProperty(data_canc);

        ExcelProperty data_cess = new ExcelProperty();
        data_cess.setName("Data Cessazione");
        data_cess.setValue(String.valueOf(lib.getRevokeDate()));
        data_cess.setPriority(7);
        props.addExcelProperty(data_cess);

        ExcelProperty ann = new ExcelProperty();
        ann.setName("Annotazioni");
        ann.setValue(String.valueOf(lib.getNotes()));
        ann.setPriority(9);
        props.addExcelProperty(ann);






        return props;
    }


    private ArrayOfExcelProperty createPropertiesForAltriSindacati(LiberoDbNazionale lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Provincia");
        name.setValue(lib.getNomeProvinciaFeneal());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty ente = new ExcelProperty();
        ente.setName("Ente");
        ente.setValue(lib.getEnte());
        ente.setPriority(2);
        props.addExcelProperty(ente);

        ExcelProperty azienda = new ExcelProperty();
        azienda.setName("Azienda");
        azienda.setValue(lib.getCurrentAzienda());
        azienda.setPriority(3);
        props.addExcelProperty(azienda);

        ExcelProperty libero_al = new ExcelProperty();
        libero_al.setName("Libero al");
        libero_al.setValue(String.valueOf(lib.getLiberoAl()));
        libero_al.setPriority(4);
        props.addExcelProperty(libero_al);

        ExcelProperty iscrittoA = new ExcelProperty();
        iscrittoA.setName("Iscritto A");
        iscrittoA.setValue(lib.getIscrittoA());
        iscrittoA.setPriority(5);
        props.addExcelProperty(iscrittoA);

        return props;
    }


    private ArrayOfExcelProperty createPropertiesForPrevedi(LavoratorePrevedi lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Regione");
        name.setValue(lib.getCassaEdileRegione());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Cassa Edille");
        name1.setValue(lib.getCassaEdile());
        name1.setPriority(2);
        props.addExcelProperty(name1);

        ExcelProperty inq = new ExcelProperty();
        inq.setName("Inquadramento");
        inq.setValue(lib.getInquadramento());
        inq.setPriority(3);
        props.addExcelProperty(inq);

        ExcelProperty tipoA = new ExcelProperty();
        tipoA.setName("Tipo Adesione");
        tipoA.setValue(lib.getTipoAdesione());
        tipoA.setPriority(4);
        props.addExcelProperty(tipoA);

        ExcelProperty anno = new ExcelProperty();
        anno.setName("Anno");
        anno.setValue(String.valueOf(lib.getAnno()));
        anno.setPriority(5);
        props.addExcelProperty(anno);


        return props;
    }

    private ArrayOfExcelProperty createProperties(UiLibero lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Cognome");
        name.setValue(lib.getLavoratoreCognome());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Nome");
        name1.setValue(lib.getLavoratoreNome());
        name1.setPriority(2);
        props.addExcelProperty(name1);


        ExcelProperty fisclaCode = new ExcelProperty();
        fisclaCode.setName("Codice Fiscale");
        fisclaCode.setValue(lib.getLavoratoreCodiceFiscale());
        fisclaCode.setPriority(3);
        props.addExcelProperty(fisclaCode);

        ExcelProperty data = new ExcelProperty();
        data.setName("Data di nascita");

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");

        data.setValue(ff.format(lib.getLavoratoreDataNascita()));
        data.setPriority(4);
        props.addExcelProperty(data);

        ExcelProperty com1 = new ExcelProperty();
        com1.setName("Nazione nascita");
        com1.setValue(lib.getLavoratoreNazionalita());
        com1.setPriority(5);
        props.addExcelProperty(com1);

        ExcelProperty com2 = new ExcelProperty();
        com2.setName("Provincia nascita");
        com2.setValue(lib.getLavoratoreProvinciaNascita());
        com2.setPriority(6);
        props.addExcelProperty(com2);


        ExcelProperty com = new ExcelProperty();
        com.setName("Comune nascita");
        com.setValue(lib.getLavoratoreLuogoNascita());
        com.setPriority(7);
        props.addExcelProperty(com);

        ExcelProperty com3 = new ExcelProperty();
        com3.setName("Provincia residenza");
        com3.setValue(lib.getLavoratoreProvinciaResidenza());
        com3.setPriority(8);
        props.addExcelProperty(com3);


        ExcelProperty com4 = new ExcelProperty();
        com4.setName("Comune residenza");
        com4.setValue(lib.getLavoratoreCittaResidenza());
        com4.setPriority(9);
        props.addExcelProperty(com4);

        ExcelProperty add = new ExcelProperty();
        add.setName("Indirizzo");
        add.setValue(lib.getLavoratoreIndirizzo());
        add.setPriority(10);
        props.addExcelProperty(add);

        ExcelProperty cap = new ExcelProperty();
        cap.setName("Cap");
        cap.setValue(lib.getLavoratoreCap());
        cap.setPriority(11);
        props.addExcelProperty(cap);


        ExcelProperty cap1 = new ExcelProperty();
        cap1.setName("Cellulare");
        cap1.setValue(lib.getLavoratoreCellulare());
        cap1.setPriority(11);
        props.addExcelProperty(cap1);

        ExcelProperty tel = new ExcelProperty();
        tel.setName("Telefono");
        tel.setValue(lib.getLavoratoreTelefono());
        tel.setPriority(12);
        props.addExcelProperty(tel);


        ExcelProperty cap11 = new ExcelProperty();
        cap11.setName("Azienda");
        cap11.setValue(lib.getAziendaRagioneSociale());
        cap11.setPriority(13);
        props.addExcelProperty(cap11);


        ExcelProperty ente = new ExcelProperty();
        ente.setName("Ente");
        ente.setValue(lib.getLiberoEnteBilaterale());
        ente.setPriority(14);
        props.addExcelProperty(ente);

        ExcelProperty iscrittoa = new ExcelProperty();
        iscrittoa.setName("Ente");
        iscrittoa.setValue(lib.getLiberoIscrittoA());
        iscrittoa.setPriority(15);
        props.addExcelProperty(iscrittoa);


        return props;
    }

    private String extractFile(DataHandler webResult) throws IOException {
        InputStream inputStream = webResult.getInputStream();
        //recupro una cartella temporanera
        File nn = File.createTempFile("stampaLiberi", ".xlsx");



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

    private  FenealgestUtils createFenealgestUtilsService() {

        FenealgestUtils svc = null;
        ExportTessereResponse result = null;
        try {
            svc = new FenealgestUtilsStub(null,optMan.get("applica.fenealgestutils"));
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        return svc;
    }
}
