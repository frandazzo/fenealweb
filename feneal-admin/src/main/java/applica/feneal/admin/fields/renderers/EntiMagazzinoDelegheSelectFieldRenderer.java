package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntiMagazzinoDelegheSelectFieldRenderer extends SelectFieldRenderer {

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i2 = new SimpleItem();
        i2.setValue("ENTRAMBI");
        i2.setLabel("ENTRAMBI");
        result.add(i2);

        SimpleItem i = new SimpleItem();
        i.setValue("CASSA EDILE");
        i.setLabel("CASSA EDILE");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue("EDILCASSA");
        i1.setLabel("EDILCASSA");
        result.add(i1);




        return result;
    }



}

