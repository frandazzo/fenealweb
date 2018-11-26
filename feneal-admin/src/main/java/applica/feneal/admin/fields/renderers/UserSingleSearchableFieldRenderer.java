package applica.feneal.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.MultiSearchableInputFieldRenderer;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 25/09/14
 * Time: 16:45
 */
@Component
public class UserSingleSearchableFieldRenderer extends SingleSearchableInputFieldRenderer {

    @Override
    public String getLabel(FormField formField, Object o) {
        return null;
    }

    @Override
    public String getServiceUrl() {
        return "values/users";
    }

}
