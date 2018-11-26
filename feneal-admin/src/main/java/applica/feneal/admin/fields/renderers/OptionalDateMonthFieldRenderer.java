package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;


@Component
public class OptionalDateMonthFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/date_month_quote.vm";
    }

    @Override
    public void render(Writer writer, FormField formField, Object value) {

        super.render(writer, formField, value);
    }
}
