package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 09/04/16.
 */

@Component
public class SexSelectFieldRenderer extends SelectFieldRenderer {

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(Lavoratore.MALE);
        i.setLabel("MASCHIO");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue(Lavoratore.FEMALE);
        i1.setLabel("FEMMINA");
        result.add(i1);

        return result;
    }



}

