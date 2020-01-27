package applica.feneal.admin.controllers.deleghe;

import applica.feneal.admin.fields.renderers.*;

import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;


import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.deleghe.bari.ProjectionDelegheFilter;
import applica.feneal.domain.model.core.deleghe.bari.RistornoCassaEdileFilter;

import applica.feneal.domain.model.core.ristorniEdilizia.RiepilogoRistornoPerLavoratore;

import applica.feneal.services.impl.deleghe.DelegheBariRistorniService;
import applica.feneal.services.impl.deleghe.DelegheBariService;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class DelegheBaricontroller {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private DelegheBariService delegheBariService;

    @Autowired
    private DelegheBariRistorniService delRistorniService;

    @RequestMapping(value="/deleghe/reportbari", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse proiettaDeleghe(@RequestBody ProjectionDelegheFilter params){

        try {
            List<DelegaBari> f =  delegheBariService.findDelegheBari(params.getLastDateStart(),params.getLastDateEnd(),params.getCompanyId());

            return new ValueResponse(delegheBariService.ConstructReportDelegheBari(f));
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value="/deleghe/ristornibaricassaedile", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse ristorniDelegheCassaEdile(@RequestBody RistornoCassaEdileFilter params){
        try {
            List<RiepilogoRistornoPerLavoratore> r = delRistorniService.retriveListaRiepilogoRistorni(params);
            return new ValueResponse(r);
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "deleghebari/home/{workerId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long workerId) {

        try {


            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("deleghe", delegheBariService.getAllWorkerDeleghe(workerId));

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "deleghe/homebari", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "/deleghebari/referente", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showPrintTessera(HttpServletRequest request) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("referentedelegabari");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("referente", String.class, "Referente", null, applicationContext.getBean(ReferenteForDelegaBariOptionalSelectorRenderer.class))
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.COLS, Values.COLS_12);


            Map<String, Object> data = new HashMap<>();

            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/delegabari/{id}/managementcontact", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse managementContact(@PathVariable("id") Long delegaId, String newManagement, HttpServletRequest request) {

        try {
            delegheBariService.addContactForDelegaBari(delegaId, newManagement);

            return new ValueResponse();
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/delegabari/{id}/deletecontact", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse deleteContact(@PathVariable("id") Long delegaId, HttpServletRequest request) {

        try {
            delegheBariService.deleteContactForDelegaBari(delegaId);

            return new ValueResponse();
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/reportdeleghebari",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("deleghebarireport");

            FormDescriptor formDescriptor = new FormDescriptor(form);


            formDescriptor.addField("stringLastDateStart", String.class, "Data protocollo da", null, applicationContext.getBean(DatePickerRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("stringLastDateEnd", String.class, "a", null, applicationContext.getBean(DatePickerRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("companyId", String.class, "Azienda", null, applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");



            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report deleghe");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/ristornideleghebaricassaedile",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchviewDelegheBariCassaEdile(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("deleghebaricassaedilereport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("competenceYear", String.class, "Anno di competenza", null, applicationContext.getBean(YearSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("period", String.class, "Periodo", null, applicationContext.getBean(PeriodSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("parithetic", String.class, "Ente", null, applicationContext.getBean(ParitheticRistorniDelegheBariSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_4)
                    .putParam(Params.ROW, "dt8")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("file1", Date.class, "File Deleghe", null,applicationContext.getBean(FileFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Ristorni deleghe Bari");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }




    @RequestMapping(value = "/proiettadeleghebari",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request) {

        try {


            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("test", "ciao");


            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "deleghe/proiezioni", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value="/deleghe/proiezioni", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse proiettaDeleghe(
            @RequestParam(value="prevDateStart", required=true) String prevDateStart,
            @RequestParam(value="prevDateEnd", required=true) String prevDateEnd,
            @RequestParam(value="lastDateStart", required=true) String lastDateStart,
            @RequestParam(value="lastDateEnd", required=true) String lastDateEnd,
            @RequestParam(value="delRev", required=true) boolean delRev,
            @RequestParam(value="delBia", required=true) boolean delBia,
            @RequestParam(value="iscIn", required=true) boolean iscIn,
            @RequestParam(value="iscCon", required=true) boolean iscCon,
            @RequestParam(value="iscRic", required=true) boolean iscRic



    ){

        try {
            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            ProjectionDelegheFilter p = new ProjectionDelegheFilter();
            p.setPrevDateStart(ff.parse(prevDateStart));
            p.setPrevDateEnd(ff.parse(prevDateEnd));
            p.setLastDateStart(ff.parse(lastDateStart));
            p.setLastDateEnd(ff.parse(lastDateEnd));


            p.setRevocheDeleghe(delRev);
            p.setRevocheBianche(delBia);
            p.setIscrizioniInefficaci(iscIn);
            p.setIscrizioniCongelate(iscCon);
            p.setIscrizioniIRiconferma(iscRic);

            return new ValueResponse(delegheBariService.ProiezioniDelegheBari(p));
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }



}
