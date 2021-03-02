package applica.feneal.admin.crudconfig;

import applica.feneal.domain.data.core.RSU.SedeRSURepository;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.GridConfigurator;
import org.springframework.stereotype.Component;

@Component
public class SedeRSUCrudConfig implements AppSetup {

    @Override
    public void setup() {

        GridConfigurator.configure(SedeRSU.class, "sedersu")
                .repository(SedeRSURepository.class)
                .column("description", "Descrizione", true)
                .column("piva", "P.iva", false)
                .column("province", "Provincia", false)
                .column("city", "Città", false)
                .column("address", "Indirizzo", false);

    }
}
