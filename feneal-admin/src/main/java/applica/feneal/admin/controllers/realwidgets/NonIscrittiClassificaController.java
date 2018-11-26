package applica.feneal.admin.controllers.realwidgets;

import applica.feneal.admin.facade.WidgetFacade;
import applica.feneal.admin.viewmodel.options.UiEnableWidget;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.services.StatisticService;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;

/**
 * Created by fgran on 17/06/2016.
 */
@Controller
@RequestMapping("widget/real/noniscritticlassifica")
public class NonIscrittiClassificaController {
    @Autowired
    private WidgetFacade widgetFacade;

    @Autowired
    private StatisticService statServ;

    @Autowired
    private Security sec;

    @RequestMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String circulars(Model model) throws Exception {

        //recupero il widget in base al suo url
        String url = "widget/real/noniscritticlassifica/";

        //trovandomi nel metodo che costruisce il widget conosco sicuramente
        //il contesto nel quale il widget opererà
        UiWidgetManager w = widgetFacade.getEnabledWidgets("dashboard");
        UiEnableWidget widget = w.getWidgetByUrl(url);
        if (widget != null){
            model.addAttribute("widgetName", widget.getWidgetName());
            model.addAttribute("widgetParams", widget.getParams());
        }
        else
            throw new Exception("Nessun widget trovato in Widget controller....");


        //davide in ogni controller devi anche inserire cosi comè questo codice per valorizzare la lista
        //dei territori
        List<String> provinces =  statServ.getListaTerritori(((User) sec.getLoggedUser()).getLid());
        model.addAttribute("provinces", provinces);
        model.addAttribute("enti", statServ.getListaEnti());
        //imposto tutti gli eventuali valori di default se non ci sono paramtri
        model.addAttribute("provincia", provinces.get(0));
        model.addAttribute("ente", Paritethic.ente_cassaedile);
        model.addAttribute("anno", Calendar.getInstance().get(Calendar.YEAR));




        //*********************************************************
        //*********************************************************
        //*********************************************************
        //*********************************************************

        return "widget/real/noniscritticlassifica";

    }


    @RequestMapping("/getData")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse getData() {

        return new ValueResponse(widgetFacade.getClassificaNonIscritti());

    }


}

