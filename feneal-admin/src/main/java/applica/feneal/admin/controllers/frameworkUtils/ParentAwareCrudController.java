package applica.feneal.admin.controllers.frameworkUtils;

import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.i18n.controllers.LocalizedController;
import applica.framework.library.responses.*;
import applica.framework.security.Security;
import applica.framework.security.authorization.AuthorizationException;
import applica.framework.widgets.*;
import applica.framework.widgets.acl.CrudAuthorizationException;
import applica.framework.widgets.acl.CrudGuard;
import applica.framework.widgets.acl.CrudPermission;
import applica.framework.widgets.builders.FormBuilder;
import applica.framework.widgets.builders.FormDataProviderBuilder;
import applica.framework.widgets.builders.GridBuilder;
import applica.framework.widgets.builders.GridDataProviderBuilder;
import applica.framework.widgets.data.FormDataProvider;
import applica.framework.widgets.data.GridDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringWriter;

/**
 * Created by fgran on 28/04/2016.
 */
@Controller
@RequestMapping("/parentcrud")
public class ParentAwareCrudController extends LocalizedController {

    /*
    QUesto controller gestisce i crud delle entità correlate alla commessa, provvedendo ad effettuare find o save tenendo sempre in considerazione uno
    specifico JOB_ID
     */

    @Autowired(required = false)
    private CrudGuard crudGuard;

    @Autowired
    private LavoratoriRepository lavRepository;



    @Autowired
    private Security security;


    @RequestMapping({"/grid/{entity}/{workerId}"})
    @ResponseBody
    public SimpleResponse grid(@PathVariable("entity") String entity, @PathVariable("workerId") Long workerId, String loadRequest) {
        if(this.crudGuard != null) {
            try {
                this.crudGuard.check("list", entity);
            } catch (CrudAuthorizationException var10) {
                return new ErrorResponse(localization.getMessage("crud.unauthorized"));
            }
        }


        GridResponse response = new GridResponse();
        StringWriter writer = new StringWriter();

        try {
            Grid grid = GridBuilder.instance().build(entity);
            GridDataProvider dataProvider = GridDataProviderBuilder.instance().build(entity);
            //filtrare per ID lavoratore
            LoadRequest request = LoadRequest.fromJSON(loadRequest);
            Filter lavIdFilter = new Filter();
            lavIdFilter.setType(Filter.EQ);
            lavIdFilter.setValue(workerId);
            lavIdFilter.setProperty("lavoratore");
            request.getFilters().add(lavIdFilter);

            dataProvider.load(grid, request);
            grid.write(writer);
            if(StringUtils.isEmpty(grid.getTitle())) {
                grid.setTitle(this.localization.getMessage("crud.grid.title." + entity));
            }

            response.setTitle(grid.getTitle());
            response.setSearchFormIncluded(grid.getSearchForm() != null);
            response.setFormIdentifier(grid.getFormIdentifier());
            response.setCurrentPage(grid.getCurrentPage());
            response.setPages(grid.getPages());
            response.setError(false);
        } catch (CrudConfigurationException var8) {
            response.setError(true);
            response.setMessage("Crud configuration error: " + var8.getMessage());
        } catch (GridCreationException var9) {
            response.setError(true);
            response.setMessage("Error creating grid: " + var9.getMessage());
        }

        response.setContent(writer.toString());
        return response;
    }


    @RequestMapping(value="/form/{entity}/{workerId}", method=RequestMethod.GET)
    public @ResponseBody SimpleResponse form(@PathVariable("entity") String entity,@PathVariable("workerId") Long workerId, String id) {
        if(crudGuard != null) {

            try {
                String crudPermission = StringUtils.hasLength(id) ? CrudPermission.EDIT : CrudPermission.NEW;
                crudGuard.check(crudPermission, entity);

            } catch (CrudAuthorizationException e) {
                return new ErrorResponse(localization.getMessage("crud.unauthorized"));
            }

        }

        FormResponse response = new FormResponse();
        StringWriter writer = new StringWriter();

        Form form;
        FormDataProvider dataProvider;
        try {
            form = FormBuilder.instance().build(entity);
            //specifico il jobId
            dataProvider = FormDataProviderBuilder.instance().build(entity);
            dataProvider.load(form, id);
            form.setAction("javascript:;");

            if (StringUtils.isEmpty(form.getTitle())) {
                if (!form.isEditMode()) {

                    form.setTitle("Crea " + entity);


                } else {
                    form.setTitle("Modifica " + entity);
                }
            }
            Lavoratore lavoratore = lavRepository.get(workerId).orElse(null);
            form.getData().put("lavoratore",lavoratore.getLid());

            form.write(writer);

            response.setTitle(form.getTitle());
            response.setAction("javascript:;");
            response.setError(false);
        } catch (FormProcessException e) {
            response.setError(true);
            response.setMessage("Error processing form: " + e.getMessage());
        } catch (CrudConfigurationException e) {
            response.setError(true);
            response.setMessage("Crud configuration error: " + e.getMessage());
        } catch (FormCreationException e) {
            response.setError(true);
            response.setMessage("Error creating form: " + e.getMessage());
        }

        response.setContent(writer.toString());

        return response;
    }






}
