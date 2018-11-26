package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.Filters;
import applica.feneal.domain.model.core.Paritethic;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 16/05/2016.
 */
@Component
public class ParitethicListFieldRenderer  extends SelectFieldRenderer {




    @Override
    public List<SimpleItem> getItems() {



        List<SimpleItem> res = new ArrayList<>();


        SimpleItem simpleItem = new SimpleItem("CASSA EDILE","CASSA EDILE");
        res.add(simpleItem);

        SimpleItem simpleItem1 = new SimpleItem("EDILCASSA","EDILCASSA");
        res.add(simpleItem1);


        SimpleItem simpleItema = new SimpleItem("CALEC","CALEC");
        res.add(simpleItema);

        SimpleItem simpleItem11 = new SimpleItem("CEA","CEA");
        res.add(simpleItem11);

        SimpleItem CEC = new SimpleItem("CEC","CEC");
        res.add(CEC);

        SimpleItem CEDA = new SimpleItem("CEDA","CEDA");
        res.add(CEDA);

        SimpleItem CEDAF = new SimpleItem("CEDAF","CEDAF");
        res.add(CEDAF);

        SimpleItem CEDAM = new SimpleItem("CEDAM","CEDAM");
        res.add(CEDAM);

        SimpleItem CELCOF = new SimpleItem("CELCOF","CELCOF");
        res.add(CELCOF);

        SimpleItem CEMA = new SimpleItem("CEMA","CEMA");
        res.add(CEMA);


        SimpleItem CERT = new SimpleItem("CERT","CERT");
        res.add(CERT);

        SimpleItem CEVA = new SimpleItem("CEVA","CEVA");
        res.add(CEVA);

        SimpleItem CEDAIIER = new SimpleItem("CEDAIIER","CEDAIIER");
        res.add(CEDAIIER);

        SimpleItem FALEA = new SimpleItem("FALEA","FALEA");
        res.add(FALEA);


        return res;
    }
}