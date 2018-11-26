package applica.feneal.services;

import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.search.LiberoReportSearchParams;

import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
public interface ReportNonIscrittiService {

    List<LiberoDbNazionale> retrieveLiberi(LiberoReportSearchParams params);

    List<LiberoDbNazionale> retrieveLiberiForWorker(long id);
}
