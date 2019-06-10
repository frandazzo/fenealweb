package applica.feneal.services;

import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
import applica.feneal.domain.model.core.servizi.search.UiIscrittoReportSearchParams;
import applica.feneal.domain.model.dbnazionale.UiIscrittiNazionaleExport;

import java.util.List;

public interface ReportQuoteVareseService {

    List<DettaglioQuotaAssociativa> retrieveQuoteVarese(UiQuoteVareseReportSearchParams params);

    String compileFileForLavoratore(String id, String templatePath) throws Exception;
}

