package applica.feneal.domain.model.core.inps;

import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.beans.Transient;
import java.security.Security;
import java.util.Date;

public class RistornoInps extends SecuredDomainEntity{


    private Date data;
    private String titolo;
    private double percentualeRistorno;
    private double percentualeRistornoEdili;
    private double percentualeEffettiva;
    private double costoInpsRiga;
    private Double costoTessera;

    public double getCostoTessera() {
        return costoTessera;
    }

    public void setCostoTessera(double costoTessera) {
        this.costoTessera = costoTessera;
    }

    public double getPercentualeEffettiva() {
        return percentualeEffettiva;
    }

    public void setPercentualeEffettiva(double percentualeEffettiva) {
        this.percentualeEffettiva = percentualeEffettiva;
    }

    private String log;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public double getPercentualeRistorno() {
        return percentualeRistorno;
    }

    public void setPercentualeRistorno(double percentualeRistorno) {
        this.percentualeRistorno = percentualeRistorno;
    }

    public double getPercentualeRistornoEdili() {
        return percentualeRistornoEdili;
    }

    public void setPercentualeRistornoEdili(double percentualeRistornoEdili) {
        this.percentualeRistornoEdili = percentualeRistornoEdili;
    }

    public double getCostoInpsRiga() {
        return costoInpsRiga;
    }

    public void setCostoInpsRiga(double costoInpsRiga) {
        this.costoInpsRiga = costoInpsRiga;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
