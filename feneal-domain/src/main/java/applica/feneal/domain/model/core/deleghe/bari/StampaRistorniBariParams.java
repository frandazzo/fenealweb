package applica.feneal.domain.model.core.deleghe.bari;

import applica.feneal.domain.model.core.ristorniEdilizia.QuotaAssociativaBari;
import applica.feneal.domain.model.core.ristorniEdilizia.UiReferenti;

import java.util.List;

public class StampaRistorniBariParams {
    private List<QuotaAssociativaBari> listQuote;
    private List<UiReferenti> listReferenti;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UiReferenti> getListReferenti() {
        return listReferenti;
    }

    public void setListReferenti(List<UiReferenti> listReferenti) {
        this.listReferenti = listReferenti;
    }

    public List<QuotaAssociativaBari> getListQuote() {
        return listQuote;
    }

    public void setListQuote(List<QuotaAssociativaBari> listQuote) {
        this.listQuote = listQuote;
    }
}
