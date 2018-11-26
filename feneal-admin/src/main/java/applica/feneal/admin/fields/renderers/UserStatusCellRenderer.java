package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Grid;
import applica.framework.widgets.GridColumn;
import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;

/**
 * Applica
 * User: angelo
 * Date: 20/11/17
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserStatusCellRenderer extends BaseCellRenderer {

    @Override
    public String getTemplatePath() {
        return "templates/cells/user_status.vm";
    }
}
