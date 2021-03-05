package applica.feneal.admin.controllers.RSU;

import applica.feneal.admin.facade.RSU.ReportElezioniRsuFacade;
import applica.feneal.admin.fields.renderers.CustomCheckboxFieldRenderer;
import applica.feneal.admin.fields.renderers.DateFromYearFieldRenderer;
import applica.feneal.admin.fields.renderers.RSU.OptionalSedeRsuFieldRenderer;
import applica.feneal.admin.form.renderers.MulticolumnFormRenderer;
import applica.feneal.domain.model.RSU.Dto.*;

import applica.feneal.domain.model.RSU.Election.ListaElettorale;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
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
import java.util.List;
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
    private AziendaRsuService aziendaRsuService;


    @RequestMapping(value = "/reportrsu/datigenerali/{firmId}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse createFormForDatiGenerali(HttpServletRequest request, @PathVariable long firmId) {



        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("datigenerali");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("firmrsu", String.class, "Azienda Rsu", null, applicationContext.getBean(ReadOnlyFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("sedersu", String.class, "Sede Rsu", null, applicationContext.getBean(OptionalSedeRsuFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("anno", String.class, "Anno",null, applicationContext.getBean(DateFromYearFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");

            AziendaRSU d = aziendaRsuService.getAziendaRsuById(((User) sec.getLoggedUser()).getLid(),firmId);

            if (d!= null){
                Map<String, Object> data = new HashMap<>();

                data.put("firmrsu", d.getDescription());
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

    @RequestMapping(value = "/reportrsu/checklistdata",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse setListDataCalcoloAttribuzione(@RequestBody UiElezioeDtoCheckListeData dto) {

        try {
            ElezioneDto newDto = facade.setListDataCalcoloAttribuzione(dto);
            return new ValueResponse(newDto);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/editlista",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse editListToCalcoloAttribuzione(@RequestBody UiElezioneDtoForListe dto) {

        try {
            ElezioneDto newDto = facade.editListToElezioneRsu(dto);
            return new ValueResponse(newDto);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/deletelista",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse deleteListToCalcoloAttribuzione(@RequestBody UiElezioneDtoForListe dto) {
        try {
            ElezioneDto newDto = facade.deleteFromListToElezioneRsu(dto);
            return new ValueResponse(newDto);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/esitovotazione/listvotazioni",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse retrieveListVotazioniCalcoloAttribuzione(@RequestBody UiElezioeDtoCheckListeData dto) {
        try {
            List<UiEsitoVotazioneListe> esitiListe = facade.retrieveListVotazioniCalcoloAttribuzione(dto.getDto().getListe());
            return new ValueResponse(esitiListe);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/reportrsu/esitovotazione",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse setEsitivotazioneForCalcoloAttribuzione(@RequestBody UiEsitoVotazione dto) {
        try {
            ElezioneDto newDto = facade.createVotazioniAndSetEsitiVotazioni(dto);
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

    @RequestMapping(value = "/reportrsu/esitovotazioni",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse createFormEsitoVotazioni(HttpServletRequest request) {
        try {
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("esitovotazione");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("aventidiritto", Integer.class, "Aventi diritto", "Preliminari votazione", applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_8)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("rsueleggibili", Integer.class, "RSU Eleggibili", "Preliminari votazione", applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_8)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("addschedenulle", Boolean.class, "Aggiungi schede nulle per il calcolo del numero dei votanti", "Preliminari votazione", applicationContext.getBean(CustomCheckboxFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("schedebianche", Integer.class, "Schede bianche", "Esito votazione", applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_8)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("schedenulle", Integer.class, "Schede nulle", "Esito votazione", applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_8)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, "  ");

            Map<String, Object> data = new HashMap<>();

            data.put("aventidiritto", 0);
            data.put("rsueleggibili", 0);
            data.put("schedebianche", 0);
            data.put("schedenulle", 0);


            form.setData(data);

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

    @RequestMapping(value = "/reportrsu/editlista",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse editFormForLista(HttpServletRequest request, String nome, Boolean firmataria) {
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

            Map<String, Object> data = new HashMap<>();

            data.put("name", nome);
            if(firmataria == true){
                data.put("firmataria", ":notchecked");
            }else{
                data.put("firmataria", ":checked");
            }

            form.setData(data);

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


