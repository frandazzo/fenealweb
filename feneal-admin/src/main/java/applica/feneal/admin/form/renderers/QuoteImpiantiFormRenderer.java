package applica.feneal.admin.form.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.forms.renderers.BaseFormRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by angelo on 14/04/2016.
 */
@Component
public class QuoteImpiantiFormRenderer extends BaseFormRenderer {


    protected String createTemplatePath(Form form) {
        String templatePath = "/templates/forms/quoteImpiantiForm.vm";
        return templatePath;
    }

}