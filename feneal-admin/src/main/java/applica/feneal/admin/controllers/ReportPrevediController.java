package applica.feneal.admin.controllers;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalStateFieldRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;
import applica.feneal.domain.model.dbnazionale.search.PrevediReportSearchParams;
import applica.feneal.services.ReportPrevediService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
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

@Controller
public class ReportPrevediController {


    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private ReportPrevediService reportSvc;

    @RequestMapping(value="/prevedi/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportLiberi(@RequestBody PrevediReportSearchParams params){
        List<LavoratorePrevedi> f;
        try{
            f = reportSvc.retrieveLavoratoriPrevedi(params);
            for (LavoratorePrevedi lavoratorePrevedi : f) {
                lavoratorePrevedi.setNumIscrizioni(lavoratorePrevedi.getIscrizioni().size());
            }
            //manageActivityReportNonIscritti(params, "Report prevedi", f);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }


    @RequestMapping(value = "/prevediview",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("prevedireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggdUserExclusiveProvicesNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("anno", String.class, "Anno", null, applicationContext.getBean(YearPrevediSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report prevedi");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }
}
