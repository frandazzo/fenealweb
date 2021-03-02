package applica.feneal.admin.controllers.RSU;

import applica.feneal.admin.facade.RSU.ReportElezioniRsuFacade;
import applica.feneal.admin.fields.renderers.CustomCheckboxFieldRenderer;
import applica.feneal.admin.fields.renderers.DateFromYearFieldRenderer;
import applica.feneal.domain.model.RSU.Dto.ElezioneDto;

import applica.feneal.domain.model.RSU.Dto.UiElezioneDtoForListe;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.SedeRSU;
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
import applica.framework.widgets.fields.renderers.ReadOnlyFieldRenderer;

import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
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
public class ReportElezioniRsuController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private Security sec;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ReportElezioniRsuFacade facade;

    @Autowired
    private SedeRsuService sedeRsuService;


    @RequestMapping(value = "/reportrsu/datigenerali/{sedeId}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse createFormForDatiGenerali(HttpServletRequest request, @PathVariable long sedeId) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("datigenerali");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("firmrsu", String.class, "Azienda Rsu", null, applicationContext.getBean(ReadOnlyFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("sedersu", String.class, "Sede Rsu", null, applicationContext.getBean(ReadOnlyFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("anno", String.class, "Anno",null, applicationContext.getBean(DateFromYearFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");

            SedeRSU d = sedeRsuService.getSedeRsuById(((User) sec.getLoggedUser()).getLid(),sedeId);

            if (d!= null){
                Map<String, Object> data = new HashMap<>();

                data.put("sedersu", d.getDescription());
                data.put("firmrsu",d.getAziendaRSU().getDescription());
                form.setData(data);
            }

            FormResponse response = new FormResponse();
            response.setContent(form.writeToString());

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/datigenerali",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse setdatiGenerali(HttpServletRequest request, Long firmRsu, Long sedeRsu, int anno) {

        try {
            ElezioneDto dto = facade.setDatiGeneraliElezioneRsu(firmRsu,sedeRsu,anno);
            return new ValueResponse(dto);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "reportelezionirsu", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse view(HttpServletRequest request) {

        try {


            HashMap<String, Object> model = new HashMap<String, Object>();

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "RSU/reportRsu", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/createlista",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse addListToCalcoloAttribuzione(@RequestBody UiElezioneDtoForListe dto) {

        try {
            ElezioneDto newDto = facade.createAndAddListToElezioneRsu(dto);
            return new ValueResponse(newDto);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/createlista",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse createFormForLista(HttpServletRequest request) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("lista");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("name", String.class, "Nome lista", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("firmataria", Boolean.class, "Firmataria CCNL", null, applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();
            response.setContent(form.writeToString());

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


