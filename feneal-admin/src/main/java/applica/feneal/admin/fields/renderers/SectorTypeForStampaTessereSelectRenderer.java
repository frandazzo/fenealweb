package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.Sector;
import applica.framework.library.SimpleItem;
import applica.framework.security.Security;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 27/01/2017.
 */
@Component
public class SectorTypeForStampaTessereSelectRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private Security sec;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> result = new ArrayList<SimpleItem>();




        if (((User) sec.getLoggedUser()).getCompany().containProvince("BOLZANO")){
            SimpleItem i = new SimpleItem();
            i.setValue("EDILIZIA - BAUSEKTOR");
            i.setLabel("Edile");
            result.add(i);

            SimpleItem i1 = new SimpleItem();
            i1.setValue("IMPIANTI FISSI - ANDERE SEKTOREN");
            i1.setLabel("Impianti fissi");
            result.add(i1);


            SimpleItem i2 = new SimpleItem();
            i2.setValue(Sector.sector_inps);
            i2.setLabel("Inps");
            result.add(i2);

        }else{
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
            i2.setLabel("Inps");
            result.add(i2);

        }





        return result;
    }
}

