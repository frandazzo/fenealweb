package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.MagazzinoDelegheFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiAppDelega;
import applica.feneal.admin.viewmodel.deleghe.UiMagazzino;
import applica.feneal.admin.viewmodel.reports.UiMagazzinoDeleghe;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheSearchParams;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by angelo on 14/04/2016.
 */
@Controller
public class MagazzinoDelegheController {

    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MagazzinoDelegheFacade magazzinoDelFac;

    @RequestMapping(value="/magazzinodeleghe/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse magazzinoDeleghe(@RequestBody UiMagazzinoDelegheSearchParams params){
        List<UiMagazzinoDeleghe> f;
        try{
            f = magazzinoDelFac.magazzinoDeleghe(params);
            return new ValueResponse(f);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/magazzinodeleghe",method = RequestMethod.GET)
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
            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("collaborator", String.class, "Collabor.", null, applicationContext.getBean(CollaboratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Magazzino deleghe");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    @RequestMapping(value = "/magazzinodeleghe/add",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody SimpleResponse showListaLavoroAddWorker(HttpServletRequest request) throws Exception {

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

    @RequestMapping(value = "/magazzinodeleghe/add",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody SimpleResponse addDelega(@RequestBody UiMagazzino magazzino) throws Exception {

        try {

            magazzinoDelFac.createDeleghe(magazzino);
            return new ValueResponse("Ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/magazzinodeleghe/generadeleghe", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public void generateDeleghe(String province, String parithetic, String collaborator, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UiMagazzinoDelegheSearchParams uiMagazzParams = new UiMagazzinoDelegheSearchParams();

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



    @RequestMapping(value = "/magdelegaforapp", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse save(@RequestBody UiAppDelega delega) {

        try {
            return new ValueResponse(magazzinoDelFac.saveDelegaForApp(delega));
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/magdelegaforapp/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable String id) {

        try {
            return new ValueResponse(magazzinoDelFac.deleteDelega(id));
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }
}
