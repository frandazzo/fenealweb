package applica.feneal.admin.fields.renderers;

import applica.feneal.admin.crudconfig.SignupDelegationReasonCrudConfig;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.Filters;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by antoniolovicario on 16/04/16.
 */
@Component
public class CausaleIscrizioneDelegaSelectFieldRenderer extends SelectFieldRenderer {

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRepository;

    @Override
    public List<SimpleItem> getItems() {
        return SimpleItem.createList(causaleIscrizioneDelegaRepository.find(LoadRequest.build().sort(Filters.DESCRIPTION, false)).getRows(), "description", "id");
    }
}
