package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.DocumentTypeRepository;
import applica.feneal.domain.model.setting.TipoDocumento;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class OptionalTipoDocumentoFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private DocumentTypeRepository docRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<TipoDocumento> types = docRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(types, (s) -> s.getDescription(), (s) -> String.valueOf(s.getId()));
    }

}