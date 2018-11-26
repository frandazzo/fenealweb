package applica.feneal.services;

import applica.feneal.domain.model.core.Widget;

import java.util.List;

/**
 * Created by fgran on 11/04/2016.
 */
public interface WidgetService {

    List<Widget> getWidgetByRoleId(long loggedUserRoleId, String context);


}
