package applica.feneal.webservices;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Contract;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Region;
import applica.feneal.domain.utils.Box;
import it.fenealgestweb.www.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.axis2.AxisFault;


import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import javax.activation.DataHandler;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 06/04/2016.
 */
public class TraceTest{ //extends TestCase {


    protected void setUp() {
//        context =
//                new ClassPathXmlApplicationContext(new String[] {"conf.xml"});
    }

    public void xxxtestGetDataExport() throws IOException{

        it.fenealgestweb.www.FenealgestStatsStub svc = null;
        try {
            svc =  new FenealgestStatsStub("http://www.fenealgest.it/servizi/WebServices/FenealgestStats.asmx");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        it.fenealgestweb.www.FenealgestStatsStub.FindDataExportsResponse result = null;
        it.fenealgestweb.www.FenealgestStatsStub.FindDataExports method = new it.fenealgestweb.www.FenealgestStatsStub.FindDataExports();
        method.setProvince("PERUGIA");
        result = svc.findDataExports(method);
        Assert.assertNotNull(result.getFindDataExportsResult());
    }


    public void xxxtestCalculateStats() throws IOException{

        it.fenealgestweb.www.FenealgestStatsStub svc = null;
        try {
            svc =  new FenealgestStatsStub("http://www.fenealgest.it/servizi/WebServices/FenealgestStats.asmx");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        it.fenealgestweb.www.FenealgestStatsStub.CalculateStatisticsResponse result = null;
        it.fenealgestweb.www.FenealgestStatsStub.CalculateStatistics method = new it.fenealgestweb.www.FenealgestStatsStub.CalculateStatistics();
        method.setProvince("PERUGIA");
        method.setFilenames("IQA_2018_2_1_9_52_39.xml;Liberi_2018_2_1_9_52_36.xml");
        result = svc.calculateStatistics(method);
        Assert.assertNotNull(result.getCalculateStatisticsResult());
    }


    public void testInvioDatiImpiantiFissi() throws Exception {
//        ImportDataResponse result;
//
//
//        ImportData i = new ImportData();
//
//        FenealwebData d = new FenealwebData();
//        d.setAssociateDelega(true);
//        d.setAuthomaticIntegrationFilename("file");
//        d.setCreateDelegaIfNotExist(true);
//        d.setCreateListaLavoro(true);
//        d.setDocumentType("IQI");
//        d.setGuid("xxxxxxxxx");
//        d.setNotes1("not1");
//        d.setNotes2("not2");
//
//        ExportTrace t = new ExportTrace();
//        t.setFenealwebData(d);
//        t.setSector("IMPIANTI FISSI");
//        t.setStruttura("FENEAL");
//
//        Calendar cc = Calendar.getInstance();
//        cc.setTime(new Date());
//        t.setExportDate(cc);
//
//        t.setExportType(ExprtType.ProgramExport);
//        t.setExporterMail("fg.randazzo@hotmail.it");
//        t.setExporterName("Francesco Randazzo");
//        //t.setArea("UIL");
//
//
//        //t.setExportNumber(0);
//
//        //credenziali
//        t.setPassword("fenealbz");
//        t.setUserName("fenealbolzano");
//        t.setProvince("BOLZANO");
//
//
//        //periodo
//        t.setPeriod(-1);
//        t.setPeriodType(PeriodType.Interval);
//        t.setYear(2017);
//
//        Calendar c = Calendar.getInstance();
//        c.set(2017, Calendar.JANUARY, 1, 0, 0);
//        t.setInitialDate(c);
//
//        Calendar c1 = Calendar.getInstance();
//        c1.set(2017, Calendar.DECEMBER, 31, 0, 0);
//        t.setEndDate(c1);
//
//        //t.setTransacted(true);
//
//
//        t.setWorkers(createWorkers());
//        i.setExportTrace(t);
//
//
//        FenealgestwebServices svc = null;
//        result = null;
//
//        svc = new FenealgestwebServicesStub(null,"http://192.168.50.101/FenealgestWEBServices/WebServices/FenealgestWebServices.asmx");
//
//
//        result  = svc.importData(i);
//        Assert.assertNotNull(result.getImportDataResult());
    }

//    private ArrayOfWorkerDTO createWorkers() throws Exception {
//        ArrayOfWorkerDTO result = new ArrayOfWorkerDTO();
//
//        result.setWorker(createArray());
//
//        return result;
//    }
//
//    private WorkerDTO[] createArray() throws Exception {
//        WorkerDTO[] ar = new WorkerDTO[1];
//
//
//            Calendar c1 = Calendar.getInstance();
//            c1.set(2017, Calendar.DECEMBER, 31, 0, 0);
//
//            WorkerDTO dto = new WorkerDTO();
//            Calendar c = Calendar.getInstance();
//            c.setTime(c1.getTime());
//            dto.setBirthDate(c);
//            dto.setFiscalcode("RNDFNC77L14F052F");
//            dto.setSurname("Randazzo");
//            dto.setName("Francesco");
//            dto.setBirthPlace("Matera");
//            dto.setLivingPlace("Matera");
//            dto.setAddress("c.da serra d'alto snc");
//            dto.setCap("75100");
//            dto.setPhone("3385269726");
//            dto.setRowNumber(1);
//            dto.setLastModifer("MATERA");
//            dto.setLiberoAl(Calendar.getInstance());
//            dto.setLastUpdate(Calendar.getInstance());
//
//
//
//            //.......
//            dto.setSubscription(createSubscription());
//            ar[0] = dto;
//
//
//
//
//
//        return ar;
//    }
//
//    private SubscriptionDTO createSubscription() {
//        SubscriptionDTO dto = new SubscriptionDTO();
//        Calendar c1 = Calendar.getInstance();
//        c1.set(2017, Calendar.DECEMBER, 31, 0, 0);
//        FenealwebSubscriptionDTOData d = new FenealwebSubscriptionDTOData();
//        d.setEndDate(c1);
//        d.setStartDate(c1);
//        d.setNote("not");
//        d.setTipoPrestazione("tip");
//
//
//
//        dto.setFenealwebSubscriptionDTOData(d);
//        dto.setSector(Sector.sector_IMPIANTIFISSI);
//        dto.setEntity("");
//
//        dto.setEmployCompany("Ciccillo spa");
//        dto.setFiscalCode("");
//
//        dto.setContract("Contratto applicato");
//
//
//        dto.setLevel("");
//        dto.setQuota(100);
//
//
//        dto.setSemester(-1);
//        dto.setPeriodType(PeriodType.Interval);
//
//        dto.setYear(2017);
//
//        Calendar c = Calendar.getInstance();
//        c.set(2017, Calendar.JANUARY, 1, 0, 0);
//        dto.setInitialDate(c);
//
//        Calendar c3 = Calendar.getInstance();
//        c3.set(2017, Calendar.DECEMBER, 31, 0, 0);
//        dto.setEndDate(c3);
//
//
//        dto.setProvince("BARI");
//        dto.setRegion("PUGLIA");
//
//        return dto;
//    }

    public void xxxtestCodiceFiscale() throws IOException {

//        FenealgestUtils svc = null;
//        it.fenealgestweb.www.ExportDocumentToExcelResponse result = null;
//        try {
//            svc = new FenealgestUtilsStub(null,"http://192.168.50.101/FenealgestWEBServices/WebServices/FenealgestUtils.asmx");
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//        ExportDocumentToExcel f = new ExportDocumentToExcel();
//
//        ExcelDocument document = new ExcelDocument();
//        ArrayOfExcelRow rows = new ArrayOfExcelRow();
//
//        //creo la prima riga
//        ExcelRow row = new ExcelRow();
//
//        ArrayOfExcelProperty propList = new ArrayOfExcelProperty();
//        ExcelProperty name = new ExcelProperty();
//        name.setName("Lavoratore");
//        name.setValue("Francesco Randazzo");
//        name.setPriority(1);
//        propList.addExcelProperty(name);
//
//        ExcelProperty ente = new ExcelProperty();
//        ente.setName("Ente");
//        ente.setValue("Cassa edile");
//        ente.setPriority(2);
//        propList.addExcelProperty(ente);
//
//        row.setProperties(propList);
//
//        ExcelRow row1 = new ExcelRow();
//
//
//        ArrayOfExcelProperty propList1 = new ArrayOfExcelProperty();
//        ExcelProperty name1 = new ExcelProperty();
//        name1.setName("Lavoratore");
//        name1.setValue("Bruno Fortunato");
//        name1.setPriority(1);
//        propList1.addExcelProperty(name1);
//
//        ExcelProperty ente1 = new ExcelProperty();
//        ente1.setName("Ente");
//        ente1.setValue("Cassa edile");
//        ente1.setPriority(2);
//        propList1.addExcelProperty(ente1);
//
//        row1.setProperties(propList1);
//
//
//        rows.addExcelRow(row);
//        rows.addExcelRow(row1);
//
//        document.setRows(rows);
//
//
//        f.setDocument(document);
//
//        result = svc.exportDocumentToExcel(f);
//
//
//
//
//        DataHandler webResult = result.getExportDocumentToExcelResult();
//        //byte[] arrb =  (byte[])webResult.getContent();
//
//
//        InputStream inputStream = webResult.getInputStream();
//        File nn = File.createTempFile("excel", ".xlsx");
//
//        nn.createNewFile();
//        OutputStream outputStream = new FileOutputStream(nn);
//
//            try{
//
//                int read = 0;
//                byte[] bytes = new byte[1024];
//
//                while ((read = inputStream.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, read);
//                }
//
//                System.out.println("Done!");
//
//            } finally {
//                        if (inputStream != null) {
//                            try {
//                                inputStream.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (outputStream != null) {
//                            try {
//                                // outputStream.flush();
//                                outputStream.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//            }













//        FenealgestUtils svc = null;
//        it.fenealgestweb.www.ExportTessereResponse result = null;
//        try {
//            svc = new FenealgestUtilsStub(null,"http://www.fenealgest.it/servizi/WebServices/FenealgestUtils.asmx");
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//        ExportTessere f = new ExportTessere();
//        f.setSettore("EDILE");
//        f.setProvincia("BOLZANO");
//
//        ArrayOfTesserato t = new ArrayOfTesserato();
//        Tesserato t1 = new Tesserato();
//        t1.setAzienda("PROVA");
//        t1.setProvincia("BOLZANO");
//        t1.setComune("MATERA");
//        t1.setCap("75100");
//        t1.setVia("via gravina 39");
//        t1.setNome("francesco");
//        t1.setCognome("randazzo");
//        t1.setProvinciaSindacale("BOLZANO");
//        t1.setSettoreTessera("EDILE");
//        t1.setStampataDa("Maurizio D'aurelio");
//        Tesserato[] arr = new Tesserato[]{t1};
//        t.setTesserato(arr);
//        f.setTesserati(t);
//
//        result = svc.exportTessere(f);
//
//        DataHandler webResult = result.getExportTessereResult();
//        //byte[] arrb =  (byte[])webResult.getContent();
//
//
//        InputStream inputStream = webResult.getInputStream();
//        File nn = File.createTempFile("tessere-matera", ".zip");
//
//        nn.createNewFile();
//        OutputStream outputStream = new FileOutputStream(nn);
//
//            try{
//
//                int read = 0;
//                byte[] bytes = new byte[1024];
//
//                while ((read = inputStream.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, read);
//                }
//
//                System.out.println("Done!");
//
//            } finally {
//                        if (inputStream != null) {
//                            try {
//                                inputStream.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (outputStream != null) {
//                            try {
//                                // outputStream.flush();
//                                outputStream.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//            }


//***************************************************************
//        String d = "Di  Randazzo    Francesco     ";
//        while (StringUtils.countMatches(d, "  ") > 0){
//            String reduced = d.replace("  " , " ");
//            d = reduced;
//        }
//
//        String[] p = d.split(" ");
//
//        Box box = new Box();
//        //se ci sono piu di due elementi controllo il primo elemento e se è diverso da
//        List<String> dan = Arrays.asList("de", "di", "del", "delli" , "della", "dalla" , "la", "le", "li", "lo", "el");
//        if (!dan.contains(p[0])){
//            //allora metto il primo elemento nel cognome e tutti gli altri nel nome
//            box.setValue(p[0]);
//            box.setValue1(calculateNameFromArrayPosition(p, 1));
//        }else{
//            //metto i primi due nel cognome e i restanti nel nome
//            box.setValue(p[0] + " " + p[1]);
//            box.setValue1(calculateNameFromArrayPosition(p, 2));
//        }
//




        FenealgestUtils svc = null;
        CalcolaCodiceFiscaleResponse result = null;
        try {
            svc = new FenealgestUtilsStub(null,"http://192.168.50.101/FenealgestWEBServices/WebServices/FenealgestUtils.asmx");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        CalcolaCodiceFiscale params = new CalcolaCodiceFiscale();
        params.setCognome("D'alconzo");
        params.setNome("Angelo");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(1988, 1, 5, 0, 0, 0);
        params.setDataNascita(cal);
        params.setNomeComuneNascita("Matera");
        params.setNomeNazione("Italia");
        params.setSesso("MASCHIO");

        result = svc.calcolaCodiceFiscale(params);

        Assert.assertEquals(result.getCalcolaCodiceFiscaleResult(), "RNDFNC77L14F052F");

    }

    private String calculateNameFromArrayPosition(String[] elements, int position){
        String result = "";
        int i = 0;
        for (String element : elements) {
            if (i>=position)
                result = result + " " + element;
            i++;
        }

        return result.trim();
    }

    public void xxxtestUserIsValid(){
        UserIsValidResponse result;

        Credenziali c = new Credenziali();
        c.setUserName("fenealmatera");
        c.setPassword("serenamente");
        c.setProvince("MATERA");
        CredenzialiE cred = new CredenzialiE();
        cred.setCredenziali(c);
        UserIsValid u = new UserIsValid();


        FenealgestwebServices svc = null;
        result = null;
        try {
            svc = new FenealgestwebServicesStub(null,"http://www.fenealgest.it/servizi/WebServices/FenealgestWebServices.asmx");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        try {
            result  = svc.userIsValid(u, cred);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(result.getUserIsValidResult(), true);
    }

    public void xxxtestSendData(){
        ImportDataResponse result;
//
//        Credenziali c = new Credenziali();
//        c.setUserName("fenealmatera");
//        c.setPassword("serenamente");
//        c.setProvince("MATERA");
//        CredenzialiE cred = new CredenzialiE();
//        cred.setCredenziali(c);
//        UserIsValid u = new UserIsValid();

//        ImportData i = new ImportData();
//        ExportTrace t = new ExportTrace();
//        t.setArea("UIL");
//        t.setExporterMail("fg.randazzo@hotmail.it");
//        t.setExporterName("Francesco Randazzo");
//        t.setExportNumber(0);
//        t.setExportType(ExprtType.ProgramExport);
//        t.setPassword("123456");
//        t.setUserName("feneal123456");
//        t.setProvince("Matera");
//        t.setPeriod(-1);
//        t.setPeriodType(PeriodType.Interval);
//        t.setSector("IMPIANTI FISSI");
//        t.setStruttura("FENEAL");
//        t.setTransacted(true);
//        t.setYear(2016);
//        t.setWorkers(createWorkers());
//
//
//        Calendar cc = Calendar.getInstance();
//        cc.setTime(new Date());
//
//        t.setExportDate(cc);
//
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//
//        t.setEndDate(c);
//
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(new Date());
//
//        t.setInitialDate(c1);
//
//
//
//
//
//
//
//
//
//
//
//        FenealgestwebServices svc = null;
//        result = null;
//        try {
//            svc = new FenealgestwebServicesStub(null,"http://www.fenealgest.it/servizi/WebServices/FenealgestWebServices.asmx");
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
//        try {
//            result  = svc.importData(null);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        Assert.assertEquals(result.getImportDataResult(), "");
    }

//    private ArrayOfWorkerDTO createWorkers() {
//        ArrayOfWorkerDTO result = new ArrayOfWorkerDTO();
//
//        result.setWorker(createArray());
//
//        return result;
//    }
//
//    private WorkerDTO[] createArray() {
//
//        WorkerDTO[] ar = new WorkerDTO[5];
//        for (int i = 0; i < 5; i++) {
//
//            WorkerDTO dto = new WorkerDTO();
//
//            //.......
//
//            ar[i] = dto;
//        }
//        return ar;
//    }


    public void xxxtestService() {

        TraceUsageResponse result = null;
        TraceUsage u = new TraceUsage();

        u.setRegion("SICILIA");
        u.setProvince("TRAPANI");
        u.setApp("FENEALGEST");



        FenealgestwebServices svc = null;
        try {
            svc = new FenealgestwebServicesStub(null,"http://www.fenealgest.it/servizi/WebServices/FenealgestWebServices.asmx");
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        try {
            result  = svc.traceUsage(u);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(result.getTraceUsageResult(), "ok");

    }


    protected void tearDown() {

    }
}