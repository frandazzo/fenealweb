package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.Role;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 2/26/13
 * Time: 6:09 PM
 */
@Component
public class AdminRolesFieldRenderer extends SingleSearchableInputFieldRenderer {




    private String label;


    @Override
    public String getLabel(FormField field, Object value) {

      

        return label;


    }

    @Override
    public void render(Writer writer, FormField field, Object value) {

        List<Role> roles;

        try{
         roles =  ((List<Role>) value);
        }catch(Exception e){
            roles = new ArrayList<>();
        }


        if (roles == null){
            label = "Select";
            super.render(writer, field, null);
            return;
        }

        if (roles.size() != 1){
            label = "Select";
            super.render(writer, field, null);
            return;
        }

        Role l = roles.get(0);
        label = l.getRole();
        super.render(writer, field, l.getId());
    }

    @Override
    public String getServiceUrl() {
        return "values/rolesforadmin";
    }

//    @Override
//    public List<SimpleItem> getSelectedItems(FormField field, Object value) {
//        List<Role> roles = (List<Role>) value;
//        if(roles == null) {
//            roles = new ArrayList<>();
//        }
//
//        return SimpleItem.createList(roles, "role", "id");
//    }
}
