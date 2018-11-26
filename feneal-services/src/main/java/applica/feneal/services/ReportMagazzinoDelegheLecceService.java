package applica.feneal.services;

import applica.feneal.domain.model.core.deleghe.UiMagazzinoDelegheLecceSearchParams;
import applica.feneal.domain.model.core.servizi.MagazzinoDelegaLecce;

import java.util.List;

public interface ReportMagazzinoDelegheLecceService {

    List<MagazzinoDelegaLecce> retrieveMagazzinoDeleghe(UiMagazzinoDelegheLecceSearchParams params);

    List<MagazzinoDelegaLecce> retrieveDistinctMagazzinoDeleghe(UiMagazzinoDelegheLecceSearchParams params);

    String generateDeleghe(List<MagazzinoDelegaLecce> del) throws Exception;


}
