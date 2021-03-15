package applica.feneal.admin.controllers.RSU;

import applica.feneal.admin.facade.RSU.ContrattoRsuFacade;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.form.renderers.MulticolumnFormRenderer;
import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.RSU.UiContrattoRsu;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.services.RSU.ContrattoRsuService;
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
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by felicegramegna on 09/03/2021.
 */

@Controller
public class ContrattoRsuController {

    @Autowired
    private Security sec;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ContrattoRsuService contrattoRsuService;

    @Autowired
    private ContrattoRsuFacade contrattoRsuFacade;


    @RequestMapping(value = "/contractrsu",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse create(HttpServletRequest request) {
        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("contractrsu");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Descrizione", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("rsuMin", Integer.class, "Rsu Min.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("rsuMax", Integer.class, "Rsu Max.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione anagrafica contratto RSU");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/contractrsu/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse update(HttpServletRequest request, @PathVariable long id) {
        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("contractrsu");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Descrizione", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("rsuMin", Integer.class, "Rsu Min.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("rsuMax", Integer.class, "Rsu Max.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");

            ContrattoRSU c = contrattoRsuService.getContrattoRsuById(((User) sec.getLoggedUser()).getLid(),id);

            if (c!= null){
                Map<String, Object> data = new HashMap<>();

                data.put("id", id);
                data.put("description", c.getDescription());
                data.put("rsuMin", c.getRsuMin());
                data.put("rsuMax", c.getRsuMax());

                form.setData(data);
            }

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Aggiornamento anagrafica contratto RSU");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/contractrsu/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            contrattoRsuFacade.deleteContratto(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/contractrsu", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse saveRsu(@RequestBody UiContrattoRsu anag) {

        try {
            long id = contrattoRsuFacade.saveContratto(anag);
            return new ValueResponse(id);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/contractrsu/details", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse contractDetails(HttpServletRequest request, long id) {

        try {
            UiContrattoRsu ui = contrattoRsuFacade.getContrattoDetail(id);
            return new ValueResponse(ui);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }
}
