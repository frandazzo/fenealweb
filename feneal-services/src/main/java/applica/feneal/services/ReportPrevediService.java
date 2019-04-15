package applica.feneal.services;

import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.feneal.domain.model.dbnazionale.search.PrevediReportSearchParams;

import java.util.List;

public interface ReportPrevediService {

    List<Integer> getAnniPrevedi();
    List<LavoratorePrevedi> retrieveLavoratoriPrevedi(PrevediReportSearchParams params);

}
