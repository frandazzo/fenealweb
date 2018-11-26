package applica.feneal.admin.fields.renderers;

import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.Calendar;


@Component
public class OptionalDateFromMonthFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/date_month.vm";
    }

    @Override
    public void render(Writer writer, FormField formField, Object value) {

        this.putExtraContextValue("propMonth", "fromMonthReport");
        this.putExtraContextValue("propYear", "fromYearReport");

        this.putExtraContextValue("optionalDate", true);

        super.render(writer, formField, value);
    }
}
