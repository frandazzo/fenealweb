package applica.feneal.admin.controllers.base;

import applica.feneal.admin.facade.NotificationsFacade;
import applica.feneal.admin.facade.UsersFacade;
import applica.feneal.admin.facade.WidgetFacade;
import applica.feneal.admin.viewmodel.UiNotificationData;
import applica.feneal.admin.viewmodel.options.UiWidgetManager;
import applica.feneal.domain.data.dbnazionale.ImportazioniRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 2/22/13
 * Time: 3:18 PM
 */
@Controller
public class HomeController {

    @Autowired
    private Security sec;

    @Autowired
    private WidgetFacade widgetFacade;

    @Autowired
    private NotificationsFacade notFac;

    @Autowired
    private UsersFacade usersFacade;



    @RequestMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String index(Model model) {

        //imposto il ruolo dell'utente per deciderne le funzioni di menu
        User u = ((User) sec.getLoggedUser());
        model.addAttribute("roleid", u.retrieveUserRole().getLid());
        model.addAttribute("user", ((User) sec.getLoggedUser()).getCompleteName());
        model.addAttribute("rolename", u.retrieveUserRole().getRole());
        model.addAttribute("userInitials", String.format("%s%s",((User) sec.getLoggedUser()).getName().charAt(0), ((User) sec.getLoggedUser()).getSurname().charAt(0)));


        User uu = ((User) sec.getLoggedUser());
        if (uu.getCompany() != null){
            model.addAttribute("provinces", uu.getCompany().getProvinces()
                    .stream().map(p -> p.getDescription().toLowerCase()).collect(Collectors.joining(",")));

            model.addAttribute("regionid",String.valueOf(uu.getCompany().getRegionId()));

        }
        else{
            model.addAttribute("provinces", "");
            model.addAttribute("regionid","");
        }


        //invio alla vista principale la lista delle notifiche da mostrare
        try{
            //se sono super amministratore non ho la company
            UiNotificationData d = notFac.getNotificationData();
            model.addAttribute("notifications", d);
        }catch (Exception e){

        }


        //invio al client l'oggetto con le specifiche circa la disposizione dei widjets e del layout

        UiWidgetManager m = widgetFacade.getEnabledWidgets("dashboard");
        model.addAttribute("widgets", m);


       // List<Importazione> ii = impRep.find(null).getRows();




        return "index";
    }

    @RequestMapping(value = "/downloadconnector", method = RequestMethod.GET)
    public void downloadConnector(HttpServletResponse response) {
        try {
            usersFacade.downloadConnectorSoftware(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    @RequestMapping(value = "/downloadtestermail", method = RequestMethod.GET)
    public void downloadConnectorMail(HttpServletResponse response) {
        try {
            usersFacade.downloadTesterMailSoftware(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

}
