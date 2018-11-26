package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.model.core.Sector;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by angelo on 17/04/16.
 */
@Component
public class SectorSelectFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private SectorRepository sectorRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Sector> sectors = sectorRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(sectors, (s) -> s.getType(), (s) -> String.valueOf(s.getId()));
    }

}
