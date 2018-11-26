package applica.feneal.services.impl.setup.options;

import applica.feneal.domain.data.core.UserOptionsRepository;
import applica.feneal.domain.data.core.WidgetRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.setting.option.UserOptions;
import applica.feneal.services.UserOptionsService;
import applica.feneal.services.WidgetService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fgran on 11/04/2016.
 */
@Service
public class UserOptionsServiceImpl implements UserOptionsService {



    @Autowired
    private WidgetRepository widgetRep;

    @Autowired
    private UserOptionsRepository optRep;



    public UserOptions getUserOptionOrCreateIt(User loggedUser){

        LoadRequest req = LoadRequest.build().filter("user", loggedUser.getLid());
        UserOptions opt = optRep.find(req).findFirst().orElse(null);


        if (opt == null){
            opt = new UserOptions();
            //imposto alcuni parametri di default
            opt.setDashboardLayout(UserOptions.layout_vertical);
            opt.setFirmLayout(UserOptions.layout_vertical);
            opt.setWorkerLayout(UserOptions.layout_vertical);

            opt.setUser(loggedUser);
            optRep.save(opt);
        }

        return opt;


    }

    private Widget findWidgetByName(String name, String context){

        LoadRequest req = LoadRequest.build().filter("context", context).filter("description", name);
        return widgetRep.find(req).findFirst().orElse(null);

    }

    @Override
    public void addWidget(User user, String widgetName,  String context, String params) {
        Widget w = findWidgetByName(widgetName, context.toLowerCase());
        if (w == null)
            return;

        //recupero le opzioni per l'utente loggato
        UserOptions opt = getUserOptionOrCreateIt(user);
        opt.addOrUpdateWidgetOption(context.toLowerCase(), w,params);
        optRep.save(opt);

    }



    @Override
    public void removeWidget(User loggedUser, String widgetName, String context) {
        Widget w = findWidgetByName(widgetName, context);
        if (w == null)
            return;
        UserOptions opt = getUserOptionOrCreateIt(loggedUser);
        opt.removeWiget( w,context.toLowerCase());
        optRep.save(opt);

    }

    @Override
    public void setLayout(User loggedUser, String context, String layoutValue) {
        UserOptions opt = getUserOptionOrCreateIt(loggedUser);
        if (context.toLowerCase().equals(Widget.context_dashboard)){
            opt.setDashboardLayout(layoutValue);
        }else if (context.toLowerCase().equals(Widget.context_lavoratore)){
            opt.setWorkerLayout(layoutValue);
        }else if (context.toLowerCase().equals(Widget.context_azienda)){
            opt.setFirmLayout(layoutValue);
        }
        optRep.save(opt);

    }

    @Override
    public void setParams(User loggedUser, String name, String context, String value) {
        Widget w = findWidgetByName(name, context.toLowerCase());
        if (w == null)
            return;

        UserOptions opt = getUserOptionOrCreateIt(loggedUser);
        opt.addOrUpdateWidgetOption(context.toLowerCase(), w,value);
        optRep.save(opt);
    }


}
