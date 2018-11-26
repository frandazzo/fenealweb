package applica.feneal.admin.crudconfig;

import applica.feneal.domain.data.core.CommunicationTypeRepository;
import applica.feneal.domain.data.core.FundRepository;
import applica.feneal.domain.model.setting.Fondo;
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
public class CommunicationTypeCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(TipoComunicazione.class, "communicationtype")
                .repository(CommunicationTypeRepository.class)
                .field("description", "Descrizione");


        GridConfigurator.configure(TipoComunicazione.class, "communicationtype")
                .repository(CommunicationTypeRepository.class)
                .column("description", "Descrizione", true);
    }
}
