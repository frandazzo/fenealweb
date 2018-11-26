package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.IscrittiFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.app.dashboard.report.UiAppReportIscrittiInput;
import applica.feneal.admin.viewmodel.reports.UiIscittiStampaTessere;
import applica.feneal.admin.viewmodel.reports.UiIscritto;
import applica.feneal.admin.viewmodel.stats.UiSelectDataExportResult;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.servizi.search.UiIscrittoReportSearchParams;
import applica.feneal.services.AziendaService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import it.fenealgestweb.www.FenealgestStatsStub;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fgran on 15/04/2016.
 */
@Controller
public class ReportIscrittiController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private AziendaService azSvc;

    @Autowired
    private Security security;

    @Autowired
    private IscrittiFacade iscrittiReportFac;

    @RequestMapping(value="/iscritti/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportIscritti(@RequestBody UiIscrittoReportSearchParams params){
        List<UiIscritto> f;
        try{
            f = iscrittiReportFac.reportIscritti(params);
            manageActivityReportIscritti(params, "Report iscritti", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value="/iscritti/reportforapp", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportIscritti(@RequestBody UiAppReportIscrittiInput params){

        try{

            return new ValueResponse(iscrittiReportFac.reportIscrittiForApp(params));
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/iscritti",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("iscrittireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceStringSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("date", String.class, "Da", null, applicationContext.getBean(DateFromMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("date", String.class, "a", null, applicationContext.getBean(DateToMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("typeQuoteCash", String.class, " ", "Selezione tipo quote", applicationContext.getBean(TypeQuoteCashFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("delegationActiveExist", Boolean.class, "Verifica esistenza delega attiva", "", applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("sector", String.class, "Settore", "Filtri", applicationContext.getBean(SectorTypeWithoutInpsSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("parithetic", String.class, "Ente", "Filtri", applicationContext.getBean(ParitethicListFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("firm", String.class, "Azienda", "Filtri", applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt7")
                    .putParam(Params.FORM_COLUMN, "  ");
            /*formDescriptor.addField("collaborator", String.class, "Collabor.", "Filtri", applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, "  ");*/


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report iscritti");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "/iscritti/tessera/print", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showPrintTessera(HttpServletRequest request) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("iscrittitessera");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class)).putParam(Params.ROW, "dt").putParam(Params.COLS, Values.COLS_12);
            formDescriptor.addField("sector", String.class, "Settore", null, applicationContext.getBean(SectorTypeForStampaTessereSelectRenderer.class)).putParam(Params.ROW, "dt1").putParam(Params.COLS, Values.COLS_12);
            formDescriptor.addField("onlyWithoutTessera", Boolean.class, "Stampa tessera se non è stata già stampata", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class)).putParam(Params.ROW, "dt2").putParam(Params.COLS, Values.COLS_12);
            formDescriptor.addField("global", Boolean.class, "Stampa tessera se non è stata già stampata in Italia", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class)).putParam(Params.ROW, "dt3").putParam(Params.COLS, Values.COLS_12);

            Map<String, Object> data = new HashMap<>();

            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/iscritti/retrievetesserefile", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse retrievePathFileIscrittiTessere(@RequestBody UiIscittiStampaTessere uiIscrittiStampaTessere) throws Exception {
        try{
            String pathFile = iscrittiReportFac.retrieveTessereFile(uiIscrittiStampaTessere);
            return new ValueResponse(pathFile);
        } catch(Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/iscritti/tessera/downloadfile", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public void printTessere(String path, String province, HttpServletResponse response) {
        try {
            iscrittiReportFac.downloadTesseraFile(path, province, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }

    }

    @RequestMapping(value = "/iscritti/stats", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showGetDataExport(HttpServletRequest request) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("statsgetdataexport");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class)).putParam(Params.ROW, "dt").putParam(Params.COLS, Values.COLS_10);

            Map<String, Object> data = new HashMap<>();

            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/iscritti/stats/selectdata", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showCalculateStatsIscritti(@RequestBody UiSelectDataExportResult dataExport, HttpServletRequest request) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("calcolastatsiscritti");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("dataExportFilenames", String.class, "Nome file", null, applicationContext.getBean(DataExportFilenamesFieldRenderer.class)).putParam(Params.ROW, "dt").putParam(Params.COLS, Values.COLS_12);

            Map<String, Object> data = new HashMap<>();
            data.put("dataExportFilenames", dataExport.getFilenames());
            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/iscritti/stats/getdataexport", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse statsGetDataExport(String province) {
        try {
            List<String> filenames = iscrittiReportFac.statsGetDataExport(province);
            return new ValueResponse(filenames);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/iscritti/stats/getstatisticsresult", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse statsGetStatisticsResult(String province, String filenames) {
        try {
            FenealgestStatsStub.DataExportResult statsResult = iscrittiReportFac.statsGetStatisticResult(province, filenames);
            return new ValueResponse(statsResult);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    private void manageActivityReportIscritti(UiIscrittoReportSearchParams params, String activityName, List<UiIscritto> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String azDescr = "";
            if (!StringUtils.isEmpty(params.getFirm())) {
                Azienda az = azSvc.getAziendaById(((User) security.getLoggedUser()).getLid(), Long.parseLong(params.getFirm()));
                if (az != null)
                    azDescr = az.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportIscritti(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Da: %s %s; A: %s %s; Tipo quota: %s; Settore: %s; Ente: %s; Azienda: %s",
                            params.getProvince(),
                            StringUtils.isEmpty(params.getDatefromMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatefromMonthReport()) - 1),
                            params.getDatefromYearReport(),
                            StringUtils.isEmpty(params.getDatetoMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatetoMonthReport()) - 1),
                            params.getDatetoYearReport(),
                            params.getTypeQuoteCash(),
                            params.getSector(),
                            params.getParithetic(),
                            azDescr));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForReportIscritti(List<UiIscritto> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (UiIscritto lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }


}


