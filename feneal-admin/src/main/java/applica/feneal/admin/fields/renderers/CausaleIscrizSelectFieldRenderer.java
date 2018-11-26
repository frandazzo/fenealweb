package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by angelo on 17/04/16.
 */
@Component
public class CausaleIscrizSelectFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<CausaleIscrizioneDelega> causaleIscrizList = causaleIscrizioneDelegaRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(causaleIscrizList, (c) -> c.getDescription(), (c) -> String.valueOf(c.getId()));
    }

}
