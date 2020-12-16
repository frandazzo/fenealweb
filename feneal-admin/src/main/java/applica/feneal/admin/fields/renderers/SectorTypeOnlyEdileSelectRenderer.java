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
public class SectorTypeOnlyEdileSelectRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(Sector.sector_edile);
        i.setLabel("Edile");
        result.add(i);

        return result;
    }
}
