package applica.feneal.services;

import applica.feneal.domain.model.core.servizi.Documento;
import applica.feneal.domain.model.core.servizi.search.UiDocumentoReportSearchParams;

import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
public interface ReportDocumentiService {

    List<Documento> retrieveDocumenti(UiDocumentoReportSearchParams params);

}
