package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.geo.Region;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegionalCompaniesOptionalSelectfieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private Security sec;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<SimpleItem> getItems() {

        User u = ((User) sec.getLoggedUser());
        int regionId = u.getCompany().getRegionId();

        List<Company> companies = companyRepository.find(LoadRequest.build()).getRows().stream().filter(a -> a.getRegionId() == regionId).collect(Collectors.toList());
        return SimpleItem.createList(companies, (c) -> c.getDescription(), (c) -> String.valueOf(c.getId()));
    }


    @Override
    public void render(Writer writer, FormField field, Object value) {

        if (value == null) {
            Company prov = ((User) sec.getLoggedUser()).getCompany();

            value = (prov == null)? null : prov.getLid();
        }


        super.render(writer, field, value);
    }
}
