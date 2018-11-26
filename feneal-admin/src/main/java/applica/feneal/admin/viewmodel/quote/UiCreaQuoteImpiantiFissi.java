package applica.feneal.admin.viewmodel.quote;

import applica.feneal.domain.model.core.servizi.search.UiQuoteImpiantiFissiSearchParams;

import java.util.List;

public class UiCreaQuoteImpiantiFissi {

    private UiQuoteImpiantiFissiSearchParams params;
    private List<UiLavoratoriQuoteImpiantiFissi> quoteRows;


    public UiQuoteImpiantiFissiSearchParams getParams() {
        return params;
    }

    public void setParams(UiQuoteImpiantiFissiSearchParams params) {
        this.params = params;
    }

    public List<UiLavoratoriQuoteImpiantiFissi> getQuoteRows() {
        return quoteRows;
    }

    public void setQuoteRows(List<UiLavoratoriQuoteImpiantiFissi> quoteRows) {
        this.quoteRows = quoteRows;
    }
}
