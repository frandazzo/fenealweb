package applica.feneal.admin.facade;


import applica.feneal.domain.data.core.ristorniEdilizia.RistornoItemRepository;
import applica.feneal.domain.data.core.ristorniEdilizia.RistornoRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.*;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StoricoRistorniBariFacade {

    @Autowired
    private RistornoRepository risRep;

    @Autowired
    private RistornoItemRepository risItemRep;

    public List<Ristorno> getRistorniBari() {
        return risRep.find(LoadRequest.build()).getRows();
    }

    public RistornoBariObject getDettaglioRistorno(long id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RistornoBariObject ristornoObj = new RistornoBariObject();
        Ristorno r = risRep.find(LoadRequest.build().filter("id",id, Filter.EQ)).findFirst().orElse(null);

        List<QuotaAssociativaBari> quote = mapper.readValue(r.getQuoteserialaized(), new TypeReference<List<QuotaAssociativaBari>>(){});

//        List<QuotaAssociativaBari> quote = new ArrayList<>();

        List<RistornoItem> items = risItemRep.find(LoadRequest.build().filter("idRistorno",id, Filter.EQ)).getRows();

        List<UiReferenti> referenti = convertItemsToUiRefrenti(items);

        ristornoObj.setListaQuote(quote);
        ristornoObj.setListaReferenti(referenti);

        return ristornoObj;

    }

    private List<UiReferenti> convertItemsToUiRefrenti(List<RistornoItem> items) throws IOException {
        List<UiReferenti> list = new ArrayList<>();
        for(RistornoItem i : items){
            UiReferenti r = new UiReferenti();
            r.setNominativo(i.getReferente().getCompleteName());
            r.setComune(i.getReferente().getCity());
            r.setImportoTot(i.getImportoTot());
            ObjectMapper mapper = new ObjectMapper();
            List<QuotaAssociativaBari> quote = mapper.readValue(i.getListQuote(), new TypeReference<List<QuotaAssociativaBari>>(){});
            r.setListQuote(quote);

            list.add(r);
        }

        return list;
    }
}
