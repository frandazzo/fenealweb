package applica.feneal.admin.fields.renderers.geo;

import applica.feneal.domain.data.geo.RegonsRepository;
import applica.feneal.domain.model.geo.Region;
import applica.framework.Entity;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelo on 06/01/2016.
 */
@Component
public class OptionalRegionFieldRenderer extends OptionalSelectFieldRenderer {


    @Autowired
    RegonsRepository rep;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<>();
        List<Region> ll = rep.find(LoadRequest.build()).getRows();

        for (Region region : ll) {
            SimpleItem i = new SimpleItem();
            i.setValue(String.valueOf(region.getIid()));
            i.setLabel(region.getDescription());
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