package applica.feneal.admin.controllers.realwidgets;

import applica.feneal.admin.facade.WidgetFacade;
import applica.feneal.admin.viewmodel.options.UiEnableWidget;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.admin.viewmodel.widget.Circulars;
import applica.feneal.admin.viewmodel.widget.RowInteger;
import applica.feneal.domain.model.core.Paritethic;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.feneal.domain.model.User;
import applica.feneal.services.StatisticService;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by fgran on 17/05/2016.
 */
@Controller
@RequestMapping("widget/real/sindacalizzazione")
public class SindacalizzazioneController {


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
        String url = "widget/real/sindacalizzazione/";

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
        List<String>provinces =  statServ.getListaTerritori(((User) sec.getLoggedUser()).getLid());
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

        return "widget/real/sindacalizzazione";

    }


    @RequestMapping("/getData")
    public @ResponseBody
    SimpleResponse getData() {

        return new ValueResponse(widgetFacade.getSindacalizzazioneData());

    }

    @RequestMapping("/getAppData")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse getData1(@RequestParam(required=false) String province,
                            @RequestParam(required=false) String ente) {

        return new ValueResponse(widgetFacade.getSindacalizzazioneData(province, ente));

    }


}
