package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.ParitethicListFieldRenderer;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.core.Paritethic;
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
public class ParitethicCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Paritethic.class, "parithetic")
                .repository(ParitheticRepository.class)
                .field("description", "Descrizione")
                .field("type", "Tipo", ParitethicListFieldRenderer.class);


        GridConfigurator.configure(Paritethic.class, "parithetic")
                .repository(ParitheticRepository.class)
                .column("description", "Descrizione", true)
                .column("type", "Tipo", false);
    }
}
