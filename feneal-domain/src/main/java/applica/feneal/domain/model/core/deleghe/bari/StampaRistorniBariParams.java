package applica.feneal.domain.model.core.deleghe.bari;

import applica.feneal.domain.model.core.ristorniEdilizia.QuotaAssociativaBari;

import java.util.List;

public class StampaRistorniBariParams {
    private List<QuotaAssociativaBari> listQuote;

    public List<QuotaAssociativaBari> getListQuote() {
        return listQuote;
    }

    public void setListQuote(List<QuotaAssociativaBari> listQuote) {
        this.listQuote = listQuote;
    }
}
