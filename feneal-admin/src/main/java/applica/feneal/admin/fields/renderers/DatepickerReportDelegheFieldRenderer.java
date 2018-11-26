package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.Calendar;
import java.util.Date;


@Component
public class DatepickerReportDelegheFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "/templates/forms/report_deleghe_date.vm";
    }

    @Override
    protected void setupContext(VelocityContext context) {

        super.setupContext(context);

        context.put("defaultDate", new Date());
    }
}
