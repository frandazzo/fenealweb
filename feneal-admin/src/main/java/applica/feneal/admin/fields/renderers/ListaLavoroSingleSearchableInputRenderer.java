package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Applica (www.applicadoit.com)
 * User: angelo
 * Date: 12/6/16
 * Time: 15:25 PM
 */
@Component
public class ListaLavoroSingleSearchableInputRenderer extends SingleSearchableInputFieldRenderer {

    @Override
    public String getLabel(FormField field, Object value) {
        if (value != null)
            return String.valueOf(value);

        return "";
    }

    @Override
    public String getServiceUrl() {
        return "values/listelavoro";
    }

}
