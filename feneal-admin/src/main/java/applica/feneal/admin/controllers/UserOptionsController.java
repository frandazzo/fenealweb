package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.WidgetFacade;
import applica.feneal.admin.viewmodel.options.UiEnableWidget;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.services.UserOptionsService;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.GET;
import java.util.List;

/**
 * Created by fgran on 11/04/2016.
 */
@Controller
public class UserOptionsController {


    @Autowired
    private WidgetFacade widgetFacade;

    @Autowired
    private Security sec;

    @Autowired
    private UserOptionsService optSvc;

    @RequestMapping(value="/options/dashboard", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String getDashboardUserOptionsView(Model model){

        //devo recuperare tutte la lista di tutti widgets della dashborad con l'informazione se
        //sono abilitati per l'utente corrente
        UiWidgetManager w =   widgetFacade.getEnabledWidgets(Widget.context_dashboard);
        model.addAttribute("widgets", w);

        return "options/dashboardOptions";
    }

    @RequestMapping(value="/options/worker", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String getWorkerUserOptionsView(Model model){

        //devo recuperare tutte la lista di tutti widgets della dashborad con l'informazione se
        //sono abilitati per l'utente corrente
        UiWidgetManager w =   widgetFacade.getEnabledWidgets(Widget.context_lavoratore);
        model.addAttribute("widgets", w);

        return "options/workerOptions";
    }

    @RequestMapping(value="/options/firm", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String getFirmUserOptionsView(Model model){

        //devo recuperare tutte la lista di tutti widgets della dashborad con l'informazione se
        //sono abilitati per l'utente corrente
        UiWidgetManager w =   widgetFacade.getEnabledWidgets(Widget.context_azienda);
        model.addAttribute("widgets", w);

        return "options/firmOptions";
    }

    @RequestMapping(value="/options/showwidget", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse chooseWidgetOnManager(String name, Boolean toActive, String context){

        if (toActive)
            optSvc.addWidget(((User) sec.getLoggedUser()),name, context, "");
        else
            optSvc.removeWidget(((User) sec.getLoggedUser()), name,context);

        return new ValueResponse("ok");

    }

    @RequestMapping(value="/options/selectlayout", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse chooseStyleWidgetOnManager(String value, String context){

        optSvc.setLayout(((User) sec.getLoggedUser()), context, value);

        return new ValueResponse("ok");

    }

    @RequestMapping(value="/options/setparams", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse setParams(String name, String value, String context){

        optSvc.setParams(((User) sec.getLoggedUser()),name, context, value);

        return new ValueResponse("ok");

    }


}
