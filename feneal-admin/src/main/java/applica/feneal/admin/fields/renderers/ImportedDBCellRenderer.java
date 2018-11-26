package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.framework.widgets.Grid;
import applica.framework.widgets.GridColumn;
import applica.framework.widgets.cells.renderers.BaseCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Writer;

/**
 * Applica
 * User: Angelo
 * Date: 11/05/16
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ImportedDBCellRenderer extends BaseCellRenderer {

    @Override
    public void render(Writer writer, GridColumn column, Object value) {
        super.render(writer, column, value);
    }

    @Override
    protected String createTemplatePath(Grid grid, GridColumn column) {
        return "templates/cells/imported_DB.vm";
    }
}
