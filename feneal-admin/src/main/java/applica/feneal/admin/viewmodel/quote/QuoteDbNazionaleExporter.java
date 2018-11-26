package applica.feneal.admin.viewmodel.quote;

import applica.feneal.domain.data.core.ContractRepository;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Contract;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.inps.QuotaInps;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.feneal.services.GeoService;
import applica.framework.LoadRequest;
import applica.framework.library.options.OptionsManager;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import it.fenealgestweb.www.*;
import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by fgran on 09/06/2016.
 */
@Component
public class QuoteDbNazionaleExporter {

    @Autowired
    private LavoratoriRepository lavRep;

    @Autowired
    private AziendeRepository azRep;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private Security sec;

    @Autowired
    private ContractRepository contrRep;

    private boolean isUserIsValid(String username, String password, String province){
        UserIsValidResponse result;

        Credenziali c = new Credenziali();
        c.setUserName(username);
        c.setPassword(password);
        c.setProvince(province.toUpperCase());
        CredenzialiE cred = new CredenzialiE();
        cred.setCredenziali(c);
        UserIsValid u = new UserIsValid();


        FenealgestwebServices svc = null;
        result = null;
        try {
            svc = new FenealgestwebServicesStub(null,optMan.get("applica.fenealgestservices"));
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
        try {
            result  = svc.userIsValid(u, cred);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result.getUserIsValidResult();
    }


    public void calculateCredentials(StringBuilder currentUsernameBuilder, StringBuilder currentPasswordBuilder, Province province) throws Exception {
        User u = ((User) sec.getLoggedUser());
        String currentUsername = "";
        String currentPassword = "";

        if (org.springframework.util.StringUtils.isEmpty(u.getCompany().getNationaUsername())){
            throw new Exception("Credenziali non presenti");
        }

        if (org.springframework.util.StringUtils.isEmpty(u.getCompany().getNationUserPassword())){
            throw new Exception("Credenziali non presenti");
        }




        String[] usernames = u.getCompany().getNationaUsername().split("#");
        String[] passwords = u.getCompany().getNationUserPassword().split("#");

        //verifico che entrambi gli array abbiano la stessa dimensione
        if (usernames.length != passwords.length)
            throw new Exception("Dimensioni diverse per gli array degli username e delle password");



        boolean found = false;
        for (int i = 0; i < usernames.length; i++) {
            currentPassword = passwords[i];
            currentUsername = usernames[i];
            if (isUserIsValid(currentUsername,currentPassword, province.getDescription())){
                found = true;
                currentUsernameBuilder.append(currentUsername);
                currentPasswordBuilder.append(currentPassword);
                break;
            }
        }

        if (!found)
            throw new Exception("Credenziali non valide per la provincia selezionata");

    }


    public String exportImpiantiFissi(UiCreaQuoteImpiantiFissi data, String username, String password, String province) throws Exception {


        ImportDataResponse result;


        ImportData i = new ImportData();

        ExportTrace t = new ExportTrace();
        t.setSector("IMPIANTI FISSI");
        t.setStruttura("FENEAL");

        Calendar cc = Calendar.getInstance();
        cc.setTime(new Date());
        t.setExportDate(cc);

        t.setExportType(ExprtType.ProgramExport);
        t.setExporterMail(((User) sec.getLoggedUser()).getMail());
        t.setExporterName(((User) sec.getLoggedUser()).getName() + " " + ((User) sec.getLoggedUser()).getSurname());
        //t.setArea("UIL");
        t.setFenealwebData(new FenealwebData());
        t.getFenealwebData().setDocumentType("IQA");
        t.getFenealwebData().setGuid(UUID.randomUUID().toString());




        //t.setExportNumber(0);

        //credenziali
        t.setPassword(password);
        t.setUserName(username);
        if (data.getParams().getCompleteProvince() == null)
            throw new Exception("Provincia non specificata");
        t.setProvince(data.getParams().getCompleteProvince().getDescription().toUpperCase());


        //periodo
        t.setPeriod(-1);
        t.setPeriodType(PeriodType.Interval);
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        if (!StringUtils.isEmpty(data.getParams().getCompetenceYear())){
            year = Integer.parseInt(data.getParams().getCompetenceYear());
        }
        if (year == null)
            year = Calendar.getInstance().get(Calendar.YEAR);
        t.setYear(year);

        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.JANUARY, 1, 0, 0);
        t.setInitialDate(c);

        Calendar c1 = Calendar.getInstance();
        c1.set(year, Calendar.DECEMBER, 31, 0, 0);
        t.setEndDate(c1);

        //t.setTransacted(true);


        t.setWorkers(createWorkers(data));
        i.setExportTrace(t);


        FenealgestwebServices svc = null;
        result = null;

        svc = new FenealgestwebServicesStub(null,optMan.get("applica.fenealgestservices"));


        result  = svc.importData(i);
        return result.getImportDataResult();


    }

    public String exportInps(List<QuotaInps> data, String username, String password, String province) throws Exception {

        if (data.size() == 0)
            return "";

        ImportDataResponse result;


        ImportData i = new ImportData();

        ExportTrace t = new ExportTrace();
        t.setSector("INPS");
        t.setStruttura("FENEAL");

        Calendar cc = Calendar.getInstance();
        cc.setTime(new Date());
        t.setExportDate(cc);

        t.setExportType(ExprtType.ProgramExport);
        t.setExporterMail(((User) sec.getLoggedUser()).getMail());
        t.setExporterName(((User) sec.getLoggedUser()).getName() + " " + ((User) sec.getLoggedUser()).getSurname());
        //t.setArea("UIL");
        t.setFenealwebData(new FenealwebData());
        t.getFenealwebData().setDocumentType("IQI");
        t.getFenealwebData().setGuid(UUID.randomUUID().toString());




        //t.setExportNumber(0);

        //credenziali
        t.setPassword(password);
        t.setUserName(username);
        if (province == null)
            throw new Exception("Provincia non specificata");
        t.setProvince(province.toUpperCase());


        //periodo
        t.setPeriod(-1);
        t.setPeriodType(PeriodType.Interval);
        Calendar g = new GregorianCalendar();
        cc.setTime(data.get(0).getDataValuta());
        Integer year = g.get(Calendar.YEAR);

        t.setYear(year);

        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.JANUARY, 1, 0, 0);
        t.setInitialDate(c);

        Calendar c1 = Calendar.getInstance();
        c1.set(year, Calendar.DECEMBER, 31, 0, 0);
        t.setEndDate(c1);

        //t.setTransacted(true);


        t.setWorkers(createWorkersFromQuote(data, province));
        i.setExportTrace(t);


        FenealgestwebServices svc = null;
        result = null;

        svc = new FenealgestwebServicesStub(null,optMan.get("applica.fenealgestservices"));


        result  = svc.importData(i);
        return result.getImportDataResult();


    }

