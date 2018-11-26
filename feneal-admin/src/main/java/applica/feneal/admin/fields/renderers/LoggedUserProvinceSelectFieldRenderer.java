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
 * Created by antoniolovicario on 16/04/16.
 * Questa classe ottiene le province collegate all'utente loggato
 */
@Component
public class LoggedUserProvinceSelectFieldRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    private Security security;

    @Override
    public List<SimpleItem> getItems() {
        return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(), "description", "id");
    }

    @Override
    public void render(Writer writer, FormField field, Object value) {

        if (value == null) {
            Province prov = ((User) security.getLoggedUser()).getDefaultProvince();

            value = (prov == null)? null : prov.getIid();
        }

        super.render(writer, field, value);
    }
}
