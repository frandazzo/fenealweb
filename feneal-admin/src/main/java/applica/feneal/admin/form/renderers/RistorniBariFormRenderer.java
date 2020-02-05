package applica.feneal.admin.form.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.forms.renderers.BaseFormRenderer;
import org.springframework.stereotype.Component;

@Component
public class RistorniBariFormRenderer extends BaseFormRenderer {



    protected String createTemplatePath(Form form) {
        String templatePath = "/templates/forms/ristorniBariSearchForm.vm";
        return templatePath;
    }
}
