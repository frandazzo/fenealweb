package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.UserSingleSearchableFieldRenderer;
import applica.feneal.domain.data.core.FundRepository;
import applica.feneal.domain.data.core.ProcedureTypeRepository;
import applica.feneal.domain.model.setting.Fondo;
import applica.feneal.domain.model.setting.TipoPratica;
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
public class ProcedureTypeCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(TipoPratica.class, "proceduretype")
                .repository(ProcedureTypeRepository.class)
                .field("description", "Descrizione")
                .field("restricted", "Limitato")
                .field("jollyUser", "Utente jolly", UserSingleSearchableFieldRenderer.class)
                .field("controlPraticaUser", "Utente di controllo pratica", UserSingleSearchableFieldRenderer.class)
                .field("integrationPraticaUser", "Utente di integrazione pratica", UserSingleSearchableFieldRenderer.class)
                .field("confirmPraticaUser", "Utente di conferma pratica", UserSingleSearchableFieldRenderer.class)
                .field("rejectedPraticaUser", "Utente di respingimento pratica", UserSingleSearchableFieldRenderer.class)
                .field("closedPraticaUser", "Utente di chiusura pratica", UserSingleSearchableFieldRenderer.class);


        GridConfigurator.configure(TipoPratica.class, "proceduretype")
                .repository(ProcedureTypeRepository.class)
                .column("description", "Descrizione", true)
                .column("restricted", "Limitato", false);
    }
}
