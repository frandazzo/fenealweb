package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.RichiesteFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.DateFromMonthFieldRenderer;
import applica.feneal.admin.fields.renderers.DateToMonthFieldRenderer;
import applica.feneal.admin.fields.renderers.LoggedUserProvinceSelectFieldRenderer;
import applica.feneal.admin.fields.renderers.OptionalStringProvinceSelectRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.reports.UiRichiesta;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.servizi.search.UiRichiestaReportSearchParams;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
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
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
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
import java.util.List;

/**
 * Created by angelo on 29/04/2016.
 */
@Controller
public class ReportRichiesteController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private RichiesteFacade richiesteReportFac;

    @Autowired
    private TraceFacade traceFacade;

    @RequestMapping(value="/richieste/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportRichieste(@RequestBody UiRichiestaReportSearchParams params){
        List<UiRichiesta> f;
        try{
            f = richiesteReportFac.reportRichieste(params);
            manageActivityReportRichieste(params, "Report richieste", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/richieste",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("richiestereport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("date", String.class, "Da", null, applicationContext.getBean(DateFromMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("date", String.class, "a", null, applicationContext.getBean(DateToMonthFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("recipient", String.class, "Destinat.", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("requestToProvince", String.class, "Rich. al territorio di", null, applicationContext.getBean(OptionalStringProvinceSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report richieste");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    private void manageActivityReportRichieste(UiRichiestaReportSearchParams params, String activityName, List<UiRichiesta> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String provDescr = "";
            if (!StringUtils.isEmpty(params.getProvince())) {
                Province c = geoSvc.getProvinceById(Integer.parseInt(params.getProvince()));
                if (c != null)
                    provDescr = c.getDescription();
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportRichieste(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Da: %s %s; A: %s %s; Destinatario: %s; Richiesta al territorio di: %s",
                            provDescr,
                            StringUtils.isEmpty(params.getDatefromMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatefromMonthReport()) - 1),
                            params.getDatefromYearReport(),
                            StringUtils.isEmpty(params.getDatetoMonthReport()) ? "-" : FenealDateUtils.getDescriptionByMonthCode(Integer.parseInt(params.getDatetoMonthReport()) - 1),
                            params.getDatetoYearReport(),
                            params.getRecipient(),
                            params.getRequestToProvince()));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }


    private String createDetailForReportRichieste(List<UiRichiesta> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (UiRichiesta lav : lavs) {
            b.append(String.format("%s <br>", lav.getLavoratoreCodiceFiscale()));
        }

        return b.toString();
    }

}


