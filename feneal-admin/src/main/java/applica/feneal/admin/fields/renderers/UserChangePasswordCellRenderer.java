package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: angelo
 * Date: 21/11/17
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserChangePasswordCellRenderer extends BaseCellRenderer {

    @Override
    public String getTemplatePath() {
        return "templates/cells/user_change_password.vm";
    }
}
