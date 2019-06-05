package applica.feneal.domain.model.core.quote.varese;

import java.util.List;

public class SmsQuoteVarese {
    private String message;
    private List<UiDettaglioQuotaVarese> quote;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UiDettaglioQuotaVarese> getQuote() {
        return quote;
    }

    public void setQuote(List<UiDettaglioQuotaVarese> quote) {
        this.quote = quote;
    }
}
