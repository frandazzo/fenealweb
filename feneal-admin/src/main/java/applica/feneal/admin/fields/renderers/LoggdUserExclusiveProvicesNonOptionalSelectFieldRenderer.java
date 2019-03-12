package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
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
public class LoggdUserExclusiveProvicesNonOptionalSelectFieldRenderer extends SelectFieldRenderer {


    @Autowired
    private Security security;

    @Autowired
    private ApplicationOptionRepository appOpRep;


    @Override
    public List<SimpleItem> getItems() {

        ApplicationOptions opt = appOpRep.find(null).findFirst().orElse(null);

        if (opt == null){
            return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(),
                    "description", "id");

        }

        //se si tratta di veneto passo solo la provincia di default
        Province prov = ((User) security.getLoggedUser()).getDefaultProvince();

        if (prov == null){
            return SimpleItem.createList(((User) security.getLoggedUser()).getCompany().getProvinces(),
                    "description", "id");
        }


        if (opt.getEnableExclusiveLiberiQuery() != null && opt.getEnableExclusiveLiberiQuery() == true){
            return SimpleItem.createList(((User) security.getLoggedUser()).getCompany()
                            .getProvinces().stream()
                            .filter(a -> a.getId() == prov.getId()).collect(Collectors.toList()),
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
