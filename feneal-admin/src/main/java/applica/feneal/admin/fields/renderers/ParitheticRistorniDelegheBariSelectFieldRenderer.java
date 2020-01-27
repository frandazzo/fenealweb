package applica.feneal.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParitheticRistorniDelegheBariSelectFieldRenderer extends SelectFieldRenderer {

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> res = new ArrayList<>();

        SimpleItem simpleItem = new SimpleItem("CASSA EDILE","CASSA EDILE");


        res.add(simpleItem);
        SimpleItem simpleItem2 = new SimpleItem("EDILCASSA","EDILCASSA");


        res.add(simpleItem2);

        return res;
    }
}
