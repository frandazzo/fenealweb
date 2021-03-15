package applica.feneal.admin.fields.renderers.RSU;

import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.domain.model.setting.CausaleComunicazione;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by felicegramegna on 09/03/2021.
 */

@Component
public class ContrattoRsuSelectFieldRenderer extends SelectFieldRenderer {

    @Autowired
    private ContrattoRSURepository contrattoRSURepository;

    @Override
    public List<SimpleItem> getItems() {

        List<ContrattoRSU> contratto = contrattoRSURepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(contratto, (s) -> s.getDescription(), (s) -> String.valueOf(s.getLid()));
    }
}
