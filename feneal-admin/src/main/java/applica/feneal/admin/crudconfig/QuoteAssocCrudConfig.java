package applica.feneal.admin.crudconfig;

import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.GridConfigurator;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 28/04/2016.
 */
@Component
public class QuoteAssocCrudConfig implements AppSetup {




    @Override
    public void setup() {


        /*GridConfigurator.configure(RiepilogoQuoteAssociative.class, "quoteassociative")
                .repository(QuoteAssocRepository.class)
                .column("id", "Id", true)
                .column("provincia", "Provincia", false)
                .column("dataRegistrazione", "Data registrazione", false)
                .column("dataDocumento", "Data documento", false)
                .column("tipoDocumento", "Tipo documento", true)
                .column("settore", "Settore", false)
                .column("ente", "Ente", false)
                .column("competenza", "Competenza", false)
                .column("importedLogFilePath", "File di importazione", true)
                .column("originalFileServerName", "File originale", true)
                .column("xmlFileServerName", "File xml normalizzato", true);
                */
    }
}