package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.Widget;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 11/04/2016.
 */
@Component
public class WidgetPositionRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue("1");
        i.setLabel("Primo quadrante");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue("2");
        i1.setLabel("Secondo quadrante");
        result.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue("3");
        i2.setLabel("Terzo quadrante");
        result.add(i2);


        return result;
    }
}