package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.services.GeoService;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 11/05/2016.
 */
@Component
public class LoggedUserProvinceNonOptionalSelectFieldRenderer extends SelectFieldRenderer {


    @Autowired
    private Security security;


    @Override
    public List<SimpleItem> getItems() {



        return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(),
                "description", "id");


    }

    @Override
    public void render(Writer writer, FormField field, Object value) {

        if (value == null) {
            Province prov = ((User) security.getLoggedUser()).getDefaultProvince();

            value = (prov == null)? null : prov.getIid();
        } else {
            Province prov = (Province) value;
            value = prov.getIid();
        }


        super.render(writer, field, value);
    }
}
