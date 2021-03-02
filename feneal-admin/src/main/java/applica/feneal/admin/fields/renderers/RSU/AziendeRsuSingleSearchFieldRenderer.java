package applica.feneal.admin.fields.renderers.RSU;

import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by felicegramegna on 26/02/2021.
 */

@Component
public class AziendeRsuSingleSearchFieldRenderer  extends SingleSearchableInputFieldRenderer {
    @Override
    public String getLabel(FormField formField, Object o) {
        return o != null? o.toString() : "NA";
    }

    @Override
    public String getServiceUrl() {
        return "values/aziendersu";
    }
}
