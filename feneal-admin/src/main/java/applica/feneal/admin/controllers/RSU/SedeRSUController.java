package applica.feneal.admin.controllers.RSU;

import applica.feneal.admin.facade.RSU.AziendeRsuFacade;
import applica.feneal.admin.facade.RSU.SedeRsuFacade;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.form.renderers.MulticolumnFormRenderer;
import applica.feneal.admin.viewmodel.RSU.UiAnagraficaAziendaRsu;
import applica.feneal.admin.viewmodel.RSU.UiAnagraficaSedeRsu;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.services.GeoService;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.feneal.services.RSU.SedeRsuService;
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
import java.util.Map;

@Controller
public class SedeRSUController {

    @Autowired
    private Security sec;

    @Autowired
    private SedeRsuService svc;

    @Autowired
    private AziendaRsuService azSrvc;

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SedeRsuFacade sedeRsuFacade;

    @RequestMapping(value = "/sedersu",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse create(Long firmId) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("sedersu");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Descrizione", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", "", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("city", String.class, "Città", "", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("address", String.class, "Indir.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("firmId", String.class, "lastModificationDate", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, " ");

            AziendaRSU d = azSrvc.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),firmId);

            if (d!= null){
                Map<String, Object> data = new HashMap<>();
                data.put("firmId",d.getLid());

                form.setData(data);
            }

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione sede per azienda RSU");


            return response;


        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/sedersu/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            sedeRsuFacade.deleteAzienda(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/sedersu/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse update(@PathVariable long id) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(MulticolumnFormRenderer.class));
            form.setIdentifier("sedersu");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("id", String.class, "id", null, applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("description", String.class, "Descrizione", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("province", String.class, "Provincia", "", applicationContext.getBean(OptionalProvinceFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt1").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("city", String.class, "Città", "", applicationContext.getBean(OptionalCityFieldRenderer.class)).putParam(Params.COLS, Values.COLS_6).putParam(Params.ROW, "dt8").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("address", String.class, "Indir.", "", applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt2").putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("firmId", String.class, "lastModificationDate", "", applicationContext.getBean(HiddenFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12).putParam(Params.ROW, "dt6").putParam(Params.FORM_COLUMN, " ");

            SedeRSU d = svc.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),id);

            if (d!= null){
                Map<String, Object> data = new HashMap<>();

                data.put("id", id);
                data.put("description", d.getDescription());
                data.put("firmId",d.getAziendaRSU().getLid());

                if (StringUtils.isEmpty(d.getProvince()))
                    data.put("province", null);
                else
                    data.put("province", geoSvc.getProvinceByName(d.getProvince()));

                if (StringUtils.isEmpty(d.getCity()))
                    data.put("city", null);
                else
                    data.put("city", geoSvc.getCityByName(d.getCity()));

                data.put("address", d.getAddress());


                form.setData(data);
            }


            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Aggiornamento sede RSU");


            return response;


        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "sedirsu/home/{firmId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long firmId) {

        try {


            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("deleghe", sedeRsuFacade.getAllSediAziendaRsu(firmId));

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "RSU/sedeRsuHome", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/sedersu", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse saveSedeRsu(@RequestBody UiAnagraficaSedeRsu anag) {

        try {
            long id = sedeRsuFacade.saveAnagrafica(anag);
            return new ValueResponse(id);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }
}
