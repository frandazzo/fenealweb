package applica.feneal.domain.model.core.inps;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.Date;

public class QuotaInps extends SecuredDomainEntity {


    //dal file inps
    private Lavoratore lavoratore;
    private double importo;
    private Date dataValuta;
    private String numDomanda;
    private String patronato;

    //calcolati da fenealweb
    private boolean lavoratoreEdile;
    private double importRistornato;

    //dal file csc
    private String referente;
    private Date dataDomanda;
    private RistornoInps ristorno;
    private String cscFilename;
    private String cscFilepath;

    public String getCscFilename() {
        return cscFilename;
    }

    public void setCscFilename(String cscFilename) {
        this.cscFilename = cscFilename;
    }

    public String getCscFilepath() {
        return cscFilepath;
    }

    public void setCscFilepath(String cscFilepath) {
        this.cscFilepath = cscFilepath;
    }

    public Lavoratore getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(Lavoratore lavoratore) {
        this.lavoratore = lavoratore;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public Date getDataValuta() {
        return dataValuta;
    }

    public void setDataValuta(Date dataValuta) {
        this.dataValuta = dataValuta;
    }

    public String getNumDomanda() {
        return numDomanda;
    }

    public void setNumDomanda(String numDomanda) {
        this.numDomanda = numDomanda;
    }

    public String getPatronato() {
        return patronato;
    }

    public void setPatronato(String patronato) {
        this.patronato = patronato;
    }

    public boolean isLavoratoreEdile() {
        return lavoratoreEdile;
    }

    public void setLavoratoreEdile(boolean lavoratoreEdile) {
        this.lavoratoreEdile = lavoratoreEdile;
    }

    public double getImportRistornato() {
        return importRistornato;
    }

    public void setImportRistornato(double importRistornato) {
        this.importRistornato = importRistornato;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    public Date getDataDomanda() {
        return dataDomanda;
    }

    public void setDataDomanda(Date dataDomanda) {
        this.dataDomanda = dataDomanda;
    }

    public RistornoInps getRistorno() {
        return ristorno;
    }

    public void setRistorno(RistornoInps ristorno) {
        this.ristorno = ristorno;
    }

    public void calculateImportoRistornato(RistornoInps ristorno) {

        importRistornato = importo;

        if (ristorno.getPercentualeEffettiva() == 0 || ristorno.getPercentualeEffettiva() == 100)
            importRistornato = importo;
        else{
            double perc = (importo/100)*ristorno.getPercentualeEffettiva();
            importRistornato = Math.round(perc * 100.0) / 100.0;
        }


        if (ristorno.getCostoInpsRiga() > 0)
            importRistornato = importRistornato - ristorno.getCostoInpsRiga();

        if (lavoratoreEdile){

            if (ristorno.getPercentualeRistornoEdili() > 0){
                double perc = (importRistornato/100)*ristorno.getPercentualeRistornoEdili();
                importRistornato = Math.round(perc * 100.0) / 100.0;
            }

        }else{

            if (ristorno.getPercentualeRistorno() > 0){
                double perc = (importRistornato/100)*ristorno.getPercentualeRistorno();
                importRistornato = Math.round(perc * 100.0) / 100.0;
            }
        }

    }
}
