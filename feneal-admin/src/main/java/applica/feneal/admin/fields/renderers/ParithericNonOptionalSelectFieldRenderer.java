package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.Filters;
import applica.feneal.domain.model.core.Paritethic;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.FormField;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import applica.framework.widgets.fields.renderers.SingleSearchableInputFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by antoniolovicario on 16/04/16.
 */
@Component
public class ParithericNonOptionalSelectFieldRenderer extends SelectFieldRenderer {
    @Autowired
    private ParitheticRepository paritheticRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Paritethic> enti = paritheticRepository.find(null).getRows();
        return SimpleItem.createList(enti, (s) -> s.getType(), (s) -> String.valueOf(s.getLid()));
    }
}
