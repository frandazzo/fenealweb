package applica.feneal.admin.controllers.ristorni;



import applica.feneal.admin.facade.StoricoRistorniBariFacade;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuotaView;
import applica.feneal.admin.viewmodel.ristornibari.UiDettaglioRistornoBari;
import applica.feneal.admin.viewmodel.ristornibari.UiStoricoRistorniBariView;
import applica.feneal.domain.data.core.ristorniEdilizia.RistornoRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.Ristorno;
import applica.feneal.domain.model.core.ristorniEdilizia.RistornoBariObject;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Controller
public class StoricoRistorniBariController {

    @Autowired
    private StoricoRistorniBariFacade risFac;

    @Autowired
    private RistornoRepository risRep;

    @Autowired
    private ViewResolver viewResolver;

    @RequestMapping(value = "/storicoristorni",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewRistorniBari(HttpServletRequest request) {

        try {

            List<Ristorno> ristorniBari = risFac.getRistorniBari();

            HashMap<String, Object> model = new HashMap<String, Object>();

            PartialViewRenderer renderer = new PartialViewRenderer();
            UiStoricoRistorniBariView ristorniBariView = new UiStoricoRistorniBariView();
            String content = renderer.render(viewResolver, "storicoristorni/storicoRistorniBari", model, LocaleContextHolder.getLocale(), request);
            ristorniBariView.setContent(content);
            ristorniBariView.setListaRistorni(ristorniBari);

            return new ValueResponse(ristorniBariView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/storicoristorni/dettaglio/{id}",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long id) {

        try {

            Ristorno r = risRep.find(LoadRequest.build().filter("id",id, Filter.EQ)).findFirst().orElse(null);

            RistornoBariObject ristornoDetails = risFac.getDettaglioRistorno(id);

            HashMap<String, Object> model = new HashMap<String, Object>();

            SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
            model.put("ristorno",r);
            model.put("data",ff.format(r.getDataRistorno()));
            PartialViewRenderer renderer = new PartialViewRenderer();
            UiDettaglioRistornoBari dettaglioView = new UiDettaglioRistornoBari();
            String content = renderer.render(viewResolver, "storicoristorni/ristornoDettaglio", model, LocaleContextHolder.getLocale(), request);
            dettaglioView.setContent(content);
            dettaglioView.setRistornoBariObject(ristornoDetails);

            return new ValueResponse(dettaglioView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

}
