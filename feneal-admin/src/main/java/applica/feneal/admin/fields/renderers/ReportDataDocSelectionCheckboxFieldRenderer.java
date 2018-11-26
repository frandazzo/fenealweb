package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;


@Component
public class ReportDataDocSelectionCheckboxFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/data_doc_selection_checkbox.vm";
    }

}
