package applica.feneal.admin.controllers.reportNazionali;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.domain.model.dbnazionale.UiIscrittiNazionaleExport;
import applica.feneal.services.ReportIscrittiService;
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
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StampaIscrittiController {
    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private ReportIscrittiService rptIscr;



    @RequestMapping(value="/stampaiscritti/report", method = RequestMethod.GET)
   // @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportIqa(@RequestParam(value="anno", required=true) int anno,
                             @RequestParam(value="settore", required=false, defaultValue = "") String settore,
                             @RequestParam(value="provincia", required=false, defaultValue = "") String provincia,
                             @RequestParam(value="raggruppa", required=true, defaultValue = "true") String raggruppa){
        List<UiIscrittiNazionaleExport> f;
        try{



            f = rptIscr.getIscrittiNazionale(anno, settore, provincia, raggruppa);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }



    @RequestMapping(value = "/stampaiscritti",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("stampaiscritti");

            FormDescriptor formDescriptor = new FormDescriptor(form);


            formDescriptor.addField("anno", String.class, "Anno", null, applicationContext.getBean(YearSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("settore", String.class, "Settore", null, applicationContext.getBean(SectorSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("provincia", String.class, "Provincia", null, applicationContext.getBean(ProvinceSingleSearchableRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("raggruppa", boolean.class, "Raggruppa per settore", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Stampa Iscritti");

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
