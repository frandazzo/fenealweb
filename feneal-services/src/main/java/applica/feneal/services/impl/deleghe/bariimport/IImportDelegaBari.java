package applica.feneal.services.impl.deleghe.bariimport;

import applica.feneal.domain.model.core.deleghe.ImportDelegheSummaryForBari;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.io.File;

/**
 * Created by fgran on 04/02/2017.
 */
public interface IImportDelegaBari {

    void importDelega(Lavoratore l , ImportDelegheSummaryForBari summary, File f) throws Exception;
}
