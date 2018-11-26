package applica.feneal.services;

import applica.feneal.domain.model.core.ImportData;

/**
 * Created by fgran on 19/04/2017.
 */
public interface ImportDataService {

    String importaDeleghe(ImportData importData) throws Exception;
    String importaAnagrafiche(ImportData importData) throws Exception;

}
