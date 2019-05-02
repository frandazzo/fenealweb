package applica.feneal.admin.fields.renderers;

import applica.feneal.services.ReportPrevediService;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class YearPrevediSelectFieldRenderer extends SelectFieldRenderer {

    @Autowired
    private ReportPrevediService rptSvc;

    @Override
    public List<SimpleItem> getItems() {

        List<SimpleItem> years = new ArrayList<>();

        List<Integer> anni = rptSvc.getAnniPrevedi();

        for (Integer i : anni) {
            SimpleItem simpleItem = new SimpleItem(String.valueOf(i), String.valueOf(i));
            years.add(simpleItem);
        }

        return years;
    }
}
