package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.framework.AEntity;



public class RistornoItem extends AEntity {

    private Ristorno idRistorno;
    private Referenti referente;
    private float importoTot;

    public Ristorno getIdRistorno() {
        return idRistorno;
    }

    public void setIdRistorno(Ristorno idRistorno) {
        this.idRistorno = idRistorno;
    }

    public Referenti getReferente() {
        return referente;
    }

    public void setReferente(Referenti referente) {
        this.referente = referente;
    }

    public float getImportoTot() {
        return importoTot;
    }

    public void setImportoTot(float importoTot) {
        this.importoTot = importoTot;
    }
}
