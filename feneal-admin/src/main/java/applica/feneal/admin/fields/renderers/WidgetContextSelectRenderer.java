package applica.feneal.admin.fields.renderers;

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
public class WidgetContextSelectRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(Widget.context_dashboard);
        i.setLabel("Dashboard");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue(Widget.context_lavoratore);
        i1.setLabel("Lavoratore");
        result.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue(Widget.context_azienda);
        i2.setLabel("Azienda");
        result.add(i2);



        return result;
    }
}