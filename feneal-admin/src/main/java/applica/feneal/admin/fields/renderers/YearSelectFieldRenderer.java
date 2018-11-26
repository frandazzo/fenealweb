package applica.feneal.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 17/04/16.
 */
@Component
public class YearSelectFieldRenderer extends OptionalSelectFieldRenderer {

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> years = new ArrayList<>();

        for (int i = 2050; i >= 1990; i--) {
            SimpleItem simpleItem = new SimpleItem(String.valueOf(i), String.valueOf(i));
            years.add(simpleItem);
        }

        return years;
    }
}