    private ArrayOfWorkerDTO createWorkersFromQuote(List<QuotaInps> data, String province) {
        ArrayOfWorkerDTO result = new ArrayOfWorkerDTO();

        result.setWorker(createArrayFromQuote(data, province));

        return result;
    }

    private WorkerDTO[] createArrayFromQuote(List<QuotaInps> data, String province) {
        WorkerDTO[] ar = new WorkerDTO[data.size()];

        int i  = 0;
        for (QuotaInps q : data) {

            WorkerDTO dto = new WorkerDTO();
            Calendar c = Calendar.getInstance();
            c.setTime(q.getLavoratore().getBirthDate());
            dto.setBirthDate(c);
            dto.setFiscalcode(q.getLavoratore().getFiscalcode());
            dto.setSurname(q.getLavoratore().getSurname());
            dto.setName(q.getLavoratore().getName());
            dto.setName(q.getLavoratore().getName());
            dto.setBirthPlace(q.getLavoratore().getBirthPlace());
            dto.setLivingPlace(q.getLavoratore().getLivingCity());
            dto.setAddress(q.getLavoratore().getAddress());
            dto.setCap(q.getLavoratore().getCap());
            dto.setPhone(q.getLavoratore().getCellphone());
            dto.setRowNumber(i+1);
            dto.setLastModifer(province);
            dto.setLiberoAl(Calendar.getInstance());
            dto.setLastUpdate(Calendar.getInstance());
            dto.setPhone(q.getLavoratore().getCellphone());




            //.......
            dto.setSubscription(createSubscriptionFromQuoteInps(q, province));
            ar[i] = dto;


            i++;
        }

        return ar;
    }

