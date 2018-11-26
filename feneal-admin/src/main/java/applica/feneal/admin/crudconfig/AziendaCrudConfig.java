package applica.feneal.admin.crudconfig;

import applica.feneal.admin.data.AziendeRepositoryWrapper;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.search.AziendeSearchForm;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 10/04/2016.
 */
@Component
public class AziendaCrudConfig implements AppSetup {




    @Override
    public void setup() {

        GridConfigurator.configure(Azienda.class, "firm")
                .repository(AziendeRepositoryWrapper.class)
                .searchForm(AziendeSearchForm.class)

                .column("description", "Ragione sociale", true)
                .column("province", "Provincia", false)
                .column("city", "Città", false)
                .column("address", "Indirizzo", false)
                .column("cap", "CAP", false);

    }
}

