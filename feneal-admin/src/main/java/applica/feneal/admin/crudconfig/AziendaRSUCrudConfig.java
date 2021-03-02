package applica.feneal.admin.crudconfig;

import applica.feneal.admin.data.AziendeRSURepositoryWrapper;
import applica.feneal.admin.data.AziendeRepositoryWrapper;
import applica.feneal.admin.search.AziendeSearchForm;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.GridConfigurator;
import org.springframework.stereotype.Component;

@Component
public class AziendaRSUCrudConfig implements AppSetup {

    @Override
    public void setup() {

        GridConfigurator.configure(AziendaRSU.class, "firmrsu")
                .repository(AziendeRSURepositoryWrapper.class)
                .searchForm(AziendeSearchForm.class)

                .column("description", "Ragione sociale", true)
                .column("piva", "P.iva", false)
                .column("province", "Provincia", false)
                .column("city", "Città", false)
                .column("address", "Indirizzo", false)
                .column("cap", "CAP", false);

    }
}
