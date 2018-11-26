package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.core.VisibleFunction;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class VisibleFunctionSelectRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(VisibleFunction.sms);
        i.setLabel("SMS");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue(VisibleFunction.prtiche);
        i1.setLabel("Pratiche");
        result.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue(VisibleFunction.comunicazioni);
        i2.setLabel("Comunicazioni");
        result.add(i2);


        return result;
    }
}