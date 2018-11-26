package applica.feneal.domain.model.core.quote;

/**
 * Created by fgran on 15/09/2017.
 */
public class UpdatableDettaglioQuota {

    private Double quota;
    private String livello;
    private String note;


    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
