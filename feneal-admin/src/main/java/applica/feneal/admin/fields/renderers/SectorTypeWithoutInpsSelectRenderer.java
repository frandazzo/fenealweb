package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.Sector;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 16/05/2016.
 */
@Component
public class SectorTypeWithoutInpsSelectRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(Sector.sector_edile);
        i.setLabel("Edile");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue(Sector.sector_IMPIANTIFISSI);
        i1.setLabel("Impianti fissi");
        result.add(i1);



        return result;
    }
}
