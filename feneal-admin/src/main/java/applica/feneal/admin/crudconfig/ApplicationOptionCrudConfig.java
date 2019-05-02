package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.WidgetContextSelectRenderer;
import applica.feneal.admin.fields.renderers.WidgetTypeSelectRenderer;
import applica.feneal.domain.data.core.ApplicationOptionRepository;
import applica.feneal.domain.data.core.VisibleFunctionRepository;
import applica.feneal.domain.model.core.Widget;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
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
public class ApplicationOptionCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(ApplicationOptions.class, "applicationoption")
                .repository(ApplicationOptionRepository.class)
                .field("createDelegaAsAccettata", "Crea delega come accettata")

                .field("updateFirmasDuringImport", "Aggiorna aziende")
                .field("updateWorkersDuringImport", "Aggiorna utenti")
                .field("creaDelegaIfNotExistDuringImport", "Crea delega se non esiste")
                .field("associaDelegaDuringImport", "Associa delega")
                .field("enableExclusiveLiberiQuery", "Abilita query esclusive su report non iscritti");


        GridConfigurator.configure(ApplicationOptions.class, "applicationoption")
                .repository(ApplicationOptionRepository.class)
                .column("createDelegaAsAccettata", "Crea delega come accettata", true);
    }
}
