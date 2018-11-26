package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.framework.Entity;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class OptionalCompanySelectRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    private Security security;

    @Autowired
    CompanyRepository rep;

    @Override
    public List<SimpleItem> getItems() {
        Role r = ((Role) security.getLoggedUser().getRoles().get(0));
        if (r.getLid() == 2){


            Company c =((User) security.getLoggedUser()).getCompany();
            if (c == null)
                return new ArrayList<SimpleItem>();


            List<SimpleItem> result = new ArrayList<>();
            Company ll = rep.get(((User) security.getLoggedUser()).getCompany().getLid()).orElse(null);

            if (ll == null)
                return new ArrayList<SimpleItem>();



            SimpleItem i = new SimpleItem();
            i.setLabel(ll.getDescription());
            i.setValue(ll.getSid());
            result.add(i);

            return result;
        }else{
            List<SimpleItem> result = new ArrayList<>();
            List<Company> ll = rep.find(LoadRequest.build()).getRows();

            for (Company user : ll) {
                SimpleItem i = new SimpleItem();
                i.setValue(String.valueOf(user.getLid()));
                i.setLabel(user.getDescription());
                result.add(i);
            }

            return result;
        }






    }

    @Override
    public void render(Writer writer, FormField field, Object value) {
        Object id = null;

        try{
            if (value != null) {
                id =((Entity)value).getId();
            }
        }catch (Exception ex){
            //anche qui l'eccezione è stata gestita nel contesto della ricerca con il search form che dopo la ricerca invia un value sballato
            ex.printStackTrace();
        }


        super.render(writer, field, id);
    }
}