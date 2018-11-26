package applica.feneal.admin.fields.renderers.geo;

import applica.feneal.domain.data.geo.ProvinceRepository;
import applica.feneal.domain.model.geo.Province;
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
public class OptionalProvinceFieldRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    ProvinceRepository rep;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<>();
        List<Province> ll = new ArrayList<Province>();

        Collections.sort(ll, new Comparator<Province>() {
            @Override
            public int compare(Province o1, Province o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });

        for (Province province : ll) {
            SimpleItem i = new SimpleItem();
            i.setValue(String.valueOf(province.getIid()));
            i.setLabel(province.getDescription());
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