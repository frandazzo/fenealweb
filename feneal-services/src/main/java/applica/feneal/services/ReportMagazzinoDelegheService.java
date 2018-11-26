package applica.feneal.services;

import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheSearchParams;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;


import java.util.List;

/**
 * Created by angelo on 17/05/2016.
 */
public interface ReportMagazzinoDelegheService {

    List<MagazzinoDelega> retrieveMagazzinoDeleghe(UiMagazzinoDelegheSearchParams params);

    List<MagazzinoDelega> retrieveDistinctMagazzinoDeleghe(UiMagazzinoDelegheSearchParams params);

    String generateDeleghe(List<MagazzinoDelega> del) throws Exception;
}
