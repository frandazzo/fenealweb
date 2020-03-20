package applica.feneal.admin.utils;

import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.deleghe.bari.StampaRistorniBariParams;
import applica.feneal.domain.model.core.ristorniEdilizia.QuotaAssociativaBari;

import applica.feneal.domain.model.core.ristorniEdilizia.UiReferenti;
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

    public   String createExcelFile(StampaRistorniBariParams params) throws IOException {




        FenealgestUtils svc = createFenealgestUtilsService();

        ExportDocumentToExcel f = null;

        f = createExcelDocument(params);

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

    private ExportDocumentToExcel createExcelDocument(StampaRistorniBariParams params) {
        ExportDocumentToExcel doc = new ExportDocumentToExcel();

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRows(params));

        doc.setDocument(document);
        return  doc;
    }

    private ArrayOfExcelRow createRows(StampaRistorniBariParams params) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();




        if(params.getType().equals("Riepilogo Quote Associative")){
            for (QuotaAssociativaBari q : params.getListQuote()) {

                ExcelRow row = new ExcelRow();
                row.setProperties(createQuoteAssocProperties(q));
                row.setDocument(createQuoteAssocSubDocument(q));
                rows.addExcelRow(row);

            }
        }

        if(params.getType().equals("Riepilogo Referenti")){
            for (UiReferenti q : params.getListRefrenti()) {

                ExcelRow row = new ExcelRow();
                row.setProperties(createReferentiProperties(q));
                row.setDocument(createReferentiSubDocument(q));
                rows.addExcelRow(row);

            }
        }





        return rows;
    }

    private ExcelDocument createQuoteAssocSubDocument(QuotaAssociativaBari quota) {
        if (quota.getUltimaDelega() == null)
            return null;

        if (quota.getDelegheBari().size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForDettaglioRistorno(quota));
        return  document;


    }
    private ExcelDocument createReferentiSubDocument(UiReferenti dett) {
        if (dett.getListQuote() == null)
            return null;

        if (dett.getListQuote().size() == 0)
            return null;

        ExcelDocument document = new ExcelDocument();
        document.setRows(createRowsForDettaglioReferente(dett));
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
    private ArrayOfExcelRow createRowsForDettaglioReferente(UiReferenti dett) {
        ArrayOfExcelRow rows = new ArrayOfExcelRow();

        List<QuotaAssociativaBari> list = dett.getListQuote();

        for (QuotaAssociativaBari l : list) {

            ExcelRow row = new ExcelRow();
            row.setProperties(createPropertiesForDettaglioReferenti(l));
            rows.addExcelRow(row);

        }


        return rows;
    }

    private ArrayOfExcelProperty createPropertiesForDelegaBari(DelegaBari l) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty firm = new ExcelProperty();
        firm.setName("Azienda");
        firm.setValue(l.getWorkerCompany() != null ? l.getWorkerCompany().getDescription() : "");
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
    private ArrayOfExcelProperty createPropertiesForDettaglioReferenti(QuotaAssociativaBari l) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty cogn = new ExcelProperty();
        cogn.setName("Cognome");
        cogn.setValue(l.getLavoratore().getSurname());
        cogn.setPriority(1);
        props.addExcelProperty(cogn);

        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");

        ExcelProperty name = new ExcelProperty();
        name.setName("Nome");
        name.setValue(l.getLavoratore().getName());
        name.setPriority(2);
        props.addExcelProperty(name);

        ExcelProperty dateProtocol = new ExcelProperty();
        dateProtocol.setName("Data protocollo");
        dateProtocol.setValue(l.getUltimaDelega().getProtocolDate() != null ? ff.format(l.getUltimaDelega().getProtocolDate()) : "");
        dateProtocol.setPriority(3);
        props.addExcelProperty(dateProtocol);


        ExcelProperty numProtocollo = new ExcelProperty();
        numProtocollo.setName("Num. Protocollo");
        numProtocollo.setValue(l.getUltimaDelega().getProtocolNumber());
        numProtocollo.setPriority(4);
        props.addExcelProperty(numProtocollo);





        ExcelProperty ultMov = new ExcelProperty();
        ultMov.setName("Azienda");
        ultMov.setValue(l.getUltimaDelega().getWorkerCompany() != null ? l.getUltimaDelega().getWorkerCompany().getDescription() : "" );
        ultMov.setPriority(5);
        props.addExcelProperty(ultMov);


        ExcelProperty referente = new ExcelProperty();
        referente.setName("Quota");
        referente.setValue(String.valueOf(l.getQuotaAssoc()));
        referente.setPriority(6);
        props.addExcelProperty(referente);




        return props;
    }

    private ArrayOfExcelProperty createQuoteAssocProperties(QuotaAssociativaBari lib) {
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
    private ArrayOfExcelProperty createReferentiProperties(UiReferenti lib) {
        ArrayOfExcelProperty props = new ArrayOfExcelProperty();


        ExcelProperty name = new ExcelProperty();
        name.setName("Nominativo referente");
        name.setValue(lib.getNominativo());
        name.setPriority(1);
        props.addExcelProperty(name);

        ExcelProperty name1 = new ExcelProperty();
        name1.setName("Comune referente");
        name1.setValue(lib.getComune());
        name1.setPriority(2);
        props.addExcelProperty(name1);


        ExcelProperty fisclaCode = new ExcelProperty();
        fisclaCode.setName("Importo Totale");
        fisclaCode.setValue(String.valueOf(lib.getImportoTot()));
        fisclaCode.setPriority(3);
        props.addExcelProperty(fisclaCode);


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
