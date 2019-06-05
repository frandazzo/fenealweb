package applica.feneal.admin.viewmodel.quote;

public class UiDettaglioQuotaVarese {

    private long id;

    private long idQuota;

    private String provincia;


    //dati lavoratore
    private String lavoratoreNomeCompleto;
    private String lavoratoreCodiceFiscale;
    private String lavoratoreProvinciaResidenza;
    private String lavoratoreComuneResidenza;
    private String lavoratoreCap;
    private String lavoratoreCell;
    private String lavoratoreWrongCell;
    private long lavoratoreId;

    public String getLavoratoreWrongCell() {
        return lavoratoreWrongCell;
    }

    public void setLavoratoreWrongCell(String lavoratoreWrongCell) {
        this.lavoratoreWrongCell = lavoratoreWrongCell;
    }

    private String lavoratoreUltimaComunicazione;
    private String lavoratoreIndirizzo;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdQuota() {
        return idQuota;
    }

    public void setIdQuota(long idQuota) {
        this.idQuota = idQuota;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLavoratoreNomeCompleto() {
        return lavoratoreNomeCompleto;
    }

    public void setLavoratoreNomeCompleto(String lavoratoreNomeCompleto) {
        this.lavoratoreNomeCompleto = lavoratoreNomeCompleto;
    }

    public String getLavoratoreCodiceFiscale() {
        return lavoratoreCodiceFiscale;
    }

    public void setLavoratoreCodiceFiscale(String lavoratoreCodiceFiscale) {
        this.lavoratoreCodiceFiscale = lavoratoreCodiceFiscale;
    }

    public String getLavoratoreProvinciaResidenza() {
        return lavoratoreProvinciaResidenza;
    }

    public void setLavoratoreProvinciaResidenza(String lavoratoreProvinciaResidenza) {
        this.lavoratoreProvinciaResidenza = lavoratoreProvinciaResidenza;
    }

    public String getLavoratoreComuneResidenza() {
        return lavoratoreComuneResidenza;
    }

    public void setLavoratoreComuneResidenza(String lavoratoreComuneResidenza) {
        this.lavoratoreComuneResidenza = lavoratoreComuneResidenza;
    }

    public String getLavoratoreCap() {
        return lavoratoreCap;
    }

    public void setLavoratoreCap(String lavoratoreCap) {
        this.lavoratoreCap = lavoratoreCap;
    }

    public String getLavoratoreCell() {
        return lavoratoreCell;
    }

    public void setLavoratoreCell(String lavoratoreCell) {
        this.lavoratoreCell = lavoratoreCell;
    }

    public long getLavoratoreId() {
        return lavoratoreId;
    }

    public void setLavoratoreId(long lavoratoreId) {
        this.lavoratoreId = lavoratoreId;
    }

    public String getLavoratoreUltimaComunicazione() {
        return lavoratoreUltimaComunicazione;
    }

    public void setLavoratoreUltimaComunicazione(String lavoratoreUltimaComunicazione) {
        this.lavoratoreUltimaComunicazione = lavoratoreUltimaComunicazione;
    }

    public void setLavoratoreIndirizzo(String lavoratoreIndirizzo) {
        this.lavoratoreIndirizzo = lavoratoreIndirizzo;
    }

    public String getLavoratoreIndirizzo() {
        return lavoratoreIndirizzo;
    }
}
