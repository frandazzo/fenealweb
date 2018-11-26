package applica.feneal.admin.viewmodel.app.dashboard.lavoratori;

/**
 * Created by fgran on 06/09/2016.
 */
public class IscrittoLavoratore {

    private boolean showChevron;

    private String provincia;
    private String settore;
    private String ente;
    private int periodo;
    private int anno;
    private String azienda;
    private String piva;
    private String livello;
    private String quota;
    private String contratto;
    private String fiscale;
    private String nome;
    private String stringPeriodo;


    public boolean getShowChevron() {
        return showChevron;
    }

    public void setShowChevron(boolean showChevron) {
        this.showChevron = showChevron;
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

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
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

    public void setFiscale(String fiscale) {
        this.fiscale = fiscale;
    }

    public String getFiscale() {
        return fiscale;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setStringPeriodo(String stringPeriodo) {
        this.stringPeriodo = stringPeriodo;
    }

    public String getStringPeriodo() {
        return stringPeriodo;
    }
}
