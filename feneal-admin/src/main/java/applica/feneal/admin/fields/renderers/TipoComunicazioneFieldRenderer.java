package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.CommunicationTypeRepository;
import applica.feneal.domain.model.setting.TipoComunicazione;
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
public class TipoComunicazioneFieldRenderer extends SelectFieldRenderer {

    @Autowired
    private CommunicationTypeRepository commRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<TipoComunicazione> comm = commRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(comm, (s) -> s.getDescription(), (s) -> String.valueOf(s.getLid()));
    }

}