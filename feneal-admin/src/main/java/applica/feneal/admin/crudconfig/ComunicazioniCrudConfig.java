package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.domain.data.core.servizi.ComunicazioniRepository;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.setting.TipoComunicazione;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class ComunicazioniCrudConfig implements AppSetup {




    @Override
    public void setup() {

        FormConfigurator.configure(Comunicazione.class, "comunicazione")
                .repository(ComunicazioniRepository.class)
                .field("data", "Data", CurrentDateFieldRenderer.class)
                .field("tipo", "Tipo", TipoComunicazioneFieldRenderer.class)
                .field("causale", "Causale", CausaleComunicazioneFieldRenderer.class)
                .field("province", "Provincia", LoggedUserProvinceNonOptionalSelectFieldRenderer.class, GeoProvinceDataMapper.class)
                .field("oggetto", "Oggetto", TextAreaFieldRenderer.class)
                .field("lavoratore", "lavoratore", HiddenFieldRenderer.class);




        GridConfigurator.configure(Comunicazione.class, "comunicazione")
                .repository(ComunicazioniRepository.class)
                .column("lavoratore", "Lavoratore", true)
                .column("data", "Data", false)
                .column("tipo", "Tipo comunicazione", false)
                .column("causale", "Causale", false);
    }
}