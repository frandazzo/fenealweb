package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.Calendar;


@Component
public class DateFromYearFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/date_year.vm";
    }

    @Override
    public void render(Writer writer, FormField formField, Object value) {

        this.putExtraContextValue("propYear", "fromYearReport");
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        this.putExtraContextValue("currentYear", currentYear);

        super.render(writer, formField, value);
    }
}
