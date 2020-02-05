package applica.feneal.services.impl.deleghe;

import applica.feneal.domain.data.core.deleghe.DelegaBariRepository;
import applica.feneal.domain.data.core.ristorniEdilizia.ReferentiRepository;
import applica.feneal.domain.model.core.deleghe.bari.*;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.feneal.domain.model.core.ristorniEdilizia.UiReferenti;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DelegheBariService {

    @Autowired
    private DelegaBariRepository delRep;

    @Autowired
    private ReferentiRepository refRep;


    private DelegaBari findDelegaBari(Long delegaId) {
        DelegaBari d = delRep.find(LoadRequest.build().filter("id",delegaId)).findFirst().orElse(null);
        return d;
    }

    public void addContactForDelegaBari(Long delegaId, String newManagement) {
        DelegaBari d = findDelegaBari(delegaId);
        Referenti r = refRep.find(LoadRequest.build().filter("id",Long.parseLong(newManagement))).findFirst().orElse(null);
        d.setManagementContact(r);
        delRep.save(d);
    }

    public void deleteContactForDelegaBari(Long delegaId) {
        DelegaBari d = findDelegaBari(delegaId);
        d.setManagementContact(null);
        delRep.save(d);
    }

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
                .filter("worker", workerId).sort("protocolDate", true);

        return delRep.find(req).getRows();
    }

    public List<UiReferenti> getAllContactDetilForDelega() {
        List<UiReferenti> refList = new ArrayList<>();

        List<Referenti> aa = refRep.find(null).getRows();
        for( Referenti ref : aa) {
            UiReferenti r = new UiReferenti();
            r.setComune(ref.getCity());
            r.setImportoTot(34);
            r.setNominativo(ref.getCompleteName());

            refList.add(r);
        }

        return refList;
    }
}
