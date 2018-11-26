package applica.feneal.admin.mapping;

import applica.framework.Entity;
import applica.framework.widgets.FormDescriptor;
import applica.framework.widgets.FormField;
import applica.framework.widgets.mapping.MappingException;
import applica.framework.widgets.mapping.SimplePropertyMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/14/15
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RolesToSinglePropertyMapper extends SimplePropertyMapper {

    @Override
    public void toFormValue(FormDescriptor formDescriptor, FormField formField, Map<String, Object> values, Entity entity) throws MappingException {
        super.toFormValue(formDescriptor, formField, values, entity);
    }

    @Override
    public void toEntityProperty(FormDescriptor formDescriptor, FormField formField, Entity entity, Map<String, String[]> requestValues) throws MappingException {
        if (formField.getProperty().equals("roles")){
            String[] result = requestValues.get("roles");
            if (result.length == 1){
                if (result[0].equals(""))
                    requestValues.get("roles")[0] = null;
            }

        }
        super.toEntityProperty(formDescriptor, formField, entity, requestValues);
    }
}
