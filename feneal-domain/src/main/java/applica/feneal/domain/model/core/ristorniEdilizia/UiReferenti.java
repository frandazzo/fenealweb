package applica.feneal.domain.model.core.ristorniEdilizia;

public class UiReferenti {
    private String nominativo;
    private String comune;
    private float importoTot;

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
