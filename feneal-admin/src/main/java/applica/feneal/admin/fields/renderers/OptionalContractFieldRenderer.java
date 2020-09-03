package applica.feneal.admin.fields.renderers;

import applica.feneal.domain.data.core.ContractRepository;
import applica.feneal.domain.model.core.Contract;
import applica.framework.LoadRequest;
import applica.framework.library.SimpleItem;
import applica.framework.widgets.fields.renderers.OptionalSelectFieldRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OptionalContractFieldRenderer extends OptionalSelectFieldRenderer {

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public List<SimpleItem> getItems() {

        List<Contract> causali = contractRepository.find(LoadRequest.build()).getRows();
        return SimpleItem.createList(causali, (s) -> s.getDescription(), (s) -> String.valueOf(s.getLid()));
    }

}
