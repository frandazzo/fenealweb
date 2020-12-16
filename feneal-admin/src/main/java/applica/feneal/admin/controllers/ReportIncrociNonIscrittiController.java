package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.IncrocioFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.LoggedUserProvinceNonOptionalSelectFieldRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocioNoniscritti;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioNonIscrittiSearchParams;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioSearchParams;
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
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ReportIncrociNonIscrittiController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IncrocioFacade incrocioFacade;

    @Autowired
    private TraceFacade traceFacade;

    @RequestMapping(value = "/incrocio/noniscritti",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("incrociononiscrittireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia",null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("data", String.class, "Libero al",null, applicationContext.getBean(DatePickerRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Incrocio non iscritti per provincia residenza");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    @RequestMapping(value="/report/incrocio/noniscritti", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportNoniscritti(@RequestBody ReportIncrocioNonIscrittiSearchParams params){
        List<LavoratoreIncrocioNoniscritti> f;
        try{
            f = incrocioFacade.retrieveLavoratoriIncrocioNonIscritti(params);
//            manageActivityReportIscritti(params, "Report incrocio non iscritti", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    private void manageActivityReportIscritti(ReportIncrocioNonIscrittiSearchParams params, String activityName, List<LavoratoreIncrocioNoniscritti> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            if(StringUtils.isEmpty(params.getData())){
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.MONTH, -2);
                params.setData(c.getTime().toString());
            }

            traceFacade.traceActivity(user, activityName, createDetailForReportIncrocioNonIscritti(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Libero al: %s;",
                            params.getProvince(),
                            params.getData()));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForReportIncrocioNonIscritti(List<LavoratoreIncrocioNoniscritti> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti lavoratori: <br><br>");

        for (LavoratoreIncrocioNoniscritti lav : lavs) {
            b.append(String.format("%s <br>", lav.getCodiceFiscale()));
        }

        return b.toString();
    }
}
