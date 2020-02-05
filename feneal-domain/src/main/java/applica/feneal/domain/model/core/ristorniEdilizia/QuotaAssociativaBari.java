package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.List;

public class QuotaAssociativaBari {
    private Lavoratore lavoratore;
    private List<DelegaBari> delegheBari;
    private float quotaAssoc;

    public DelegaBari getUltimaDelega() {
        return ultimaDelega;
    }

    public void setUltimaDelega(DelegaBari ultimaDelega) {
        this.ultimaDelega = ultimaDelega;
    }

    private DelegaBari ultimaDelega;

    public Lavoratore getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(Lavoratore lavoratore) {
        this.lavoratore = lavoratore;
    }

    public List<DelegaBari> getDelegheBari() {
        return delegheBari;
    }

    public void setDelegheBari(List<DelegaBari> delegheBari) {
        this.delegheBari = delegheBari;
    }

    public float getQuotaAssoc() {
        return quotaAssoc;
    }

    public void setQuotaAssoc(float quotaAssoc) {
        this.quotaAssoc = quotaAssoc;
    }
}
