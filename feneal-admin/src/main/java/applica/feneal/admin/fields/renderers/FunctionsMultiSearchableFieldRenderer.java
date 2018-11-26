package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.VisibleFunction;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.MultiSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 25/09/14faa
 * Time: 16:45
 */
@Component
public class FunctionsMultiSearchableFieldRenderer extends MultiSearchableInputFieldRenderer {

    @Override
    public String getServiceUrl() {
        return "values/visiblefunctions";
    }

    @Override
    public List<SimpleItem> getSelectedItems(FormField formField, Object o) {
        return SimpleItem.createList(Optional.ofNullable((List<VisibleFunction>) o).orElse(new ArrayList<VisibleFunction>()), v -> v.getDescription(), v -> String.valueOf(v.getId()));
    }
}
