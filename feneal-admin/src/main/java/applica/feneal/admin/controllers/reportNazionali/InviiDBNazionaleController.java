package applica.feneal.admin.controllers.reportNazionali;

import applica.feneal.admin.facade.ImportazioneFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.dbnazionale.UiInvioDbNazionale;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class InviiDBNazionaleController {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ImportazioneFacade impFac;


    @RequestMapping(value="/inviidbnazionali/report", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportIqa(@RequestParam(value="anno", required=true) int anno){
        List<UiInvioDbNazionale> f;
        try{
            f = impFac.getInviiDbNazionale(anno);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }




    @RequestMapping(value = "/inviidbnazionali",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("inviidbnazionalireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);


            formDescriptor.addField("date", String.class, "Da", null, applicationContext.getBean(YearSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report Invii DB Nazionali");

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
