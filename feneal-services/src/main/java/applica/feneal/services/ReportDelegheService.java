package applica.feneal.services;

import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.deleghe.UiDelegheReportSearchParams;

import java.text.ParseException;
import java.util.List;

/**
 * Created by angelo on 17/05/2016.
 */
public interface ReportDelegheService {

    List<Delega> retrieveDeleghe(UiDelegheReportSearchParams params);
    List<Delega> retrieveDelegheForInoltroEAccettazioneMassivo(UiDelegheReportSearchParams params);

}
