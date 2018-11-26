package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.Widget;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class WidgetTypeSelectRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(Widget.widget_locale);
        i.setLabel("Locale");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue(Widget.widget_regionale);
        i1.setLabel("Regionale");
        result.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue(Widget.widget_nazionale);
        i2.setLabel("Nazionale");
        result.add(i2);

        SimpleItem i3 = new SimpleItem();
        i3.setValue(Widget.widget_all);
        i3.setLabel("Tutti");
        result.add(i3);


        return result;
    }
}