    private SubscriptionDTO createSubscriptionFromQuoteInps(QuotaInps data, String province) {



        SubscriptionDTO dto = new SubscriptionDTO();

        dto.setSector(Sector.sector_inps);
        dto.setEntity("");

        dto.setEmployCompany("");

        dto.setFiscalCode("");

        dto.setContract("");


        dto.setLevel("");
        dto.setQuota(data.getImporto());


        dto.setSemester(-1);
        dto.setPeriodType(PeriodType.Interval);
        Calendar c2 = new GregorianCalendar();
        c2.setTime(data.getDataValuta());
        Integer year = c2.get(Calendar.YEAR);

        dto.setYear(year);

        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.JANUARY, 1, 0, 0);
        dto.setInitialDate(c);

        Calendar c1 = Calendar.getInstance();
        c1.set(year, Calendar.DECEMBER, 31, 0, 0);
        dto.setEndDate(c1);


        dto.setProvince(province);
        Region r = geoSvc.getREgionById(geoSvc.getProvinceByName(province).getIdRegion());
        dto.setRegion(r.getDescription());

        dto.setFenealwebSubscriptionDTOData(new FenealwebSubscriptionDTOData());


        dto.getFenealwebSubscriptionDTOData().setStartDate(c);
        dto.getFenealwebSubscriptionDTOData().setEndDate(c1);



