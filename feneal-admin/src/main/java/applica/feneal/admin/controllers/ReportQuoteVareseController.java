package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.ReportQuoteVareseFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
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
public class ReportQuoteVareseController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ReportQuoteVareseFacade reportQuoteVareseFacade;

    @RequestMapping(value="/quotevarese/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportQuoteVar(@RequestBody UiQuoteVareseReportSearchParams params){

        try {
            List<UiDettaglioQuota> f = reportQuoteVareseFacade.reportQuote(params);
            return new ValueResponse(f);
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }


    @RequestMapping(value = "/quotevarese",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("quotevaresereport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("quota1", String.class, "Quota1", null, applicationContext.getBean(SelectQuoteVareseSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("quota2", String.class, "Quota2", null, applicationContext.getBean(OptionalQuoteVareseSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report Quote");

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