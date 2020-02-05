package applica.feneal.admin.utils;


import applica.feneal.admin.viewmodel.reports.UiIscrizione;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.ristorniEdilizia.QuotaAssociativaBari;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.framework.library.options.OptionsManager;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class RistroniBariExcelExporter {

    @Autowired
    private OptionsManager optMan;

    public   String createExcelFile(List<QuotaAssociativaBari> listaQuote) throws IOException {




        FenealgestUtils svc = createFenealgestUtilsService();

        ExportDocumentToExcel f = null;

        f = createExcelDocument(listaQuote);

        ExportDocumentToExcelResponse result = svc.exportDocumentToExcel(f);

        DataHandler webResult = result.getExportDocumentToExcelResult();


        return extractFile(webResult);
    }

    private String extractFile(DataHandler webResult) throws IOException {
        InputStream inputStream = webResult.getInputStream();
        //recupro una cartella temporanera
        File nn = File.createTempFile("stampaRistorni", ".xlsx");



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

    private ExportDocumentToExcel createExcelDocument(List<QuotaAssociativaBari> listaQuote) {
        ExportDocumentToExcel doc = new ExportDocumentToExcel();

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRows(listaQuote));

        doc.setDocument(document);
        return  doc;
    }

    private ArrayOfExcelRow createRows(List<QuotaAssociativaBari> listaQuote) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        for (QuotaAssociativaBari q : listaQuote) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createProperties(q));
                row.setDocument(createRistorniSubDocument(q));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ExcelDocument createRistorniSubDocument(QuotaAssociativaBari quota) {
        if (quota.getUltimaDelega() == null)
            return null;

        if (quota.getDelegheBari().size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForDettaglioRistorno(quota));
        return  document;


    }

    private ArrayOfExcelRow createRowsForDettaglioRistorno(QuotaAssociativaBari quota) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        List<DelegaBari> list = quota.getDelegheBari();

        for (DelegaBari l : list) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createPropertiesForDelegaBari(l));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelProperty createPropertiesForDelegaBari(DelegaBari l) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty firm = new ExcelProperty();
        firm.setName("Azienda");
        firm.setValue(l.getWorkerCompany() != null ? l.getWorkerCompany().getCity() : "");
        firm.setPriority(1);
        props.addExcelProperty(firm);

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");

        ExcelProperty dateProtocol = new ExcelProperty();
        dateProtocol.setName("Data protocollo");
        dateProtocol.setValue(l.getProtocolDate() != null ? ff.format(l.getProtocolDate()) : "");
        dateProtocol.setPriority(2);
        props.addExcelProperty(dateProtocol);


        ExcelProperty numProtocollo = new ExcelProperty();
        numProtocollo.setName("Num. Protocollo");
        numProtocollo.setValue(l.getProtocolNumber());
        numProtocollo.setPriority(3);
        props.addExcelProperty(numProtocollo);

        ExcelProperty dateIscrizione = new ExcelProperty();
        dateIscrizione.setName("Iscrizione");
        dateIscrizione.setValue(l.getSignup());
        dateIscrizione.setPriority(4);
        props.addExcelProperty(dateIscrizione);

        ExcelProperty revoca = new ExcelProperty();
        revoca.setName("Revoca");
        revoca.setValue(l.getRevocation());
        revoca.setPriority(5);
        props.addExcelProperty(revoca);

        ExcelProperty doppione = new ExcelProperty();
        doppione.setName("Doppione");
        doppione.setValue(l.getDuplicate());
        doppione.setPriority(6);
        props.addExcelProperty(doppione);


        ExcelProperty dec = new ExcelProperty();
        dec.setName("Decorrenza");
        dec.setValue(l.getEffectDate() != null ? ff.format(l.getEffectDate()) : "");
        dec.setPriority(7);
        props.addExcelProperty(dec);

        ExcelProperty ultMov = new ExcelProperty();
        ultMov.setName("Ult. mov.");
        ultMov.setValue(String.valueOf(l.getLastMovement()));
        ultMov.setPriority(8);
        props.addExcelProperty(ultMov);


        ExcelProperty referente = new ExcelProperty();
        referente.setName("Referente");
        referente.setValue(l.getManagementContact() != null ? l.getManagementContact().getCompleteName() : "");
        referente.setPriority(9);
        props.addExcelProperty(referente);




        return props;
    }

    private ArrayOfExcelProperty createProperties(QuotaAssociativaBari lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Cognome");
        name.setValue(lib.getLavoratore().getSurname());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Nome");
        name1.setValue(lib.getLavoratore().getName());
        name1.setPriority(2);
        props.addExcelProperty(name1);


        ExcelProperty fisclaCode = new ExcelProperty();
        fisclaCode.setName("Codice Fiscale");
        fisclaCode.setValue(lib.getLavoratore().getFiscalcode());
        fisclaCode.setPriority(3);
        props.addExcelProperty(fisclaCode);


        ExcelProperty com1 = new ExcelProperty();
        com1.setName("Importo Quota");
        com1.setValue(String.valueOf(lib.getQuotaAssoc()));
        com1.setPriority(5);
        props.addExcelProperty(com1);




        return props;
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
}
