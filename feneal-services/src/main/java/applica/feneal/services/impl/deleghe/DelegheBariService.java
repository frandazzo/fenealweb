package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.data.core.deleghe.DelegaBariRepository;
import applica.feneal.domain.model.core.deleghe.bari.*;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DelegheBariService {

    @Autowired
    private DelegaBariRepository delRep;



    public List<DelegaBari> findDelegheBari(Date start, Date end, long workerCompanyId){
        LoadRequest req = LoadRequest.build()
                .filter("protocolDate", start, Filter.GTE)
                .filter("protocolDate", end, Filter.LTE);


        if (workerCompanyId > 0){
            Filter f = new Filter();
            f.setProperty("workerCompany");
            f.setValue(workerCompanyId);
            req.getFilters().add(f);
        }

        //req.getSorts().add(new Sort("protocoldate", true));

        return delRep.find(req).getRows();

    }




    public ReportDelegheBari ConstructReportDelegheBari(List<DelegaBari> deleghe){
        ReportDelegheBari p = new ReportDelegheBari(StatoDelegheFactory.createDelegheStes(deleghe));
        return  p;
    }

    public ReportProiezioneDelegheDari ProiezioniDelegheBari(ProjectionDelegheFilter filter){
        List<DelegaBari>  prev = findDelegheBari(filter.getPrevDateStart(), filter.getPrevDateEnd(),0);
        List<DelegaBari>  last = findDelegheBari(filter.getLastDateStart(), filter.getLastDateEnd(), 0);

        ReportDelegheBari rpt = ConstructReportDelegheBari(prev);



        ReportProiezioneDelegheDari p = new ReportProiezioneDelegheDari(StatoDelegheFactory.createDelegheProjections(rpt.getDeleghe(),last, filter));
        return p;

    }


    public List<DelegaBari> getAllWorkerDeleghe(long workerId) {
        LoadRequest req = LoadRequest.build()
                .filter("worker", workerId);

        return delRep.find(req).getRows();
    }
}
