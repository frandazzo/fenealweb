package applica.feneal.admin.facade;

import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocioNoniscritti;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioNonIscrittiSearchParams;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioSearchParams;
import applica.feneal.services.ReportIncrocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncrocioFacade {

    @Autowired
    private ReportIncrocioService reportIncrocioService;


    public List<LavoratoreIncrocio> retrieveLavoratoriIncrocio(ReportIncrocioSearchParams params) {
        List<LavoratoreIncrocio> result;

        result = reportIncrocioService.retrieveIncrocioProvinciaResidenza(params);

        return result;
    }

    public List<LavoratoreIncrocioNoniscritti> retrieveLavoratoriIncrocioNonIscritti(ReportIncrocioNonIscrittiSearchParams params) {
        List<LavoratoreIncrocioNoniscritti> result;

        result = reportIncrocioService.retrieveIncrocioNoniscritti(params);

        return result;
    }
}
