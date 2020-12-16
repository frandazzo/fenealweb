package applica.feneal.admin.facade;

import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocio;
import applica.feneal.domain.model.dbnazionale.LavoratoreIncrocioNoniscritti;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioNonIscrittiSearchParams;
import applica.feneal.domain.model.dbnazionale.search.ReportIncrocioSearchParams;
import applica.feneal.services.ReportIncrocioService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class IncrocioFacade {

    @Autowired
    private ReportIncrocioService reportIncrocioService;


    public List<LavoratoreIncrocio> retrieveLavoratoriIncrocio(ReportIncrocioSearchParams params) throws ParseException {
        List<LavoratoreIncrocio> result;

        if(!StringUtils.isEmpty(params.getData())){
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(params.getData());
            SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd");
            params.setData(g.format(date1));
        }

        result = reportIncrocioService.retrieveIncrocioProvinciaResidenza(params);

        return result;
    }

    public List<LavoratoreIncrocioNoniscritti> retrieveLavoratoriIncrocioNonIscritti(ReportIncrocioNonIscrittiSearchParams params) throws ParseException {
        List<LavoratoreIncrocioNoniscritti> result;

        if(!StringUtils.isEmpty(params.getData())){
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(params.getData());
            SimpleDateFormat g = new SimpleDateFormat("yyyy-MM-dd");
            params.setData(g.format(date1));
        }

        result = reportIncrocioService.retrieveIncrocioNoniscritti(params);

        return result;
    }
}
