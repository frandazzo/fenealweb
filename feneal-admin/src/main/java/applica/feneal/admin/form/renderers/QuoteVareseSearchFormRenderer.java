package applica.feneal.admin.form.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.forms.renderers.BaseFormRenderer;
import org.springframework.stereotype.Component;

@Component
public class QuoteVareseSearchFormRenderer extends BaseFormRenderer {


    protected String createTemplatePath(Form form) {
        String templatePath = "/templates/forms/reportQuoteVareseForm.vm";
        return templatePath;
    }
}
