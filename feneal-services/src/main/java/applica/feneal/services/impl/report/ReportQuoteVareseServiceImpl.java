package applica.feneal.services.impl.report;

import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.feneal.domain.model.core.quote.UiQuoteVareseReportSearchParams;
import applica.feneal.services.ReportQuoteVareseService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Service
public class ReportQuoteVareseServiceImpl implements ReportQuoteVareseService {


    @Autowired
    private DettaglioQuoteAssociativeRepository dettRep;


    @Override
    public List<DettaglioQuotaAssociativa> retrieveQuoteVarese(UiQuoteVareseReportSearchParams params) {


        String quota2= params.getQuota2();
        String quota1= params.getQuota1();

        LoadRequest req = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota1()));
        List<DettaglioQuotaAssociativa> list1 =  dettRep.find(req).getRows();

        if(!StringUtils.isEmpty(quota2)) {
            LoadRequest req2 = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota2()));
            List<DettaglioQuotaAssociativa>list2 = dettRep.find(req2).getRows();

            if(list2.size() > list1.size()) {
                return intersectLists(list2, list1);
            }
            else{
                return intersectLists(list1, list2);
            }
        }

        return list1;
    }

    private List<DettaglioQuotaAssociativa> intersectLists(List<DettaglioQuotaAssociativa> list1, List<DettaglioQuotaAssociativa> list2) {
        List<DettaglioQuotaAssociativa> listApp = new ArrayList<>();
        for (DettaglioQuotaAssociativa lav2 : list2) {

            DettaglioQuotaAssociativa l = list1.stream()
                    .filter(a -> lav2.getIdLavoratore() == a.getIdLavoratore())
                    .findFirst()
                    .orElse(null);

            if ( l != null)
                    listApp.add(l);

        }
        list1.removeAll(listApp);
        return list1;
    }
}


//   if(!StringUtils.isEmpty(quota1)) {
//            Filter f1= new Filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota1()));
//            req.getFilters().add(f1);
////            LoadRequest req2 = LoadRequest.build().filter("idRiepilogoQuotaAssociativa", Long.parseLong(params.getQuota2()));
////            List<DettaglioQuotaAssociativa> q2 = dettRep.find(req2).getRows();
//        }
