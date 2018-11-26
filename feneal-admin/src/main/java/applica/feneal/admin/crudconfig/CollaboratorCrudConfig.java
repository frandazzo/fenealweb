package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.UserSingleSearchableFieldRenderer;
import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.data.core.ProcedureTypeRepository;
import applica.feneal.domain.model.setting.Collaboratore;
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
public class CollaboratorCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Collaboratore.class, "collaborator")
                .repository(CollaboratorRepository.class)
                .field("description", "Descrizione");


        GridConfigurator.configure(Collaboratore.class, "collaborator")
                .repository(CollaboratorRepository.class)
                .column("description", "Descrizione", true);

    }
}