        return dto;
    }


    private ArrayOfWorkerDTO createWorkers(UiCreaQuoteImpiantiFissi data) throws Exception {
        ArrayOfWorkerDTO result = new ArrayOfWorkerDTO();

        result.setWorker(createArray(data));

        return result;
    }

    private WorkerDTO[] createArray(UiCreaQuoteImpiantiFissi data) throws Exception {
        WorkerDTO[] ar = new WorkerDTO[data.getQuoteRows().size()];

        int i  = 0;
        for (UiLavoratoriQuoteImpiantiFissi uiLavoratoriQuoteImpiantiFissi : data.getQuoteRows()) {

            Lavoratore l = lavRep.get(uiLavoratoriQuoteImpiantiFissi.getLavoratoreId()).orElse(null);


            WorkerDTO dto = new WorkerDTO();
            Calendar c = Calendar.getInstance();
            c.setTime(l.getBirthDate());
            dto.setBirthDate(c);
            dto.setFiscalcode(l.getFiscalcode());
            dto.setSurname(l.getSurname());
            dto.setName(l.getName());
            dto.setName(l.getName());
            dto.setBirthPlace(l.getBirthPlace());
            dto.setLivingPlace(l.getLivingCity());
            dto.setAddress(l.getAddress());
            dto.setCap(l.getCap());
            dto.setPhone(l.getCellphone());
            dto.setRowNumber(i+1);
            dto.setLastModifer(data.getParams().getCompleteProvince().getDescription());
            dto.setLiberoAl(Calendar.getInstance());
            dto.setLastUpdate(Calendar.getInstance());
            dto.setPhone(l.getCellphone());




            //.......
            dto.setSubscription(createSubscription(uiLavoratoriQuoteImpiantiFissi, data, l));
            ar[i] = dto;


            i++;
        }

        return ar;
    }

    private SubscriptionDTO createSubscription(UiLavoratoriQuoteImpiantiFissi uiLavoratoriQuoteImpiantiFissi, UiCreaQuoteImpiantiFissi data, Lavoratore l) throws Exception {
        if (StringUtils.isEmpty(uiLavoratoriQuoteImpiantiFissi.getAzienda()))
            throw new Exception("Azienda nulla");
       //modifca erffettuata per gestire le duplicazioni delle quote
        //l'azienda adesso la rpendo dalle row e non dalla testata
        //Azienda az = data.getParams().getCompleteFirm();
        Azienda az = azRep.find(LoadRequest.build().filter("description",uiLavoratoriQuoteImpiantiFissi.getAzienda() )).findFirst().orElse(null);
        if (az == null)
            throw new Exception("Azienda nulla");



        SubscriptionDTO dto = new SubscriptionDTO();

        dto.setSector(Sector.sector_IMPIANTIFISSI);
        dto.setEntity("");
        //if (data.getParams().getCompleteFirm() == null)
          //  throw new Exception("Azienda nulla");


        dto.setEmployCompany(az.getDescription());

        dto.setFiscalCode(az.getPiva());
        if (!StringUtils.isEmpty(uiLavoratoriQuoteImpiantiFissi.getContratto())){
            Contract v = contrRep.find(LoadRequest.build().filter("description",uiLavoratoriQuoteImpiantiFissi.getContratto())).findFirst().orElse(null);
            if (v != null)
                dto.setContract(v.getDescription());
        }else{
            dto.setContract("");
        }

        dto.setLevel("");
        dto.setQuota(uiLavoratoriQuoteImpiantiFissi.getImporto());


        dto.setSemester(-1);
        dto.setPeriodType(PeriodType.Interval);
        Integer year = Integer.parseInt(data.getParams().getCompetenceYear());

        dto.setYear(year);

        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.JANUARY, 1, 0, 0);
        dto.setInitialDate(c);

        Calendar c1 = Calendar.getInstance();
        c1.set(year, Calendar.DECEMBER, 31, 0, 0);
        dto.setEndDate(c1);


        dto.setProvince(data.getParams().getCompleteProvince().getDescription());
        Region r = geoSvc.getREgionById(geoSvc.getProvinceById(Integer.parseInt(data.getParams().getProvince())).getIdRegion());
        dto.setRegion(r.getDescription());

        dto.setFenealwebSubscriptionDTOData(new FenealwebSubscriptionDTOData());

        String monthC = data.getParams().getCompetenceMonth();


        if (StringUtils.isEmpty(monthC)){
            dto.getFenealwebSubscriptionDTOData().setStartDate(c);
            dto.getFenealwebSubscriptionDTOData().setEndDate(c1);
        }else{
            dto.getFenealwebSubscriptionDTOData().setStartDate(calculateStartDateOfMonth(year, Integer.parseInt(monthC)));
            dto.getFenealwebSubscriptionDTOData().setEndDate(calculateEndDateOfMonth(year, Integer.parseInt(monthC)));
        }
        dto.getFenealwebSubscriptionDTOData().setFirmCity(az.getCity());
        dto.getFenealwebSubscriptionDTOData().setFirmAddress(az.getAddress());
        dto.getFenealwebSubscriptionDTOData().setFirmCap(az.getCap());
        dto.getFenealwebSubscriptionDTOData().setFirmTel(az.getPhone());
        dto.getFenealwebSubscriptionDTOData().setWorkerPhone2(l.getPhone());
        dto.getFenealwebSubscriptionDTOData().setWorkerEC(l.getCe());
        dto.getFenealwebSubscriptionDTOData().setWorkerCE(l.getEc());

        return dto;
    }

    private Calendar calculateEndDateOfMonth(int year, int month){
        GregorianCalendar gc = new GregorianCalendar(year, month-1, 1);
        int dayOfMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        GregorianCalendar gc1 = new GregorianCalendar(year, month-1, dayOfMonth);
        return gc1;

    }


    private Calendar calculateStartDateOfMonth(int year, int month){
        GregorianCalendar gc = new GregorianCalendar(year, month-1, 1);
        return gc;
    }


}
