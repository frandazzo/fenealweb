package applica.feneal.services.impl.deleghe.bariimport;

import applica.feneal.domain.model.core.deleghe.ImportDelegheSummaryForBari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 04/02/2017.
 */
@Component
public class BariImportDelegheFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public IImportDelegaBari createImporter(ImportDelegheSummaryForBari summary){

        if (summary.getSubscription().equals("ISCRIZIONE"))
            return applicationContext.getBean(IscrizioneImporter.class);
        if (summary.getSubscription().equals("ISCR.INEFF"))
            return applicationContext.getBean(IscrizioneInefficaceImporter.class);

        if (summary.getRevoke().equals("REVOCA/DEL"))
            return applicationContext.getBean(RevocaDelegaImporter.class);
        if (summary.getRevoke().equals("REVOCA/BIA"))
            return applicationContext.getBean(RevocaDelegaImporter.class);
        if (summary.getDoubleDel().equals("DOPPIONE"))
            return applicationContext.getBean(DoppioneImporter.class);

        //in caso di anomalia...
        return applicationContext.getBean(AnomaliaImporter.class);

    }
}
