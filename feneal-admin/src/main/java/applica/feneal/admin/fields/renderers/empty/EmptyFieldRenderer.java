package applica.feneal.admin.fields.renderers.empty;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 17/05/2016.
 */
@Component
public class EmptyFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "/templates/fields/empty.vm";
    }
}
