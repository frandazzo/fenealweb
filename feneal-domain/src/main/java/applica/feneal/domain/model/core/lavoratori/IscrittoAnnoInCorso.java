package applica.feneal.domain.model.core.lavoratori;

/**
 * Created by fgran on 03/06/2016.
 */
public class IscrittoAnnoInCorso {
    private boolean iscritto;
    private String settore;
    private String ente;
    private String azienda;
    private String periodo;


    public boolean iscritto() {
        return iscritto;
    }

    public void setIscritto(boolean iscritto) {
        this.iscritto = iscritto;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
