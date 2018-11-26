package applica.feneal.admin.controllers.deleghe;

import applica.feneal.admin.facade.MagazzinoDelegheLecceFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.deleghe.UiMagazzinoLecce;
import applica.feneal.admin.viewmodel.reports.UiMagazzinoDelegheLecce;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheLecceSearchParams;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class DelegheLecceController {



    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MagazzinoDelegheLecceFacade magazzinoDelFac;

    @RequestMapping(value="/magazzinodeleghelecce/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse magazzinoDeleghe(@RequestBody UiMagazzinoDelegheLecceSearchParams params){
        List<UiMagazzinoDelegheLecce> f;
        try{
            f = magazzinoDelFac.magazzinoDeleghe(params);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/magazzinodeleghelecce",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("magazzinodeleghe");

            FormDescriptor formDescriptor = new FormDescriptor(form);


            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParitheticCanoniclanonOptionalFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("collaborator", String.class, "Collabor.", null, applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Magazzino deleghe revoche");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    @RequestMapping(value = "/magazzinodeleghelecce/add",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody SimpleResponse add(HttpServletRequest request) throws Exception {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("listalavoroaddworker");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class)).putParam(Params.ROW, "dt1");
            formDescriptor.addField("worker", String.class, "Lavoratore", null, applicationContext.getBean(LavoratoreSingleSearchableFieldRenderer.class)).putParam(Params.ROW, "dt1");

            formDescriptor.addField("tipoEnte", String.class, "Ente", null, applicationContext.getBean(EntiMagazzinoDelegheSelectFieldRenderer.class)).putParam(Params.ROW, "dt1");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value="/magazzinodeleghelecce/generadeleghe", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public void generateDeleghe(String province, String parithetic, String collaborator, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UiMagazzinoDelegheLecceSearchParams uiMagazzParams = new UiMagazzinoDelegheLecceSearchParams();

        uiMagazzParams.setProvince(province);
        uiMagazzParams.setParithetic(parithetic);
        uiMagazzParams.setCollaborator(collaborator);

        try {
            String fileToDownload = magazzinoDelFac.generateDeleghe(uiMagazzParams);
            magazzinoDelFac.downloadMagazzinoDelegheFile(fileToDownload, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }

    }



}
