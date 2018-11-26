package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.Calendar;


@Component
public class DateToMonthFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/date_month.vm";
    }

    @Override
    public void render(Writer writer, FormField formField, Object value) {

        this.putExtraContextValue("propMonth", "toMonthReport");
        this.putExtraContextValue("propYear", "toYearReport");

        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String currentMonth = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        this.putExtraContextValue("currentYear", currentYear);
        this.putExtraContextValue("currentMonth", currentMonth);

        super.render(writer, formField, value);
    }
}
