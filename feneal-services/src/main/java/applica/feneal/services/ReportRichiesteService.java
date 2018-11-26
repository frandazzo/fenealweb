package applica.feneal.services;

import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.feneal.domain.model.core.servizi.search.UiRichiestaReportSearchParams;

import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
public interface ReportRichiesteService {

    List<RichiestaInfo> retrieveRichieste(UiRichiestaReportSearchParams params);

}
