package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ristorniEdilizia.ReferentiRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ReferenteForDelegaBariOptionalSelectorRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private ReferentiRepository rep;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<>();
        List<Referenti> ll = rep.find(LoadRequest.build()).getRows();

        Collections.sort(ll, new Comparator<Referenti>() {
            @Override
            public int compare(Referenti o1, Referenti o2) {
                return o1.getCompleteName().compareTo(o2.getCompleteName());
            }
        });


        for (Referenti r : ll) {
            SimpleItem i = new SimpleItem();
            i.setValue(String.valueOf(r.getLid()));
            i.setLabel(r.getCompleteName());
            result.add(i);
        }

        return result;


    }
}
