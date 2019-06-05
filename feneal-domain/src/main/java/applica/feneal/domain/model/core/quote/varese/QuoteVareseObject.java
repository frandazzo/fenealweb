package applica.feneal.domain.model.core.quote.varese;

import java.util.ArrayList;
import java.util.List;

public class QuoteVareseObject {
    private List<UiDettaglioQuotaVarese> conNumero = new ArrayList<>();
    private List<UiDettaglioQuotaVarese> senzaNumero = new ArrayList<>();

    public List<UiDettaglioQuotaVarese> getConNumero() {
        return conNumero;
    }

    public void setConNumero(List<UiDettaglioQuotaVarese> conNumero) {
        this.conNumero = conNumero;
    }

    public List<UiDettaglioQuotaVarese> getSenzaNumero() {
        return senzaNumero;
    }

    public void setSenzaNumero(List<UiDettaglioQuotaVarese> senzaNumero) {
        this.senzaNumero = senzaNumero;
    }
}
