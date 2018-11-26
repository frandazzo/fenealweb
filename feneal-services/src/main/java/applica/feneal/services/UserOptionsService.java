package applica.feneal.services;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.setting.option.UserOptions;

/**
 * Created by fgran on 11/04/2016.
 */
public interface UserOptionsService {

    UserOptions getUserOptionOrCreateIt(User loggedUser);

    void addWidget(User user, String widgetName, String context,  String params);


    void removeWidget(User loggedUser, String widgetName, String context);


    void setLayout(User loggedUser, String context, String layoutValue);

    void setParams(User loggedUser, String name, String context, String value);
}
