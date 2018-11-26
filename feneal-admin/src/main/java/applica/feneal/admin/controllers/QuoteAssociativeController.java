package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.QuoteFacade;
import applica.feneal.admin.fields.renderers.AziendeSingleSearchFieldRenderer;
import applica.feneal.admin.fields.renderers.ContractSelectFieldRenderer;
import applica.feneal.admin.fields.renderers.LavoratoreSingleSearchableFieldRenderer;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuotaView;
import applica.feneal.admin.viewmodel.quote.UiQuoteAssociativeView;
import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.UpdatableDettaglioQuota;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.domain.model.core.servizi.UiQuoteHeaderParams;
import applica.feneal.services.QuoteAssociativeService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.security.AuthenticationException;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
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
import java.io.IOException;
import java.util.*;

/**
 * Created by angelo on 26/05/2016.
 */
@Controller
public class QuoteAssociativeController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private QuoteFacade quoteFacade;

    @Autowired
    private QuoteAssociativeService qServ;

    @Autowired
    private QuoteAssocRepository riepilogoQuoteRep;



    @RequestMapping(value = "/quoteassociative",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewQuoteAssociative(HttpServletRequest request) {

        try {

            List<RiepilogoQuoteAssociative> quoteAssociative = quoteFacade.getQuoteAssociative();

            HashMap<String, Object> model = new HashMap<String, Object>();

            PartialViewRenderer renderer = new PartialViewRenderer();
            UiQuoteAssociativeView quoteAssocView = new UiQuoteAssociativeView();
            String content = renderer.render(viewResolver, "quote/quoteAssociative", model, LocaleContextHolder.getLocale(), request);
            quoteAssocView.setContent(content);
            quoteAssocView.setQuoteAssociative(quoteAssociative);

            return new ValueResponse(quoteAssocView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/importquote/{companyId}")
    public @ResponseBody SimpleResponse importData(@PathVariable int companyId, @RequestBody RiepilogoQuotaDTO dto) {
        if (dto == null)
            return new ValueResponse("vuoto");
        try {
            qServ.importQuoteAssociativeFromDatiTerritori(companyId, dto);
        } catch (AuthenticationException e) {
            return new ErrorResponse("Auth exception");
        }
        return new ValueResponse("OK");
    }

    @RequestMapping(value="/importquoteliberi/{companyId}")
    public @ResponseBody SimpleResponse importDataLiberi(@PathVariable int companyId, @RequestBody RiepilogoQuotaDTO dto) {
        if (dto == null)
            return new ValueResponse("vuoto");
        try {
            qServ.importLiberiFromDatiTerritori(companyId, dto);
        } catch (AuthenticationException e) {
            return new ErrorResponse("Auth exception");
        }
        return new ValueResponse("OK");
    }


    @RequestMapping(value = "/quoteassociative/dettaglio/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long id, Long idWorker) {

        try {

            List<UiDettaglioQuota> quoteDetails = quoteFacade.getDettaglioQuota(id, idWorker);

            HashMap<String, Object> model = new HashMap<String, Object>();

            PartialViewRenderer renderer = new PartialViewRenderer();
            UiDettaglioQuotaView dettaglioView = new UiDettaglioQuotaView();
            String content = renderer.render(viewResolver, "quote/dettaglioQuota", model, LocaleContextHolder.getLocale(), request);
            dettaglioView.setContent(content);
            dettaglioView.setQuoteDetails(quoteDetails);

            return new ValueResponse(dettaglioView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/quoteassociative/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            quoteFacade.deleteQuota(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping("/quoteassociative/{id}/downloadlog")
    public void downloadLogFile(@PathVariable("id") long id, HttpServletResponse response) {

        try {
            quoteFacade.downloadFile(id, response);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @RequestMapping("/quoteassociative/{id}/downloadoriginal")
    public void downloadoriginalFile(@PathVariable("id") long id, HttpServletResponse response) {

        try {
            quoteFacade.downloadOriginalFile(id, response);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }


    @RequestMapping("/quoteassociative/{id}/downloadxml")
    public void downloadxmlFile(@PathVariable("id") long id, HttpServletResponse response) {

        try {
            quoteFacade.downloadXmlFile(id, response);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @RequestMapping(value="/quoteassociativeitem/add/{quotaId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse createQuoteItem(@PathVariable long quotaId,@RequestBody UiQuoteHeaderParams data) {

        try{

            UiDettaglioQuota r = quoteFacade.addDettaglioQuota(quotaId, data);

            return new ValueResponse(r);
        }catch(Exception e){
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/quoteassociativeitem/{quotaId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showAddItemForm(HttpServletRequest request, @PathVariable long quotaId) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("quoteitem");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("worker", String.class, "Lavoratore",null, applicationContext.getBean(LavoratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.COLS, Values.COLS_12);

            formDescriptor.addField("firm", String.class, "Azienda", null, applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.COLS, Values.COLS_12);

            formDescriptor.addField("amount", Double.class, "Importo", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.COLS, Values.COLS_12);


            formDescriptor.addField("contract", String.class, "Contratto", null, applicationContext.getBean(ContractSelectFieldRenderer.class))
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.COLS, Values.COLS_12);


            formDescriptor.addField("level", String.class, "Livello", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.ROW, "dt4")
                    .putParam(Params.COLS, Values.COLS_12);


            formDescriptor.addField("settore", String.class, "Settore", null, applicationContext.getBean(HiddenFieldRenderer.class))
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.COLS, Values.COLS_12);




            //recupero il settore della quota
            RiepilogoQuoteAssociative r = riepilogoQuoteRep.get(quotaId).orElse(null);
            if (r == null)
                return new ErrorResponse("Quota non trovata");

            Map<String, Object> data = new HashMap<>();
            data.put("settore", r.getSettore());
            data.put("amount", 0);
            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/quoteassociativeitem/delete/{quotaId}/{itemId}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delQuoteItem(@PathVariable long quotaId,@PathVariable long itemId) {

        try{

            quoteFacade.deleteDettaglioQuota(quotaId, itemId);

            return new ValueResponse("ok");
        }catch(Exception e){
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/quoteassociativeform", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse duplicatequoteform(HttpServletRequest request) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("quoteedit");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("dataInizio", String.class, "Data inizio",null, applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, "  ");

            formDescriptor.addField("dataFine", String.class, "Data fine",null, applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, "  ");


            GregorianCalendar c = new GregorianCalendar();
            int year = c.get(Calendar.YEAR) + 1;


            Map<String, Object> data = new HashMap<>();
            data.put("dataInizio", String.format("01/01/%s", year));
            data.put("dataFine", String.format("31/12/%s", year));
            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/duplicaquoteassociativeitem/{quotaId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse duplicaQuota(@PathVariable long quotaId,@RequestBody UiQuoteHeaderParams data) {

        try{

            quoteFacade.duplicaQuota(quotaId, data);

            return new ValueResponse("ok");
        }catch(Exception e){
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value="/quoteassociativeitem/update/{quotaId}/{itemId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse updateQuoteItem(@PathVariable long quotaId, @PathVariable long itemId, @RequestBody UpdatableDettaglioQuota updatedData) {

        try{

            quoteFacade.updateDettaglioQuota(quotaId, itemId, updatedData);

            return new ValueResponse("ok");
        }catch(Exception e){
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/modifycompetencequoteassociativeitem/{quotaId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse modifyCompetenceQuotaItems(@PathVariable long quotaId,@RequestBody UiQuoteHeaderParams data) {

        try{

            quoteFacade.modifyCompetenceQuotaItems(quotaId, data);

            return new ValueResponse("ok");
        }catch(Exception e){
            return new ErrorResponse(e.getMessage());
        }

    }


}


