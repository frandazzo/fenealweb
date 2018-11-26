package applica.feneal.admin.fields.renderers.geo;

import applica.feneal.domain.data.geo.CitiesRepository;
import applica.feneal.domain.model.geo.City;
import applica.framework.Entity;
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
public class OptionalCityFieldRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    CitiesRepository rep;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<>();
        List<City> ll = new ArrayList<City>();

        Collections.sort(ll, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });

        for (City city : ll) {
            SimpleItem i = new SimpleItem();
            i.setValue(String.valueOf(city.getIid()));
            i.setLabel(city.getDescription());
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
