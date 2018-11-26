package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.SectorTypeSelectRenderer;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Sector;
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
public class SectorCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Sector.class, "sector")
                .repository(SectorRepository.class)
                .field("description", "Descrizione")
                .field("type", "Tipo", SectorTypeSelectRenderer.class);


        GridConfigurator.configure(Sector.class, "sector")
                .repository(SectorRepository.class)
                .column("description", "Descrizione", true)
                .column("type", "Tipo", false);
    }
}
