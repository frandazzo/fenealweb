package applica.feneal.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


@Component
public class CustomCheckboxFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/custom_checkbox.vm";
    }

}
