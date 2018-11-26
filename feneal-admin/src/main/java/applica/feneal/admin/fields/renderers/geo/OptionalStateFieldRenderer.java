package applica.feneal.admin.fields.renderers.geo;

import applica.feneal.domain.data.geo.CountriesRepository;
import applica.feneal.domain.model.geo.Country;
import applica.framework.Entity;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Francesco on 18/10/2015.
 */
@Component
public class OptionalStateFieldRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    CountriesRepository rep;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<>();
        List<Country> ll = rep.find(LoadRequest.build()).getRows();

        Collections.sort(ll, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });


        for (Country user : ll) {
            SimpleItem i = new SimpleItem();
            i.setValue(String.valueOf(user.getIid()));
            i.setLabel(user.getDescription());
            result.add(i);
        }

        return result;


    }

    @Override
    public void render(Writer writer, FormField field, Object value) {
        Object id = null;
        if (value != null) {
            try{

                id =((Entity)value).getId();
            }catch(Exception e){
                id = value;
            }

        }

        super.render(writer, field, id);
    }
}