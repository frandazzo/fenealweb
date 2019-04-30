package applica.feneal.services;

import applica.feneal.domain.model.core.ImportData;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;

import java.util.List;

public interface ReportNonIscrittiSuper {
    List<LiberoDbNazionale> retrieveLiberi(LiberoReportSearchParams params , boolean isOldReportStyle);
    LiberoDbNazionale analyzeFiscaleCodeData(String fiscalCode, boolean isOldStyleReport);

    List<LiberoDbNazionale> incrociaCodiciFiscali(ImportData file, boolean isOldStyleReport) throws Exception;
}
