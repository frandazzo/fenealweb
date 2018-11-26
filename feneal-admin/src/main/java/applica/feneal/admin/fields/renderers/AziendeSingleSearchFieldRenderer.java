package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by antoniolovicario on 16/04/16.
 */
@Component
public class AziendeSingleSearchFieldRenderer extends SingleSearchableInputFieldRenderer {
    @Override
    public String getLabel(FormField formField, Object o) {
        return o != null? o.toString() : "NA";
    }

    @Override
    public String getServiceUrl() {
        return "values/aziende";
    }
}
