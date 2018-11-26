package applica.feneal.admin.fields.renderers;

import applica.framework.library.SimpleItem;
import applica.framework.widgets.Form;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.TemplateFormFieldRenderer;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


@Component
public class TypeQuoteCashFieldRenderer extends TemplateFormFieldRenderer {

    @Override
    protected String createTemplatePath(Form form, FormField formField) {
        return "templates/forms/type_quote_cash.vm";
    }


    @Override
    public void render(Writer writer, FormField formField, Object value) {

        // Tipi incassi quote
        List<SimpleItem> typeQuoteCash = new ArrayList<>();

        SimpleItem i1 = new SimpleItem();
        i1.setValue("IQA");
        i1.setLabel("Incassi quote associative");
        typeQuoteCash.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue("IQP");
        i2.setLabel("Incassi quote previsionali");
        typeQuoteCash.add(i2);

        SimpleItem i3 = new SimpleItem();
        i3.setValue("IQI");
        i3.setLabel("Incassi quote Inps");
        typeQuoteCash.add(i3);


        super.render(writer, formField, typeQuoteCash);
    }
}
