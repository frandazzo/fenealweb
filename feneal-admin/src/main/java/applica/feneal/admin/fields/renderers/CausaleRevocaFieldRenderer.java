package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.RevocationReasonRepository;
import applica.feneal.domain.model.Filters;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fgran on 16/05/2016.
 */
@Component
public class CausaleRevocaFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private RevocationReasonRepository causaleRepository;

    @Override
    public List<SimpleItem> getItems() {
        return SimpleItem.createList(causaleRepository.find(LoadRequest.build().sort(Filters.DESCRIPTION, false)).getRows(), "description", "id");
    }
}