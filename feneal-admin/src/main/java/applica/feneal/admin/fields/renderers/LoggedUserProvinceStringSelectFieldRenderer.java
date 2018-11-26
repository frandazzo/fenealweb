package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.Province;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;

/**
 * Created by angelo on 28/05/16.
 *
 */
@Component
public class LoggedUserProvinceStringSelectFieldRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    private Security security;

    @Override
    public List<SimpleItem> getItems() {
        return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(), "description", "description");
    }

    @Override
    public void render(Writer writer, FormField field, Object value) {

        if (value == null) {
            Province prov = ((User) security.getLoggedUser()).getDefaultProvince();

            value = (prov == null)? null : prov.getDescription();
        }

        super.render(writer, field, value);
    }
}
