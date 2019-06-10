package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.ReportQuoteVareseFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.QuoteVareseSearchFormRenderer;

import applica.feneal.domain.model.core.quote.varese.UiDettaglioQuotaVarese;
import applica.feneal.domain.model.core.quote.varese.UiQuoteVareseObject;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
import applica.feneal.services.ComunicazioniService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.widgets.CrudConfigurationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormCreationException;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class ReportQuoteVareseController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ReportQuoteVareseFacade reportQuoteVareseFacade;

    @Autowired
    private ComunicazioniService comSvc;



    @RequestMapping(value="/quotevarese/report", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse reportQuoteVar(@RequestBody UiQuoteVareseReportSearchParams params){
        UiQuoteVareseObject obj;
        try {
            obj = reportQuoteVareseFacade.reportQuote(params);
            return new ValueResponse(obj);
        } catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }


    @RequestMapping(value = "/quotevarese",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse searchview(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(QuoteVareseSearchFormRenderer.class));
            form.setIdentifier("quotevaresereport");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("quota1", String.class, "Quota1", null, applicationContext.getBean(SelectQuoteVareseSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("quota2", String.class, "Quota2", null, applicationContext.getBean(OptionalQuoteVareseSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, " ");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Report Quote");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }



    @RequestMapping(value = "/inviaSMS", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse gridInvio(@RequestBody List<UiDettaglioQuotaVarese> quote) {
        try {

            comSvc.sendParametricSms(quote);
            return new ValueResponse("ok");

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value="/c", method= RequestMethod.GET)
    public void findLocalLavoratorinew(HttpServletResponse response, @RequestParam(value="a", required=true, defaultValue="") String a){

        try {
            String file = reportQuoteVareseFacade.createFile(a);
            // get your file as InputStream
            InputStream is = new FileInputStream(new File(file));

//            String outputFile = "C:\\Users\\felic\\Desktop\\Test1.pdf";
//
//
//            XWPFDocument document = new XWPFDocument(is);
//            File outFile =new File(outputFile);
//            OutputStream out = new FileOutputStream(outFile);
//            PdfOptions options = null;
//            PdfConverter.getInstance().convert(document, out, options);


            // copy it to response's OutputStream+
            response.setContentType("application/pdf");
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}
