package applica.feneal.admin.viewmodel.app.dashboard.report;

/**
 * Created by fgran on 15/09/2016.
 */
public class AppReportIscritti {

private boolean showChevron = true;

    private String completeName;
    private String fiscale;
    private String provincia;
    private String settore;
    private String ente;
    private String azienda;
    private String data;
    private String dataRegistrazione;
    private String tipo;
    private String competenza;
    private String livello;
    private String quota;
    private String contratto;

    //numero di risultati denormalizzato
    private int results;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public boolean isShowChevron() {
        return showChevron;
    }

    public void setShowChevron(boolean showChevron) {
        this.showChevron = showChevron;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getFiscale() {
        return fiscale;
    }

    public void setFiscale(String fiscale) {
        this.fiscale = fiscale;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(String dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCompetenza() {
        return competenza;
    }

    public void setCompetenza(String competenza) {
        this.competenza = competenza;
    }

    public String getLivello() {
        return livello;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }
}
