package applica.feneal.admin.crudconfig;

import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.fields.renderers.empty.EmptyFieldRenderer;
import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.HiddenFieldRenderer;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class DocumentiCrudConfig implements AppSetup {




    @Override
    public void setup() {

        FormConfigurator.configure(Documento.class, "documento")
                .repository(DocumentiRepository.class)
                .field("data", "Data", CurrentDateFieldRenderer.class)
                .field("tipo", "Tipo", TipoDocumentoFieldRenderer.class)
                .field("notes", "Note")
                .field("province", "Provincia", LoggedUserProvinceNonOptionalSelectFieldRenderer.class,GeoProvinceDataMapper.class)
                .field("lavoratore", "lavoratore", HiddenFieldRenderer.class)
                .field("collaboratore", "Collaboratore", CollaboratoreSingleSearchableFieldRenderer.class)
                .field("allegato1", "Allegato1", DocumentFileRenderer.class)
                .field("allegato2", "Allegato2", DocumentFileRenderer.class)
                .field("allegato3", "Allegato3", DocumentFileRenderer.class)
                .field("allegato4", "Allegato4", DocumentFileRenderer.class)
                .field("allegato5", "Allegato5", DocumentFileRenderer.class)
                .field("allegato6", "Allegato6", DocumentFileRenderer.class)
                .field("nomeallegato1", "nomeAllegato1", EmptyFieldRenderer.class)
                .field("nomeallegato2", "nomeAllegato2", EmptyFieldRenderer.class)
                .field("nomeallegato3", "nomeAllegato3", EmptyFieldRenderer.class)
                .field("nomeallegato4", "nomeAllegato4", EmptyFieldRenderer.class)
                .field("nomeallegato5", "nomeAllegato5", EmptyFieldRenderer.class)
                .field("nomeallegato6", "nomeAllegato6", EmptyFieldRenderer.class);



        GridConfigurator.configure(Documento.class, "documento")
                .repository(DocumentiRepository.class)
                .column("lavoratore", "Lavoratore", true)
                .column("data", "Data", false)
                .column("tipo", "Tipo documento", false)
                .column("notes","Note",false)
                .column("id", "Num. allegati", false, AttachmentsDocumentCellRenderer.class);
    }
}
