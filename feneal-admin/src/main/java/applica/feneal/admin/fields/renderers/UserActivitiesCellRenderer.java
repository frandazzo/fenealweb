package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: angelo
 * Date: 22/11/17
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserActivitiesCellRenderer extends BaseCellRenderer {

    @Override
    public String getTemplatePath() {
        return "templates/cells/user_activities.vm";
    }
}
