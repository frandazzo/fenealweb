package applica.feneal.admin.form.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.forms.renderers.BaseFormRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by applica on 11/6/15.
 */
@Component
public class MulticolumnFormRenderer extends BaseFormRenderer {



    protected String createTemplatePath(Form form) {
        String templatePath = "/templates/forms/multi_column_wizard_form.vm";
        return templatePath;
    }

}
