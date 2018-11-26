package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.VersamentiFacade;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuotaView;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by angelo on 05/05/2016.
 */
@Controller
public class VersamentiController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private VersamentiFacade versamentiFacade;

    @RequestMapping(value = "/versamenti/{workerId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse view(HttpServletRequest request, @PathVariable long workerId) {

        try {

            List<UiDettaglioQuota> quoteDetails = versamentiFacade.getStoricoVersamenti(workerId);

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

}
