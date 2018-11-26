package applica.feneal.admin.fields.renderers.geo;

import applica.framework.library.SimpleItem;
import applica.framework.library.i18n.Localization;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 06/01/2016.
 */
@Component
public class OptionalContinentFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<SimpleItem> getItems() {

        Localization localization = new Localization(applicationContext);

        List<SimpleItem> continents = new ArrayList<>();

        SimpleItem i1 = new SimpleItem();
        i1.setValue("Africa");
        i1.setLabel("Africa");
        continents.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue("America");
        i2.setLabel("America");
        continents.add(i2);

        SimpleItem i3 = new SimpleItem();
        i3.setValue("Asia");
        i3.setLabel("Asia");
        continents.add(i3);

        SimpleItem i4 = new SimpleItem();
        i4.setValue("Europe");
        i4.setLabel(localization.getMessage("label.Europe"));
        continents.add(i4);

        SimpleItem i5 = new SimpleItem();
        i5.setValue("Oceania");
        i5.setLabel("Oceania");
        continents.add(i5);


        return continents;

    }

    @Override
    public void render(Writer writer, FormField field, Object value) {


        super.render(writer, field, value);
    }
}