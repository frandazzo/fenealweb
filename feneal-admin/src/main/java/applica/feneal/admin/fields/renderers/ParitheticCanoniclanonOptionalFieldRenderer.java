package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.model.core.Paritethic;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.SelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParitheticCanoniclanonOptionalFieldRenderer extends SelectFieldRenderer {
    @Autowired
    private ParitheticRepository paritheticRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Paritethic> enti = paritheticRepository.find(null).getRows().stream()
                .filter(a -> a.getDescription().equals(Paritethic.ente_cassaedile) || a.getDescription().equals(Paritethic.ente_edilcassa))
                .collect(Collectors.toList());
        return SimpleItem.createList(enti, (s) -> s.getType(), (s) -> String.valueOf(s.getLid()));
    }
}
