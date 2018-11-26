package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;

/**
 * Created by angelo on 04/06/16.
 * Questa classe ottiene le province del territorio in questione
 */
@Component
public class UserDefaultProvinceSelectFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private GeoService geoSvc;

    @Autowired
    private Security security;

    @Override
    public List<SimpleItem> getItems() {
        return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(), "description", "id");
    }

    @Override
    public void render(Writer writer, FormField field, Object value) {

        Integer provId = null;
        if (value != null) {

            provId = ((Province)value).getIid();
        }

        super.render(writer, field, provId);
    }
}
