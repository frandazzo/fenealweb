package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.domain.data.core.servizi.MagazzinoDelegheLecceRepository;
import applica.feneal.domain.model.core.servizi.MagazzinoDelegaLecce;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import org.springframework.stereotype.Component;

@Component
public class MagazzineDelegheLecceCrudConfig implements AppSetup {
    @Override
    public void setup() {
        FormConfigurator.configure(MagazzinoDelegaLecce.class, "magazzinodeleghelecce")
                .repository(MagazzinoDelegheLecceRepository.class)
                .field("data", "Data", CurrentDateFieldRenderer.class)
                .field("province", "Provincia", LoggedUserProvinceNonOptionalSelectFieldRenderer.class, GeoProvinceDataMapper.class)
                .field("paritethic", "Ente", ParitheticCanoniclanonOptionalFieldRenderer.class)
                .field("subscribeReason", "Causale", CausaleIscrizioneDelegaSelectFieldRenderer.class)
                .field("lavoratore", "lavoratore", HiddenFieldRenderer.class)
                .field("collaboratore", "Collaboratore", CollaboratoreSingleSearchableFieldRenderer.class);


        GridConfigurator.configure(MagazzinoDelegaLecce.class, "magazzinodeleghelecce")
                .repository(MagazzinoDelegheLecceRepository.class)
                .column("lavoratore", "Lavoratore", true)
                .column("data", "Data", false)
                .column("numGiorniDaSottoscrizione", "Num. gg. dalla sottoscrizione", false)
                .column("paritethic", "Ente", false)
                .column("province", "Provincia", false)
                .column("subscribeReason", "Causale", false);
    }
}
