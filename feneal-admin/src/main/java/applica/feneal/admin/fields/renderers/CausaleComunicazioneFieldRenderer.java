package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.CommunicationReasonRepository;
import applica.feneal.domain.model.setting.CausaleComunicazione;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class CausaleComunicazioneFieldRenderer extends SelectFieldRenderer {

    @Autowired
    private CommunicationReasonRepository causaleRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<CausaleComunicazione> causali = causaleRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(causali, (s) -> s.getDescription(), (s) -> String.valueOf(s.getLid()));
    }

}