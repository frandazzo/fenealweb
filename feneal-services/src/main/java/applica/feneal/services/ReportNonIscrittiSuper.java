package applica.feneal.services;

import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;

import java.util.List;

public interface ReportNonIscrittiSuper {
    List<LiberoDbNazionale> retrieveLiberi(LiberoReportSearchParams params);
}
