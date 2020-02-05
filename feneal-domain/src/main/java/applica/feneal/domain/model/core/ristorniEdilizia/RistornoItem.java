package applica.feneal.domain.model.core.ristorniEdilizia;

import applica.framework.AEntity;



public class RistornoItem extends AEntity {

    private long idRistorno;
    private Referenti referente;
    private float importoTot;
    private String listQuote;

    public String getListQuote() {
        return listQuote;
    }

    public void setListQuote(String listQuote) {
        this.listQuote = listQuote;
    }

    public long getIdRistorno() {
        return idRistorno;
    }

    public void setIdRistorno(long idRistorno) {
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
