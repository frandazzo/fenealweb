package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;

import java.util.List;

public class RistornoBariObject {
    private List<UiReferenti> listaReferenti;
    private List<QuotaAssociativaBari> listaQuote;

    public List<UiReferenti> getListaReferenti() {
        return listaReferenti;
    }

    public void setListaReferenti(List<UiReferenti> listaReferenti) {
        this.listaReferenti = listaReferenti;
    }

    public List<QuotaAssociativaBari> getListaQuote() {
        return listaQuote;
    }

    public void setListaQuote(List<QuotaAssociativaBari> listaQuote) {
        this.listaQuote = listaQuote;
    }
}
