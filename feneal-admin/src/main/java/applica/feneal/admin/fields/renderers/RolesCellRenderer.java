package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.Role;
import applica.framework.widgets.GridColumn;
import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/14/15
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RolesCellRenderer extends BaseCellRenderer {


    @Override
    public void render(Writer writer, GridColumn column, Object value) {

        List<Role> r = (List<Role>)value;
        String rolename = r.get(0).getRole();

        super.render(writer, column, rolename);
    }
}
