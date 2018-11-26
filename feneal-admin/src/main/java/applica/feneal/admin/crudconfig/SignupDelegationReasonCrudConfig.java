package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.UserSingleSearchableFieldRenderer;
import applica.feneal.domain.data.core.ProcedureTypeRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
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
public class SignupDelegationReasonCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(CausaleIscrizioneDelega.class, "signupdelegationreason")
                .repository(SignupDelegationReasonRepository.class)
                .field("description", "Descrizione");


        GridConfigurator.configure(CausaleIscrizioneDelega.class, "signupdelegationreason")
                .repository(SignupDelegationReasonRepository.class)
                .column("description", "Descrizione", true);
    }
}
