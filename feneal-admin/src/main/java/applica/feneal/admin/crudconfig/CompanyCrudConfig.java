package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.FunctionsMultiSearchableFieldRenderer;
import applica.feneal.admin.fields.renderers.ProvincesMultiSearchableFieldRenderer;
import applica.feneal.domain.data.core.CompanyRepository;
import applica.feneal.domain.model.core.Company;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.PasswordFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/13/15
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CompanyCrudConfig implements AppSetup {
    @Override
    public void setup() {

        FormConfigurator.configure(Company.class, "company")
                .repository(CompanyRepository.class)
                .field("description", "Descrizione")
                .field("provinces", "Province", ProvincesMultiSearchableFieldRenderer.class, GeoProvinceDataMapper.class)
                .fieldSet("Credenziali DB nazionale")
                .field("nationaUsername", "Username DB nazionale")
                .param("nationaUsername", Params.ROW, "dt")
                .param("nationaUsername", Params.COLS, Values.COLS_6)
                .field("nationUserPassword", "Password DB nazionale", PasswordFieldRenderer.class)
                .param("nationUserPassword", Params.ROW, "dt")
                .param("nationUserPassword", Params.COLS, Values.COLS_6)
                .fieldSet("Credenziali per invio SMS")
                .field("smsUsername", "SMS username")
                .param("smsUsername", Params.ROW, "dt1")
                .param("smsUsername", Params.COLS, Values.COLS_6)
                .field("smsPassword", "SMS password")
                .param("smsPassword", Params.ROW, "dt1")
                .param("smsPassword", Params.COLS, Values.COLS_6)
                .field("smsSenderNumber", "Numero di invio SMS")
                .param("smsSenderNumber", Params.ROW, "dt2")
                .param("smsSenderNumber", Params.COLS, Values.COLS_6)
                .field("smsSenderAlias", "Alias invio SMS")
                .param("smsSenderAlias", Params.ROW, "dt2")
                .param("smsSenderAlias", Params.COLS, Values.COLS_6)
                .fieldSet(" ")
                .field("visibleFunctions", "Funzionalità disponibili", FunctionsMultiSearchableFieldRenderer.class);



        GridConfigurator.configure(Company.class, "company")
                .repository(CompanyRepository.class)
                .column("description", "Descrizione", true)
                .column("provinces", "Province", false);
    }
}
