package applica.feneal.domain.model.core.inps;

public class PagamentoReferenti {
    private double totale;
    private double totaleRistornato;
    private int numeroLavoratori = 0;
    private int numeroLavoratoriEdili = 0;
    private int numeroLavoratoriNonEdili= 0;
    private double ristornoConCostoTessereSoloEdili= 0;
    private double ristornoConCostoTessereSoloNonEdili= 0;
    private double ristornoConCostoTessere= 0;


    public int getNumeroLavoratori() {
        return numeroLavoratori;
    }

    public void setNumeroLavoratori(int numeroLavoratori) {
        this.numeroLavoratori = numeroLavoratori;
    }

    public int getNumeroLavoratoriEdili() {
        return numeroLavoratoriEdili;
    }

    public void setNumeroLavoratoriEdili(int numeroLavoratoriEdili) {
        this.numeroLavoratoriEdili = numeroLavoratoriEdili;
    }

    public int getNumeroLavoratoriNonEdili() {
        return numeroLavoratoriNonEdili;
    }

    public void setNumeroLavoratoriNonEdili(int numeroLavoratoriNonEdili) {
        this.numeroLavoratoriNonEdili = numeroLavoratoriNonEdili;
    }

    public double getRistornoConCostoTessereSoloEdili() {
        return ristornoConCostoTessereSoloEdili;
    }

    public void setRistornoConCostoTessereSoloEdili(double ristornoConCostoTessereSoloEdili) {
        this.ristornoConCostoTessereSoloEdili = ristornoConCostoTessereSoloEdili;
    }

    public double getRistornoConCostoTessereSoloNonEdili() {
        return ristornoConCostoTessereSoloNonEdili;
    }

    public void setRistornoConCostoTessereSoloNonEdili(double ristornoConCostoTessereSoloNonEdili) {
        this.ristornoConCostoTessereSoloNonEdili = ristornoConCostoTessereSoloNonEdili;
    }

    public double getRistornoConCostoTessere() {
        return ristornoConCostoTessere;
    }

    public void setRistornoConCostoTessere(double ristornoConCostoTessere) {
        this.ristornoConCostoTessere = ristornoConCostoTessere;
    }

    private String referente;


    public double getTotaleRistornato() {
        return totaleRistornato;
    }

    public void setTotaleRistornato(double totaleRistornato) {
        this.totaleRistornato = totaleRistornato;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }
}
