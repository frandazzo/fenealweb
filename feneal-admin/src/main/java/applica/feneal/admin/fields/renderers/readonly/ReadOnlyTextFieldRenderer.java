package applica.feneal.admin.fields.renderers.readonly;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 16/05/2016.
 */
@Component
public class ReadOnlyTextFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "/templates/fields/readOnlyText.vm";
    }
}