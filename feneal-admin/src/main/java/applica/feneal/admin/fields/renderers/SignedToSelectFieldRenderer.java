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
public class SignedToSelectFieldRenderer extends OptionalSelectFieldRenderer {

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> items = new ArrayList<>();

        SimpleItem i1 = new SimpleItem();
        i1.setValue("codici_0");
        i1.setLabel("Codici 0");
        items.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue("filca");
        i2.setLabel("FILCA");
        items.add(i2);

        SimpleItem i3 = new SimpleItem();
        i3.setValue("fillea");
        i3.setLabel("FILLEA");
        items.add(i3);

        return items;
    }
}
