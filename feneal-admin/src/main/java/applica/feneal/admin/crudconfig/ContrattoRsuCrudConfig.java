package applica.feneal.admin.crudconfig;

import applica.feneal.admin.data.RSU.ContrattoRsuRepositoryWrapper;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.GridConfigurator;

import org.springframework.stereotype.Component;

/**
 * Created by felicegramegna on 08/03/2021.
 */

@Component
public class ContrattoRsuCrudConfig implements AppSetup {
    @Override
    public void setup() {

        GridConfigurator.configure(ContrattoRSU.class, "contractrsu")
                .repository(ContrattoRsuRepositoryWrapper.class)
                .column("description", "Descrizione", true)
                .column("rsuMin", "Rsu Min.", false)
                .column("rsuMax","Rsu Max.",false);
    }
}
