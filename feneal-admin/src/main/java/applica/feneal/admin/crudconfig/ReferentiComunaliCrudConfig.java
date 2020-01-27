package applica.feneal.admin.crudconfig;


import applica.feneal.admin.fields.renderers.LoggedUserDelegheLombardiaSassariOptionalSelectFIeldRenderer;
import applica.feneal.admin.fields.renderers.geo.ComuneProvniciaBariSelectFieldRenderer;
import applica.feneal.domain.data.core.ristorniEdilizia.ReferentiRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import org.springframework.stereotype.Component;

@Component
public class ReferentiComunaliCrudConfig implements AppSetup {

    @Override
    public void setup(){
        FormConfigurator.configure(Referenti.class, "referenti")
                .repository(ReferentiRepository.class)
                .field("completeName", "Nominativo")
                .param("completeName", Params.ROW, "dt")
                .param("completeName", Params.COLS, Values.COLS_6)
                .field("city", "Comune", ComuneProvniciaBariSelectFieldRenderer.class)
                .param("city", Params.ROW, "dt")
                .param("city", Params.COLS, Values.COLS_6)
                .field("proRataShare", "Percentuale ristorno")
                .param("proRataShare", Params.ROW, "dt1")
                .param("proRataShare", Params.COLS, Values.COLS_6);

        GridConfigurator.configure(Referenti.class, "referenti")
                .repository(ReferentiRepository.class)
                .column("completeName", "Nominativo", true)
                .column("city", "Comune", false)
                .column("proRataShare", "Percentuale ristorno", false);
    }
}
