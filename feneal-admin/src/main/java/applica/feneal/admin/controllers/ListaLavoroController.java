package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.*;
import applica.feneal.admin.fields.renderers.LavoratoreSingleSearchableFieldRenderer;
import applica.feneal.admin.fields.renderers.ListaLavoroSingleSearchableInputRenderer;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiLavoratori;
import applica.feneal.admin.viewmodel.lavoratori.UiDettaglioListaLavoroComparisonView;
import applica.feneal.admin.viewmodel.lavoratori.UiDettaglioListaLavoroView;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.admin.viewmodel.reports.*;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.feneal.domain.model.core.lavoratori.ListeLavoroComparison;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.FormResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
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

/**
 * Created by fgran on 09/06/2016.
 */
@Controller
@RequestMapping("/listalavoro")
public class ListaLavoroController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ListeLavoroFacade listFac;

    @Autowired
    private ComunicazioniFacade comFac;

    @Autowired
    private IqaFacade iqaFac;

    @Autowired
    private LiberiFacade liberiFac;

    @Autowired
    private DocumentiFacade docFac;

    @Autowired
    private IscrittiFacade iscrittiFac;

    @Autowired
    private QuoteFacade quoteFacade;

    @Autowired
    private DelegheFacade delegheFac;

    @Autowired
    private RichiesteFacade richFac;

    @Autowired
    private MagazzinoDelegheFacade magFac;



    @RequestMapping(value = "/comunicazioni/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaCom(@RequestBody List<UiComunicazione> comunicazioni, @PathVariable String description) {

        try {
            ListaLavoro l1 = comFac.createListalavoro(comunicazioni, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/documenti/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaDoc(@RequestBody List<UiDocumento> documenti, @PathVariable String description) {

        try {
            ListaLavoro l1 = docFac.createListalavoro(documenti, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/iscritti/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaIscritti(@RequestBody List<UiIscritto> iscritti, @PathVariable String description) {

        try {
            ListaLavoro l1 = iscrittiFac.createListalavoro(iscritti, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/incassiquote/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaIncassiQuote(@RequestBody List<UiDettaglioQuota> iqa, @PathVariable String description) {

        try {
            ListaLavoro l1 = iqaFac.createListalavoro(iqa, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/liberi/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaLiberi(@RequestBody List<UiLibero> liberi, @PathVariable String description) {

        try {
            ListaLavoro l1 = liberiFac.createListalavoro(liberi, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/deleghe/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaDeleghe(@RequestBody List<UiDelega> deleghe, @PathVariable String description) {

        try {
            ListaLavoro l1 = delegheFac.createListalavoro(deleghe, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/richieste/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaRichieste(@RequestBody List<UiRichiesta> richieste, @PathVariable String description) {

        try {
            ListaLavoro l1 = richFac.createListalavoro(richieste, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/magazzinodeleghe/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaMagazzinoDeleghe(@RequestBody List<UiMagazzinoDeleghe> deleghe, @PathVariable String description) {

        try {
            ListaLavoro l1 = magFac.createListalavoro(deleghe, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/accettaDeleghe/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaAccettaDeleghe(@RequestBody List<UiDelega> deleghe, @PathVariable String description) {

        try {
            ListaLavoro l1 = delegheFac.createListalavoro(deleghe, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/inoltraDeleghe/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaInoltraDeleghe(@RequestBody List<UiDelega> deleghe, @PathVariable String description) {

        try {
            ListaLavoro l1 = delegheFac.createListalavoro(deleghe, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/quoteAssociative/{description}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListaQuoteAssociative(@RequestBody List<UiDettaglioQuota> quote, @PathVariable String description) {

        try {
            ListaLavoro l1 = quoteFacade.createListalavoro(quote, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/operations", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showListaLavoroOperations(HttpServletRequest request) {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("listaoperations");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("listaOp", String.class, "Altra lista", null, applicationContext.getBean(ListaLavoroSingleSearchableInputRenderer.class)).putParam(Params.ROW, "dt1").putParam(Params.COLS, Values.COLS_12);
            formDescriptor.addField("description", String.class, "Descrizione nuova lista", null, applicationContext.getBean(DefaultFieldRenderer.class)).putParam(Params.ROW, "dt").putParam(Params.COLS, Values.COLS_12);

            Map<String, Object> data = new HashMap<>();

            form.setData(data);

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse mergeWithOtherLista(long listaId, long otherListaId, String description) {

        try {
            ListaLavoro l1 = listFac.mergeLists(listaId, otherListaId, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/exclude", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse excludeWithOtherLista(long listaId, long otherListaId, String description) {

        try {
            ListaLavoro l1 = listFac.excludeLists(listaId, otherListaId, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/intersect", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse intersectWithOtherLista(long listaId, long otherListaId, String description) {

        try {
            ListaLavoro l1 = listFac.intersectLists(listaId, otherListaId, description);
            return new ValueResponse(l1.getLid());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse compareWithOtherLista(long listaId, long otherListaId, String description, HttpServletRequest request) {

        try {
            ListeLavoroComparison l = listFac.compareLists(listaId, otherListaId, description);

            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("listaDescr_aMinusB", l.getaMinusBList().getDescription());
            model.put("listaDescr_bMinusA", l.getbMinusAListr().getDescription());
            model.put("listaDescr_intersection", l.getIntersectionList().getDescription());
            model.put("listaId", listaId);

            PartialViewRenderer renderer = new PartialViewRenderer();
            UiDettaglioListaLavoroComparisonView dettaglioView = new UiDettaglioListaLavoroComparisonView();
            String content = renderer.render(viewResolver, "listelavoro/listaComparisonSummary", model, LocaleContextHolder.getLocale(), request);
            dettaglioView.setContent(content);
            dettaglioView.setLavoratoriLista_aMinusB(l.getaMinusBList().getLavoratori());
            dettaglioView.setLavoratoriLista_bMinusA(l.getbMinusAListr().getLavoratori());
            dettaglioView.setLavoratoriLista_intersection(l.getIntersectionList().getLavoratori());

            return new ValueResponse(dettaglioView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/notification/{notificationId}/{type}")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(@PathVariable long notificationId, @PathVariable String type) {

        try {
            ListaLavoro l1 = comFac.createListalavoroFromNotification(notificationId, type);
            if (l1 != null)
                return new ValueResponse(l1.getLid());
            return new ValueResponse("");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/summary/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long id) {

        try {

            ListaLavoro listaLavoro = listFac.getListaLavoroById(id);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("listaDescr", listaLavoro.getDescription());
            model.put("listaId", listaLavoro.getLid());

            PartialViewRenderer renderer = new PartialViewRenderer();
            UiDettaglioListaLavoroView dettaglioView = new UiDettaglioListaLavoroView();
            String content = renderer.render(viewResolver, "listelavoro/listaSummary", model, LocaleContextHolder.getLocale(), request);
            dettaglioView.setContent(content);
            dettaglioView.setLavoratoriLista(listaLavoro.getLavoratori());

            return new ValueResponse(dettaglioView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse delete(@PathVariable long id) {

        try {
            listFac.deleteLista(id);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/{id}/deleteworkers", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse deleteWorkers(@PathVariable long id, @RequestBody UiLavoratori lavs) {

        try {
            listFac.deleteWorkers(id, lavs);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/addworker", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse showListaLavoroAddWorker(HttpServletRequest request) throws Exception {

        try {

            Form form = new Form();
            form.setRenderer(applicationContext.getBean(DefaultFormRenderer.class));
            form.setIdentifier("listalavoroaddworker");

            FormDescriptor formDescriptor = new FormDescriptor(form);
            formDescriptor.addField("worker", String.class, "Lavoratore", null, applicationContext.getBean(LavoratoreSingleSearchableFieldRenderer.class)).putParam(Params.ROW, "dt1");

            FormResponse response = new FormResponse();

            response.setContent(form.writeToString());

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/{id}/addworker", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse addWorker(@PathVariable long id, long workerId, HttpServletRequest request) throws Exception {

        try {
            listFac.addWorker(id, workerId);
            return new ValueResponse("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

}
