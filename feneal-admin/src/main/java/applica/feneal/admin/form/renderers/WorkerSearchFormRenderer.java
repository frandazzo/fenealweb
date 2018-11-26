package applica.feneal.admin.form.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.forms.renderers.BaseFormRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 07/04/2016.
 */
@Component
public class WorkerSearchFormRenderer extends BaseFormRenderer {



    protected String createTemplatePath(Form form) {
        String templatePath = "/templates/forms/workerSearchForm.vm";
        return templatePath;
    }

}

