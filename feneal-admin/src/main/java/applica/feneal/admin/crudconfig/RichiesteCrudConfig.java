package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.domain.data.core.servizi.RichiesteInfoRepository;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import applica.framework.widgets.fields.renderers.HtmlFieldRenderer;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class RichiesteCrudConfig implements AppSetup {




    @Override
    public void setup() {

        FormConfigurator.configure(RichiestaInfo.class, "richiesta")
                .repository(RichiesteInfoRepository.class)
                .field("data", "Data", CurrentDateFieldRenderer.class)
                .field("province", "Provincia", LoggedUserProvinceNonOptionalSelectFieldRenderer.class, GeoProvinceDataMapper.class)
                .field("destinatario", "Destinatario")
                .field("note", "Note", HtmlFieldRenderer.class)
                .field("requestToProvince", "Richiesta al territorio di", RichiesteTerrStringProvinceSelectRenderer.class)
                .field("lavoratore", "lavoratore", HiddenFieldRenderer.class);



        GridConfigurator.configure(RichiestaInfo.class, "richiesta")
                .repository(RichiesteInfoRepository.class)
                .column("lavoratore", "Lavoratore", true)
                .column("data", "Data", false)
                .column("requestToProvince", "Richiesta al territorio di", false)
                .column("destinatario", "Destinatario", false);
    }
}