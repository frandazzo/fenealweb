package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Sector;
import applica.framework.Entity;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.library.i18n.Localization;
import applica.framework.security.Security;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Writer;
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
public class SectorTypeSelectRenderer extends SelectFieldRenderer {


    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();

        SimpleItem i = new SimpleItem();
        i.setValue(Sector.sector_edile);
        i.setLabel("Edile");
        result.add(i);

        SimpleItem i1 = new SimpleItem();
        i1.setValue(Sector.sector_IMPIANTIFISSI);
        i1.setLabel("Impianti fissi");
        result.add(i1);

        SimpleItem i2 = new SimpleItem();
        i2.setValue(Sector.sector_inps);
        i2.setLabel("INPS");
        result.add(i2);


        return result;
    }
}