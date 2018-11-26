package applica.feneal.admin.crudconfig;

import applica.feneal.domain.data.core.FundRepository;
import applica.feneal.domain.data.core.RevocationReasonRepository;
import applica.feneal.domain.model.setting.Fondo;
import applica.feneal.domain.model.setting.TipoDocumento;
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
public class FundCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Fondo.class, "fund")
                .repository(FundRepository.class)
                .field("description", "Descrizione");


        GridConfigurator.configure(Fondo.class, "fund")
                .repository(FundRepository.class)
                .column("description", "Descrizione", true);
    }
}
