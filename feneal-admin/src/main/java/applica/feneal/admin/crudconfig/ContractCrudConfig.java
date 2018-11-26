package applica.feneal.admin.crudconfig;

import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.data.core.ContractRepository;
import applica.feneal.domain.model.core.Contract;
import applica.feneal.domain.model.setting.Collaboratore;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/13/15
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ContractCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Contract.class, "contract")
                .repository(ContractRepository.class)
                .field("description", "Descrizione");


        GridConfigurator.configure(Contract.class, "contract")
                .repository(ContractRepository.class)
                .column("description", "Descrizione", true);

    }
}
