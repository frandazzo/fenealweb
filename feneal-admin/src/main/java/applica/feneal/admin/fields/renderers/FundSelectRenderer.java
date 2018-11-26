package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.FundRepository;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.setting.Fondo;
import applica.framework.LoadRequest;
import applica.framework.builders.LoadRequestBuilder;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FundSelectRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private FundRepository fundRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Fondo> funds = fundRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(funds, (f) -> f.getDescription(), (f) -> String.valueOf(f.getId()));
    }
}