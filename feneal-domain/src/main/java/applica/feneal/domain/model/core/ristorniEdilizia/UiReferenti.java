package applica.feneal.domain.model.core.ristorniEdilizia;

import java.util.List;

public class UiReferenti {
    private String nominativo;
    private String comune;
    private float importoTot;
    private List<QuotaAssociativaBari> listQuote;


    public List<QuotaAssociativaBari> getListQuote() {
        return listQuote;
    }

    public void setListQuote(List<QuotaAssociativaBari> listQuote) {
        this.listQuote = listQuote;
    }

    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public float getImportoTot() {
        return importoTot;
    }

    public void setImportoTot(float importoTot) {
        this.importoTot = importoTot;
    }
}
