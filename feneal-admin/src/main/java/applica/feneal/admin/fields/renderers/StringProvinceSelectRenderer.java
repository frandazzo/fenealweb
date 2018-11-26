package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.geo.Province;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class StringProvinceSelectRenderer extends SelectFieldRenderer {

    @Autowired
    private ProvinceRepository provincesRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Province> provinces = provincesRepository.find(LoadRequest.build().sort("description", false)).getRows();
        return SimpleItem.createList(provinces, (s) -> s.getDescription(), (s) -> s.getDescription());
    }

}