package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.QuoteFacade;
import applica.feneal.admin.facade.QuoteImpiantiFissiFacade;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.form.renderers.QuoteImpiantiFormRenderer;
import applica.feneal.admin.viewmodel.quote.UiCreaQuoteImpiantiFissi;
import applica.feneal.admin.viewmodel.quote.UiLavoratoriQuoteImpiantiFissi;
import applica.feneal.domain.model.core.servizi.UiQuoteHeaderParams;
import applica.feneal.domain.model.core.servizi.search.UiQuoteImpiantiFissiSearchParams;
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
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by angelo on 15/04/2016.
 */
@Controller
public class QuoteBreviManoController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private QuoteFacade quoteFacade;


    @RequestMapping(value = "/quotebrevimano",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view2(HttpServletRequest request) {
        try{
            Form form = new Form();
            form.setRenderer(applicationContext.getBean(QuoteImpiantiFormRenderer.class));
            form.setIdentifier("quotebrevimano");

            FormDescriptor formDescriptor = new FormDescriptor(form);

            formDescriptor.addField("province", String.class, "Provincia", null, applicationContext.getBean(LoggedUserProvinceNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt")
                    .putParam(Params.FORM_COLUMN, " ");


            formDescriptor.addField("settore", String.class, "Settore",null, applicationContext.getBean(SectorTypeSelectRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");


            formDescriptor.addField("ente", String.class, "Ente bilaterale",null, applicationContext.getBean(ParithericNonOptionalSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");

            formDescriptor.addField("firm", String.class, "Azienda", null, applicationContext.getBean(AziendeSingleSearchFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");


            formDescriptor.addField("worker", String.class, "Lavoratore", null, applicationContext.getBean(LavoratoreSingleSearchableFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt1")
                    .putParam(Params.FORM_COLUMN, " ");


            formDescriptor.addField("dataInizio", String.class, "Data inizio",null, applicationContext.getBean(DatePickerRenderer.class)).putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, "  ");

            formDescriptor.addField("dataFine", String.class, "Data fine",null, applicationContext.getBean(DatePickerRenderer.class)).putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt3")
                    .putParam(Params.FORM_COLUMN, "  ");

            formDescriptor.addField("amount", Double.class, "Importo", null, applicationContext.getBean(DefaultFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt2")
                    .putParam(Params.FORM_COLUMN, "  ");

            //WorkersSingleSearchblafieldRenderer


//            formDescriptor.addField("competenceMonth", String.class, "Mese di competenza", null, applicationContext.getBean(OptionalDateMonthFieldRenderer.class))
//                    .putParam(Params.COLS, Values.COLS_12)
//                    .putParam(Params.ROW, "dt3")
//                    .putParam(Params.FORM_COLUMN, "  ");
//            formDescriptor.addField("competenceYear", String.class, "Anno di competenza", null, applicationContext.getBean(YearSelectFieldRenderer.class))
//                    .putParam(Params.COLS, Values.COLS_12)
//                    .putParam(Params.ROW, "dt4")
//                    .putParam(Params.FORM_COLUMN, "  ");
            formDescriptor.addField("contract", String.class, "Contratto", null, applicationContext.getBean(ContractSelectFieldRenderer.class))
                    .putParam(Params.COLS, Values.COLS_12)
                    .putParam(Params.ROW, "dt5")
                    .putParam(Params.FORM_COLUMN, "  ");




            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());
            response.setTitle("Creazione quota brevi mano");

            return response;
        } catch (FormCreationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        } catch (CrudConfigurationException e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value="/quotebrevimano/proceed", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse doQuotebrevimano(@RequestBody UiQuoteHeaderParams params){


        try{
            Long idQuota = quoteFacade.createRiepilogoQuoteManuali(params);
            UiQuoteHeaderParams data = new UiQuoteHeaderParams();

            data.setWorker(params.getWorker());
            data.setSettore(params.getSettore());
            data.setEnte(params.getEnte());
            data.setFirm(params.getFirm());
            data.setAmount(params.getAmount());
            data.setLevel(params.getLevel());
            data.setContract(params.getContract());



            quoteFacade.addDettaglioQuota(idQuota, data);


            return new ValueResponse(idQuota);



        }catch(Exception ex){
            return new ErrorResponse(ex.getMessage());
        }
    }
}


