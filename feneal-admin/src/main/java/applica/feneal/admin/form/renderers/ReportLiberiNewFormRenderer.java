package applica.feneal.admin.form.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.forms.renderers.BaseFormRenderer;
import org.springframework.stereotype.Component;

@Component
public class ReportLiberiNewFormRenderer extends BaseFormRenderer {



    protected String createTemplatePath(Form form) {
        String templatePath = "/templates/forms/reportLiberiNewSearchForm.vm";
        return templatePath;
    }
}
