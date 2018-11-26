package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.Calendar;
import java.util.Date;


@Component
public class CurrentDateFieldRenderer extends TemplateFormFieldRenderer {

    protected String createTemplatePath(Form form, FormField formField) {
        return "/templates/fields/date.vm";
    }

    protected void setupContext(VelocityContext context) {
        super.setupContext(context);
        context.put("defaultDate", new Date());
    }

    @Override
    public void render(Writer writer, FormField formField, Object value) {

        Date date = (value == null) ? new Date(): (Date)value;

        super.render(writer, formField, date);
    }
}
