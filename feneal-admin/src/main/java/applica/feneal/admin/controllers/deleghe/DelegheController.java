package applica.feneal.admin.controllers.deleghe;

import applica.feneal.admin.facade.DelegheFacade;
import applica.feneal.admin.facade.LavoratoriFacade;
import applica.feneal.admin.fields.renderers.CausaleRevocaFieldRenderer;
import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.utils.FormUtils;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiAppDelega;
import applica.feneal.admin.viewmodel.deleghe.UIDelega;
import applica.feneal.admin.viewmodel.deleghe.UiDelegaChangeState;
import applica.feneal.domain.model.FenealEntities;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.ImportDeleghe;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.setting.CausaleRevoca;
import applica.feneal.services.exceptions.FormNotFoundException;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.FileFieldRenderer;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fgran on 07/04/2016.
 */
@Controller
public class DelegheController {

    @Autowired
    private DelegheFacade delegheFacade;

    @Autowired
    private LavoratoriFacade lavoratoriFacade;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private FormUtils formUtils;

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/executeimportdeleghe",method = RequestMethod.POST)
    public @ResponseBody
    SimpleResponse executeimportdeleghe(HttpServletRequest request, HttpServletResponse response, @RequestBody ImportDeleghe file) {

        try{
            String fileToDownload = delegheFacade.importDeleghePerBariCassaEdile(file);

            return new ValueResponse(fileToDownload);
        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }


    @RequestMapping(value = "/importadeleghe",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview1(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("importaDelegheBariCassaEdile");

            FormDescriptor formDescriptor = new FormDescriptor(form);



            formDescriptor.addField("file1", Date.class, "File Deleghe", null,applicationContext.getBean(FileFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");




//            Map<String, Object> data = new HashMap<String, Object>();
//            data.put("data", DateUtils.createTodayDate());
//
//
//            form.setData(data);


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Importazione deleghe Bari");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "deleghe/home/{workerId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long workerId) {

        try {


            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("deleghe", delegheFacade.getAllWorkerDeleghe(workerId));

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "deleghe/home", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "delega", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse create(Long workerId) {

        try {
           Form form = formUtils.generateFormForEntity(FenealEntities.DELEGA, null);
            Map<String, Object> data = new HashMap<>();
            if (workerId != null) {

                Lavoratore d = lavoratoriFacade.getLavoratoreById(workerId);

                data.put("workerId", d.getLid());

            }
            data.put("documentDate", new Date());
            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione delega");

            return response;


        } catch (FormCreationException | CrudConfigurationException | FormNotFoundException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "delega/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse edit(@PathVariable  Long id) {
        Delega d = delegheFacade.getDelegaById(id);
        try {
            Form form = formUtils.generateFormForEntity(FenealEntities.DELEGA_EDIT, d);
            if (id != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("id", d.getId());
                data.put("documentDate", new SimpleDateFormat("dd/MM/yyy").format(d.getDocumentDate()));
                data.put("validityDate", d.getValidityDate()!= null ? new SimpleDateFormat("dd/MM/yyy").format(d.getValidityDate()): "");
                data.put("workerId", d.getWorker().getLid());
                data.put("subscribeReason", d.getSubscribeReason());
                data.put("province", d.getProvince());
                data.put("notes", d.getNotes());
                data.put("collaborator", d.getCollaborator());
                data.put("sector", d.getSector());
                data.put("workerCompany", d.getWorkerCompany());
                data.put("paritethic", d.getParitethic());
                data.put("attachment", d.getAttachment());
                data.put("nomeattachment", d.getNomeattachment());
                switch (d.getState()) {
                    case Delega.state_accepted:
                        data.put("sendDate", new SimpleDateFormat("dd/MM/yyy").format(d.getSendDate()));
                        data.put("acceptDate", new SimpleDateFormat("dd/MM/yyy").format(d.getAcceptDate()));
                            break;
                    case Delega.state_subscribe:
                        break;
                    case Delega.state_sent:
                        data.put("sendDate", new SimpleDateFormat("dd/MM/yyy").format(d.getSendDate()));
                        break;
                    case Delega.state_activated:
                        data.put("sendDate", new SimpleDateFormat("dd/MM/yyy").format(d.getSendDate()));
                        data.put("acceptDate", new SimpleDateFormat("dd/MM/yyy").format(d.getAcceptDate()));
                        data.put("activationDate", new SimpleDateFormat("dd/MM/yyy").format(d.getActivationDate()));
                         break;
                    case Delega.state_revoked:
                        data.put("revokeDate", new SimpleDateFormat("dd/MM/yyy").format(d.getRevokeDate()));
                        data.put("revokeReason", d.getRevokeReason());
                        data.put("preecedingState", d.calculatePrecedingStateString(d.getPreecedingState()));
                        break;
                    case Delega.state_cancelled:
                        data.put("cancelDate", new SimpleDateFormat("dd/MM/yyy").format(d.getCancelDate()));
                        data.put("cancelReason", d.getCancelReason());
                        data.put("preecedingState", d.calculatePrecedingStateString(d.getPreecedingState()));
                        break;
                    default:
                        break;
                }
                data.put("firstAziendaEdile", d.getFirstAziendaEdile());







                form.setData(data);
            }


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Modifica delega");

            return response;


        }catch (FormCreationException | CrudConfigurationException | FormNotFoundException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }





    @RequestMapping(value = "/delega/changeState/{newState}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse changeStateForm(@PathVariable("newState") int newState) {

        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("delega");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("date", Date.class, "Data", null, applicationContext.getBean(DatePickerRenderer.class));

            if (newState == Delega.state_cancelled || newState == Delega.state_revoked)
                formDescriptor.addField("causaleId", CausaleRevoca.class, "Causale", null, applicationContext.getBean(CausaleRevocaFieldRenderer.class));



            Map<String, Object> data = new HashMap<>();
            data.put("date", new Date());
            form.setData(data);



            FormResponse response = new FormResponse();
            response.setContent(form.writeToString());
            response.setTitle("Aggiorna delega");

            return response;


        }catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }




    @RequestMapping(value = "/delega", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse save(@RequestBody UIDelega delega) {

        try {
            return new ValueResponse(delegheFacade.saveDelega(delega));
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }
    @RequestMapping(value = "/delega/changeState", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse changeState(@RequestBody UiDelegaChangeState change) {

        try {
            delegheFacade.changeState(change);
            return new SimpleResponse(false, "");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/delega/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            delegheFacade.deleteDelega(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }




    @RequestMapping(value = "/delegaforapp", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse save(@RequestBody UiAppDelega delega) {

        try {
            return new ValueResponse(delegheFacade.saveDelegaForApp(delega));
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


}
