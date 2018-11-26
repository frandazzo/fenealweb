package applica.feneal.admin.viewmodel.inps;

import applica.feneal.domain.model.core.inps.InpsFile;
import applica.feneal.domain.model.core.inps.PagamentoReferenti;
import applica.feneal.domain.model.core.inps.QuotaInps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RistornoInpsSummary {

    private long id;
    private String titolo;
    private Date data;
    private String percentualeRistorno;
    private String percentualeRistornoEdili;
    private String costoInpsRiga;
    private String costoTessera;

    public String getCostoTessera() {
        return costoTessera;
    }

    public void setCostoTessera(String costoTessera) {
        this.costoTessera = costoTessera;
    }

    private List<PagamentoReferenti> pagamentiReferenti = new ArrayList<>();
    private List<FileToValidate> inpsFiles = new ArrayList<>();
    private List<FileToValidate> cscFiles = new ArrayList<>();
    private List<QuotaInpsDTO> quote = new ArrayList<>();
    private List<QuotaInpsDTO> quotePagate = new ArrayList<>();
    private double percentualeEffettiva;

    public List<QuotaInpsDTO> getQuotePagate() {
        return quotePagate;
    }

    public void setQuotePagate(List<QuotaInpsDTO> quotePagate) {
        this.quotePagate = quotePagate;
    }

    public List<PagamentoReferenti> getPagamentiReferenti() {
        return pagamentiReferenti;
    }

    public void setPagamentiReferenti(List<PagamentoReferenti> pagamentiReferenti) {
        this.pagamentiReferenti = pagamentiReferenti;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPercentualeRistorno() {
        return percentualeRistorno;
    }

    public void setPercentualeRistorno(String percentualeRistorno) {
        this.percentualeRistorno = percentualeRistorno;
    }

    public String getPercentualeRistornoEdili() {
        return percentualeRistornoEdili;
    }

    public void setPercentualeRistornoEdili(String percentualeRistornoEdili) {
        this.percentualeRistornoEdili = percentualeRistornoEdili;
    }

    public String getCostoInpsRiga() {
        return costoInpsRiga;
    }

    public void setCostoInpsRiga(String costoInpsRiga) {
        this.costoInpsRiga = costoInpsRiga;
    }

    public List<FileToValidate> getInpsFiles() {
        return inpsFiles;
    }

    public void setInpsFiles(List<FileToValidate> inpsFiles) {
        this.inpsFiles = inpsFiles;
    }

    public List<FileToValidate> getCscFiles() {
        return cscFiles;
    }

    public void setCscFiles(List<FileToValidate> cscFiles) {
        this.cscFiles = cscFiles;
    }

    public List<QuotaInpsDTO> getQuote() {
        return quote;
    }

    public void setQuote(List<QuotaInpsDTO> quote) {
        this.quote = quote;
    }

    public void setPercentualeEffettiva(double percentualeEffettiva) {
        this.percentualeEffettiva = percentualeEffettiva;
    }

    public double getPercentualeEffettiva() {
        return percentualeEffettiva;
    }
}
