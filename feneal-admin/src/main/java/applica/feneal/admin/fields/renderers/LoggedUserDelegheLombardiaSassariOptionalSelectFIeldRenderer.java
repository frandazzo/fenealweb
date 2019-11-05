package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.Province;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoggedUserDelegheLombardiaSassariOptionalSelectFIeldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private Security security;

    @Autowired
    private ProvinceRepository geo;

    @Override
    public List<SimpleItem> getItems() {

        User u = ((User) security.getLoggedUser());
        int regionId = u.getCompany().getRegionId();
        Role r = u.retrieveUserRole();

        List<Province> provinces = geo.find(LoadRequest.build().filter("idRegion", regionId)).getRows();

        return SimpleItem.createList(provinces,"description", "id");

    }
}
