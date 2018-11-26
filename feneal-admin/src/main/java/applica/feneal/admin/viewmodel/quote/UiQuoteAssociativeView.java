package applica.feneal.admin.viewmodel.quote;

import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;

import java.util.List;

public class UiQuoteAssociativeView {

    private String content;
    private List<RiepilogoQuoteAssociative> quoteAssociative;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<RiepilogoQuoteAssociative> getQuoteAssociative() {
        return quoteAssociative;
    }

    public void setQuoteAssociative(List<RiepilogoQuoteAssociative> quoteAssociative) {
        this.quoteAssociative = quoteAssociative;
    }
}
