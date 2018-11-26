package applica.feneal.admin.controllers.deleghe;

import applica.feneal.admin.facade.DelegheFacade;
import applica.feneal.admin.fields.renderers.DatepickerReportDelegheFieldRenderer;
import applica.feneal.admin.fields.renderers.LoggedUserProvinceNonOptionalSelectFieldRenderer;
import applica.feneal.admin.fields.renderers.ParithericNonOptionalSelectFieldRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.deleghe.UiInoltraDeleghe;
import applica.feneal.admin.viewmodel.reports.UiDelega;
import applica.feneal.domain.model.core.deleghe.UiDelegheReportSearchParams;
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
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by angelo on 14/04/2016.
 */
@Controller
public class InoltroMassivoDelegheController {

    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DelegheFacade delFac;

    @RequestMapping(value="/inoltradeleghe/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse inoltraDeleghe(@RequestBody UiDelegheReportSearchParams params){
        List<UiDelega> f;
        try {
            // aggiungo implicitamente il parametro di ricerca 'SOTTOSCRITTA'
            params.setSubscribed(true);
            f = delFac.reportDelegheForInoltroOrAccettazione(params);
            return new ValueResponse(f);
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/inoltradeleghe",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("inoltradeleghe");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("subscribedFromDate", String.class, "Da", "Data delega", applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("subscribedToDate", String.class, "a", "Data delega", applicationContext.getBean(DatepickerReportDelegheFieldRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, "  ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Inoltro deleghe");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "/deleghe/inoltra", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse sendDelegheView() {

        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("delega");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("date", Date.class, "Data", null, applicationContext.getBean(DatePickerRenderer.class));


            Map<String, Object> data = new HashMap<>();
            data.put("date", new Date());
            form.setData(data);

            FormResponse response = new FormResponse();
            response.setContent(form.writeToString());
            response.setTitle("Inoltra deleghe");

            return response;


        }catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/deleghe/inoltra", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse sendDeleghe(@RequestBody UiInoltraDeleghe uiSend) {


        try {
            delFac.inoltreDeleghe(uiSend);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

}
