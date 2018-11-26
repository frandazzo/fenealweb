package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by angelo on 28/04/2016.
 */
@Component
public class MagazzinoDelegheCrudConfig implements AppSetup {




    @Override
    public void setup() {

        FormConfigurator.configure(MagazzinoDelega.class, "magazzinodeleghe")
                .repository(MagazzinoDelegheRepository.class)
                .field("data", "Data", CurrentDateFieldRenderer.class)
                .field("province", "Provincia", LoggedUserProvinceNonOptionalSelectFieldRenderer.class, GeoProvinceDataMapper.class)
                .field("paritethic", "Ente", ParitheticCanoniclanonOptionalFieldRenderer.class)

                .field("lavoratore", "lavoratore", HiddenFieldRenderer.class)
                .field("collaboratore", "Collaboratore", CollaboratoreSingleSearchableFieldRenderer.class);


        GridConfigurator.configure(MagazzinoDelega.class, "magazzinodeleghe")
                .repository(MagazzinoDelegheRepository.class)
                .column("lavoratore", "Lavoratore", true)
                .column("data", "Data", false)
                .column("paritethic", "Ente", false)

                .column("province", "Provincia", false);
    }
}