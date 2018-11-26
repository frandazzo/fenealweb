package applica.feneal.admin.crudconfig;

import applica.feneal.domain.data.core.CommunicationReasonRepository;
import applica.feneal.domain.data.core.CommunicationTypeRepository;
import applica.feneal.domain.model.setting.CausaleComunicazione;
import applica.feneal.domain.model.setting.TipoComunicazione;
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
public class CommunicationReasonCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(CausaleComunicazione.class, "communicationreason")
                .repository(CommunicationReasonRepository.class)
                .field("description", "Descrizione");


        GridConfigurator.configure(CausaleComunicazione.class, "communicationreason")
                .repository(CommunicationReasonRepository.class)
                .column("description", "Descrizione", true);
    }
}
