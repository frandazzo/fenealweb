package applica.feneal.admin.fields.renderers.RSU;

import applica.feneal.domain.data.core.RSU.SedeRSURepository;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by felicegramegna on 01/03/2021.
 */

@Component
public class OptionalSedeRsuFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    SedeRSURepository sedeRSURepository;

    @Override
    public List<SimpleItem> getItems() {
        List<SimpleItem> result = new ArrayList<>();
        List<SedeRSU> ll = new ArrayList<SedeRSU>();

        Collections.sort(ll, new Comparator<SedeRSU>() {
            @Override
            public int compare(SedeRSU o1, SedeRSU o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });

        for (SedeRSU sede : ll) {
            SimpleItem i = new SimpleItem();
            i.setValue(String.valueOf(sede.getIid()));
            i.setLabel(sede.getDescription());
            result.add(i);
        }

        return result;

    }
}
