package applica.feneal.admin.controllers.inps;

import applica.feneal.admin.facade.RistoniInpsfacades;
import applica.feneal.admin.fields.renderers.AziendeSingleSearchFieldRenderer;
import applica.feneal.admin.fields.renderers.DocumentFileRenderer;

import applica.feneal.admin.form.renderers.ReportsSearchFormRenderer;
import applica.feneal.admin.viewmodel.inps.FileToValidate;
import applica.feneal.admin.viewmodel.inps.IncrocioQuoteInps;
import applica.feneal.admin.viewmodel.inps.QuotaInpsDTO;
import applica.feneal.admin.viewmodel.inps.RistornoInpsSummary;
import applica.feneal.admin.viewmodel.quote.QuoteDbNazionaleExporter;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.deleghe.bari.ProjectionDelegheFilter;
import applica.feneal.domain.model.core.importData.CscRowValidator;

import applica.feneal.domain.model.core.inps.QuotaInps;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.ExcelReaderDataService;

import applica.feneal.services.InpsPugliaServices;
import applica.feneal.services.impl.excel.ExcelValidator;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.management.excel.ExcelInfo;
import applica.framework.security.Security;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class InpsController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private ExcelReaderDataService excelReader;

    @Autowired
    private InpsPugliaServices inpsSeervices;

    @Autowired
    private RistoniInpsfacades fac;

    @Autowired
    private Security sec;

    @Autowired
    private QuoteDbNazionaleExporter exporter;


    @RequestMapping(value="/quoteinps/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse proiettaDeleghe(@RequestBody ProjectionDelegheFilter params){

        try {
            List<QuotaInpsDTO> f = fac.findQuote(params.getLastDateStart(),params.getLastDateEnd());

            return new ValueResponse(f);
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }

    @RequestMapping(value = "/reportquoteinps",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("reportquoteinps");

            FormDescriptor formDescriptor = new FormDescriptor(form);


            formDescriptor.addField("stringLastDateStart", String.class, "Data valuta da", null, applicationContext.getBean(DatePickerRenderer.class))
                    .putParam(Params.PLACEHOLDER, "Da")
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("stringLastDateEnd", String.class, "a", null, applicationContext.getBean(DatePickerRenderer.class))
                    .putParam(Params.PLACEHOLDER, "A")
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt6")
                    .putParam(Params.FORM_COLUMN, " ");




            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report quote inps");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    @RequestMapping(value = "/incrociaquoteinps/inps", method = RequestMethod.GET)
    public void getAnagraficheTemplate(HttpServletResponse response) {
        try {
            // get your file as InputStream
            InputStream is = getClass().getResourceAsStream("/templates/inps.xlsx");
            // copy it to response's OutputStream+
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {

            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @RequestMapping(value = "/incrociaquoteinps/csc", method = RequestMethod.GET)
    public void getAnagraficheTemplatecsc(HttpServletResponse response) {
        try {
            // get your file as InputStream
            InputStream is = getClass().getResourceAsStream("/templates/Template csc.xlsx");
            // copy it to response's OutputStream+
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {

            throw new RuntimeException("IOError writing file to output stream");
        }

    }


    @RequestMapping(value = "/incrociaquoteinps/execute",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse executeIncrocio(@RequestBody IncrocioQuoteInps data){

        try{
            return new ValueResponse(fac.incrociaQuote(data));
        }catch (Exception ex){
            return new ErrorResponse(ex.getMessage());
        }


    }


    @RequestMapping(value = "/incrociaquoteinps/validate",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse validateFile(@RequestBody FileToValidate file) {


        try {
            List<QuotaInps> result = inpsSeervices.inserisciQuoteInps(file.getFilepath());

//            //invio i dati al dbnaizonale
            StringBuilder currentUsername = new StringBuilder();
            StringBuilder currentPassword = new StringBuilder();
            Province province = ((User) sec.getLoggedUser()).getDefaultProvince();//geo.getProvinceById(Integer.parseInt(data.getParams().getProvince()));
            exporter.calculateCredentials(currentUsername, currentPassword, province);

            exporter.exportInps(result,currentUsername.toString(), currentPassword.toString(),province.getDescription());
            return new ValueResponse("ok");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }



    }

    @RequestMapping(value = "/incrociaquoteinps/validate/csc",method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse validateFileCsc(@RequestBody FileToValidate file) {


        try {
            ExcelInfo i= excelReader.readExcelFile(file.getFilepath(),0,
                    0,0,new ExcelValidator(Arrays.asList("FISCALE",
                            "COGNOME_UTENTE",
                            "NOME_UTENTE",
                            "DATA DOMANDA NASPI",
                            "COGNOME_REFERENTE",
                            "NOME_REFERENTE")),new CscRowValidator());
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }


        return new ValueResponse("ok");
    }


    @RequestMapping(value = "/incrociaquoteinps",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse importQuoteInps(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(ReportsSearchFormRenderer.class));
            form.setIdentifier("incrociaQuoteInps");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("title", String.class, "Titolo incrocio", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("effectiveCost", String.class, "Percentuale Effettiva", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("rowCost", String.class, "Costo riga inps", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("retrunPercent", String.class, "Perc. ristorno lav. non edili", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("retrunPercentEdili", String.class, "Perc. ristorno lav. edili", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");
            formDescriptor.addField("costoTessera", String.class, "Costo tessera", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("file1", Date.class, "Aggiungi file Inps", null,applicationContext.getBean(DocumentFileRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

//            formDescriptor.addField("nomefile1", Date.class, "", null,applicationContext.getBean(EmptyFieldRenderer.class))
//                    .putParam(Params.COLS, Values.COLS_12)
//                    .putParam(Params.ROW, "dt1")
//                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("file2", Date.class, "Aggiungi file CSC", null,applicationContext.getBean(DocumentFileRenderer.class))
                    .putParam(Params.COLS, Values.COLS_6)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

//            formDescriptor.addField("nomefile2", Date.class, "", null,applicationContext.getBean(EmptyFieldRenderer.class))
//                    .putParam(Params.COLS, Values.COLS_12)
//                    .putParam(Params.ROW, "dt1")
//                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Incrocia quote inps");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }




    @RequestMapping(value = "/ristorniquoteinpsfull/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewRistonoHtml(HttpServletRequest request, @PathVariable long id) {

        try {

            return new ValueResponse(fac.getRistornoInpsSummaryById(id));

        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/ristorniquoteinpsfull/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            fac.deleteRistorno(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/ristorniquoteinps/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewRistono(HttpServletRequest request, @PathVariable long id) {

        try {

            RistornoInpsSummary c = fac.getRistornoInpsSummaryByIdWithoutQuote(id);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("summary", c);

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "inps/ristorno", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/ristorniquoteinps",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewRistoni(HttpServletRequest request) {

        try {

            HashMap<String, Object> model = new HashMap<String, Object>();


            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "inps/ristorni", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/ristorniquoteinpsfull",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewRistoniFull(HttpServletRequest request) {

        try {
            return new ValueResponse(inpsSeervices.getRistorni());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }
}
