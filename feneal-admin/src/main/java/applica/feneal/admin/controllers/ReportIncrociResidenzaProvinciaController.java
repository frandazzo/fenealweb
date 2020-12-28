package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.IncrocioFacade;
import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ReportIncrociResidenzaProvinciaController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IncrocioFacade incrocioFacade;

    @Autowired
    private TraceFacade traceFacade;



    @RequestMapping(value = "/incrocio",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("incrocioreport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia",null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("sector", String.class, "Settore",null, applicationContext.getBean(SectorTypeOnlyEdileSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("date", String.class, "Anno","Filtri", applicationContext.getBean(DateFromYearFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, "  ");
//            formDescriptor.addField("period", String.class, "Periodo","Filtri", applicationContext.getBean(OptionalSemestreSelectFieldRenderer.class))
//                    .putParam(Params.COLS, Values.COLS_12)
//                    .putParam(Params.ROW, "dt2")
//                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("data", String.class, "Data esportazione","Filtri", applicationContext.getBean(DatePickerRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, "  ");
//            formDescriptor.addField("includeIscrittiProvincia", Boolean.class, "Includi iscr. prov.", "Filtri", applicationContext.getBean(DefaultFieldRenderer.class))
//                    .putParam(Params.COLS, Values.COLS_12)
//                    .putParam(Params.ROW, "dt3")
//                    .putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Incrocio iscritti per provincia residenza");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value="/report/incrocio", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportLiberi(@RequestBody ReportIncrocioSearchParams params){
        List<LavoratoreIncrocio> f;
        try{
            f = incrocioFacade.retrieveLavoratoriIncrocio(params);
//            for (LavoratorePrevedi lavoratorePrevedi : f) {
//                lavoratorePrevedi.setNumIscrizioni(lavoratorePrevedi.getIscrizioni().size());
//            }
            //manageActivityReportNonIscritti(params, "Report prevedi", f);
            manageActivityReportIscritti(params, "Report incrocio provincia residenza", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    private void manageActivityReportIscritti(ReportIncrocioSearchParams params, String activityName, List<LavoratoreIncrocio> lavs) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            if(StringUtils.isEmpty(params.getData())){
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.MONTH, -2);
                params.setData(c.getTime().toString());
            }


            traceFacade.traceActivity(user, activityName, createDetailForReportIncrocio(lavs),
                    String.format("Parametri di ricerca: Provincia: %s; Anno: %s;Settore: %s;Periodo: %s;Data esportazione: %s",
                            params.getProvince(),
                            params.getDatefromYearReport(),
                            params.getSector(),
                            StringUtils.isEmpty(params.getPeriod()) ? "" : params.getPeriod(),
                            params.getData()));

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForReportIncrocio(List<LavoratoreIncrocio> lavs) {

        StringBuilder b = new StringBuilder();
        b.append("Nel report sono comparsi i seguenti iscritti: <br><br>");

        for (LavoratoreIncrocio lav : lavs) {
            b.append(String.format("%s <br>", lav.getCodiceFiscale()));
        }

        return b.toString();
    }
}
