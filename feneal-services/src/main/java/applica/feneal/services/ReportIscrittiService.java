package applica.feneal.services;

import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.servizi.search.UiIscrittoReportSearchParams;
import applica.feneal.domain.model.dbnazionale.UiIscrittiNazionaleExport;

import java.util.List;

/**
 * Created by angelo on 29/05/2016.
 */
public interface ReportIscrittiService {

    List<DettaglioQuotaAssociativa> retrieveQuoteIscritti(UiIscrittoReportSearchParams params);


    List<UiIscrittiNazionaleExport> getIscrittiNazionale(int anno, String settore, String provincia, String raggruppa);
}
