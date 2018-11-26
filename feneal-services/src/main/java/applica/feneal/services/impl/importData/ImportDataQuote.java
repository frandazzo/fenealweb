package applica.feneal.services.impl.importData;

import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.feneal.domain.model.core.quote.fenealgestImport.RiepilogoQuotaDTO;
import applica.feneal.services.impl.quote.ImportQuoteHelper;
import applica.feneal.services.impl.quote.ImportQuoteLogger;
import applica.framework.library.options.OptionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 09/05/2017.
 */
@Component
public class ImportDataQuote {

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private ImportQuoteLogger logger;

    @Autowired
    private ImportQuoteHelper importHelper;


    public RiepilogoQuoteAssociative doImportQuoteManuali(RiepilogoQuotaDTO dto) throws Exception {
        String guid = dto.getGuid();
        String logFile = "importData_ " + guid + ".txt";

        dto.setLogFilename(logFile);

        logger.log(logFile, "StartImport", "Avvio creazione intestazione quota ", false);
        logger.log(logFile, "StartImport", "*****************************", false);
        logger.log(logFile, "StartImport", "*****************************", false);

        logger.log(logFile,  "DTO", "Validazione dettagli dto ", false);
//        if (dto.getDettagli().size() == 0)
//        {
//            logger.log(logFile,  "ERRORE", "Nessuna quota da inserire ", false);
//
//            throw new Exception("Nessuna quota da inserire");
//        }

        //adesso posso inserire il dto previa validazione
        importHelper.ValidateDTO(dto);

        //salvo i dati
        return importHelper.saveRiepilogoQuoteManuale(dto,logFile);


    }


}
