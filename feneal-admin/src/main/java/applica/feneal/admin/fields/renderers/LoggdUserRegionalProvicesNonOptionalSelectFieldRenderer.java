package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
import applica.feneal.services.GeoService;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoggdUserRegionalProvicesNonOptionalSelectFieldRenderer extends SelectFieldRenderer {


    @Autowired
    private Security security;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProvinceRepository geo;


    @Override
    public List<SimpleItem> getItems() {

        User u = ((User) security.getLoggedUser());
        int regionId = u.getCompany().getRegionId();
        Role r = u.retrieveUserRole();

        List<Province> provinces = geo.find(LoadRequest.build().filter("idRegion", regionId)).getRows();



        //se sono segretario prendo tutte le provicne
        if (r.getLid() == 3){
            return SimpleItem.createList(provinces,
                    "description", "id");

        }

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
