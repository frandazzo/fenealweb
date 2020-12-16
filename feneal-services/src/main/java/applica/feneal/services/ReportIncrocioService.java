package applica.feneal.services;

import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocioNoniscritti;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioNonIscrittiSearchParams;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioSearchParams;

import java.util.List;

public interface ReportIncrocioService {
    List<LavoratoreIncrocio> retrieveIncrocioProvinciaResidenza(ReportIncrocioSearchParams params);

    List<LavoratoreIncrocioNoniscritti> retrieveIncrocioNoniscritti(ReportIncrocioNonIscrittiSearchParams params);
}
