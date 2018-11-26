package applica.feneal.services;

import applica.feneal.domain.model.core.servizi.DocumentoAzienda;
import applica.feneal.domain.model.core.servizi.search.UiDocumentoAziendaReportSearchParams;

import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
public interface ReportDocumentiAziendaService {

    List<DocumentoAzienda> retrieveDocumentiAzienda(UiDocumentoAziendaReportSearchParams params);

}
