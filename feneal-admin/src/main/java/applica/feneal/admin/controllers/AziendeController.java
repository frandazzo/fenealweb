package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.AziendeFacade;
import applica.feneal.admin.facade.LiberiFacade;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.form.renderers.MulticolumnFormRenderer;
import applica.feneal.admin.viewmodel.app.dashboard.aziende.AppAzienda;
import applica.feneal.admin.viewmodel.aziende.UiAnagraficaAzienda;
import applica.feneal.admin.viewmodel.aziende.UiCompleteAziendaSummary;
import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.services.AziendaService;
import applica.feneal.services.DelegheService;
import applica.feneal.services.GeoService;
import applica.feneal.services.LavoratoreService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.security.Security;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fgran on 07/04/2016.
 */
@Controller
public class AziendeController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private Security security;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AziendeFacade tcFacade;


    @Autowired
    private GeoService geoSvc;


    @Autowired
    private LiberiFacade libFac;


    @Autowired
    private AziendaService svc;

    @Autowired
    private LavoratoreService lavsvc;

    @Autowired
    private DelegheService delegheService;



    @RequestMapping(value = "/remotefirmsearchforapp",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse remoteFirmsSearch(HttpServletRequest request, @RequestParam(value="description", required=true) String description)throws Exception {


        try {
            List<AppAzienda> appAziendaList = tcFacade.findAziende(description);

            return new ValueResponse(appAziendaList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/firmforapp",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse getRemoteWorkerAnagrafica(HttpServletRequest request, @RequestParam(value="description", required=true) String description)throws Exception {


        try {

            if (StringUtils.isEmpty(description))
                throw new Exception("Descrizione nulla");



            return new ValueResponse(tcFacade.getAppAziendaByDescriptionorCreateIfNotExist(description));

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }





    @RequestMapping(value = "/firm/{id}/iscrizioni", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse iscritti(@PathVariable long id) {

        try {
            List<Lavoratore> l = lavsvc.findCurrentIscrizioniForAzienda(id);
            return new ValueResponse(l);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/firm/{id}/noniscrizioni", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse noniscritti(@PathVariable long id) {

        try {
            List<UiLibero> l = libFac.convertLiberiToUiLiberi(lavsvc.findNonIscrizioniForAzienda(id));
            return new ValueResponse(l);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }
    @RequestMapping(value = "/firm/{id}/delegheedili", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delegheedili(@PathVariable long id) {

        try {
            List<Lavoratore> l = delegheService.findLavoratoriConDelegaEdilePerAzienda(id);
            return new ValueResponse(l);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/firm/remote",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String viewRemoteWorkerAnagrafica(HttpServletRequest request, String description) throws Exception {


        try {

            //qui recupero se cè l'id dell'azienda con quella ragione sociale
            //e se non cè lo predno dalla tabella dei lavoratori del db nazionel e lo creo per il territorio dell'utente
            //corrente
            long idWorker = tcFacade.getIdAziendaByDescriptionorCreateIfNotExist(description);
            if (idWorker == -1)
                throw new Exception("Azienda non trovata");

            return "redirect:/firm/summary/" + String.valueOf(idWorker);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }





    @RequestMapping(value = "/firm/summary/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long id) {

        try {

            UiCompleteAziendaSummary c = tcFacade.getFirmById(id);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("summary", c);

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "aziende/firmSummary", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/firm",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse create(HttpServletRequest request) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("firm");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Ragione sociale", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", "", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("city", String.class, "Città", "", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("address", String.class, "Indir.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("cap", String.class, "CAP", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("phone", String.class, "Telefono", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("piva", String.class, "P. Iva", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");



            formDescriptor.addField("notes", String.class, "Note", "", applicationContext.getBean(TextAreaFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione azienda");


            return response;


        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/firm/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse update(HttpServletRequest request, @PathVariable long id) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("firm");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Ragione sociale", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", "", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("city", String.class, "Città", "", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("address", String.class, "Indir.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("cap", String.class, "CAP", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");


            formDescriptor.addField("phone", String.class, "Telefono", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("piva", String.class, "P. Iva", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_3).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");


            formDescriptor.addField("notes", String.class, "Note", "", applicationContext.getBean(TextAreaFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");

            Azienda d = svc.getAziendaById(((User) security.getLoggedUser()).getLid(),id);

            if (d!= null){
                Map<String, Object> data = new HashMap<>();

                data.put("id", id);
                data.put("description", d.getDescription());

                if (StringUtils.isEmpty(d.getProvince()))
                    data.put("province", null);
                else
                    data.put("province", geoSvc.getProvinceByName(d.getProvince()));

                if (StringUtils.isEmpty(d.getCity()))
                    data.put("city", null);
                else
                    data.put("city", geoSvc.getCityByName(d.getCity()));

                data.put("address", d.getAddress());
                data.put("notes", d.getNotes());
                data.put("piva", d.getPiva());
                data.put("phone", d.getPhone());

                form.setData(data);
            }


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Aggiornamento azienda");


            return response;


        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/firm", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse save(@RequestBody UiAnagraficaAzienda anag) {

        try {
            long id = tcFacade.saveAnagrafica(anag);
            return new ValueResponse(id);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }




    @RequestMapping(value = "/firm/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            tcFacade.deleteAzienda(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

}
