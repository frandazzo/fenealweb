package applica.feneal.admin.controllers.RSU;

import applica.feneal.admin.facade.RSU.AziendeRsuFacade;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.form.renderers.MulticolumnFormRenderer;
import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.RSU.UiAnagraficaSedeRsu;
import applica.feneal.admin.viewmodel.app.dashboard.aziende.AppAzienda;
import applica.feneal.admin.viewmodel.aziende.UiAziendaAnagraficaSummary;
import applica.feneal.admin.viewmodel.aziende.UiCompleteAziendaSummary;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.services.GeoService;
import applica.feneal.services.RSU.AziendaRsuService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by felicegramegna on 23/02/2021.
 */

@Controller
public class AziendeRSUController {

    @Autowired
    private Security sec;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AziendaRsuService svc;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private AziendeRsuFacade aziendeRsuFacade;

    @RequestMapping(value = "/firmrsu",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse create(HttpServletRequest request) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("firmrsu");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Ragione sociale", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", "", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("city", String.class, "Città", "", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("address", String.class, "Indir.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("cap", String.class, "CAP", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt4").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("phone", String.class, "Telefono", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt5").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("piva", String.class, "P. Iva", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, "  ");

            formDescriptor.addField("companyCreator", String.class, "companyCreator", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("usernameCreator", String.class, "usernameCreator", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("createDate", String.class, "createDate", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("companyLastModification", String.class, "companyLastModification", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt4").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("usernameLastModification", String.class, "usernameLastModification", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt5").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("lastModificationDate", String.class, "lastModificationDate", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, "  ");



            formDescriptor.addField("notes", String.class, "Note", "", applicationContext.getBean(TextAreaFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt7").putParam(Params.FORM_COLUMN, "  ");


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione anagrafica nazionale azienda RSU");


            return response;


        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/firmrsu/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse update(HttpServletRequest request, @PathVariable long id) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("firmrsu");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Ragione sociale", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", "", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("city", String.class, "Città", "", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("address", String.class, "Indir.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("cap", String.class, "CAP", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt4").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("phone", String.class, "Telefono", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt5").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("piva", String.class, "P. Iva", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, "  ");

            formDescriptor.addField("companyCreator", String.class, "companyCreator", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("usernameCreator", String.class, "usernameCreator", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("createDate", String.class, "createDate", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("companyLastModification", String.class, "companyLastModification", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt4").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("usernameLastModification", String.class, "usernameLastModification", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt5").putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("lastModificationDate", String.class, "lastModificationDate", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, "  ");



            formDescriptor.addField("notes", String.class, "Note", "", applicationContext.getBean(TextAreaFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt7").putParam(Params.FORM_COLUMN, "  ");

            AziendaRSU d = svc.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),id);

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

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = dateFormat.format(d.getCreateDate());

                data.put("companyCreator", d.getCompanyCreator());
                data.put("usernameCreator", d.getUsernameCreator());
                data.put("createDate", strDate);

                form.setData(data);
            }


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Aggiornamento azienda RSU");


            return response;


        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/firmrsu/summary/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long id) {

        try {

            UiCompleteAziendaSummary c = aziendeRsuFacade.getFirmById(id);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("summary", c);

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "RSU/firmRsuSummary", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/remotefirmrsusearchforapp",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse remoteFirmsSearch(HttpServletRequest request, @RequestParam(value="description", required=false, defaultValue="") String description)throws Exception {


        try {
            List<UiAziendaAnagraficaSummary> appAziendaList = aziendeRsuFacade.findAziendeRsu(description);

            return new ValueResponse(appAziendaList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "remotefirmrsu/summary/{firmId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse remoteSedeSummary(HttpServletRequest request, @PathVariable long firmId) {

        try {

            UiCompleteAziendaSummary azienda =  aziendeRsuFacade.getRemoteAziendaRsu(firmId);

            return new ValueResponse(azienda);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/firmrsu/remote",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String viewRemoteWorkerAnagrafica(HttpServletRequest request, String description) throws Exception {


        try {

            //qui recupero se cè l'id dell'azienda con quella ragione sociale
            //e se non cè lo predno dalla tabella dei lavoratori del db nazionel e lo creo per il territorio dell'utente
            //corrente
            long idWorker = aziendeRsuFacade.getIdAziendaRsuByDescriptionorCreateIfNotExist(description);
            if (idWorker == -1)
                throw new Exception("Azienda non trovata");

            return "redirect:/firmrsu/summary/" + String.valueOf(idWorker);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @RequestMapping(value = "/firmrsu/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            aziendeRsuFacade.deleteAzienda(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/firmrsu", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse saveRsu(@RequestBody UiAnagraficaAziendaRsu anag) {

        try {
            long id = aziendeRsuFacade.saveAnagrafica(anag);
            return new ValueResponse(id);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }
}
