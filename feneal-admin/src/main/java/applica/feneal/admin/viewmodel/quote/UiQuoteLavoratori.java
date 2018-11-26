package applica.feneal.admin.viewmodel.quote;

/**
 * Created by angelo on 30/05/16.
 */
public class UiQuoteLavoratori {

    private long lavoratoreId;
    private String lavoratoreNomeCompleto;
    private String azienda;
    private double importo;
    private String contratto;


    public long getLavoratoreId() {
        return lavoratoreId;
    }

    public void setLavoratoreId(long lavoratoreId) {
        this.lavoratoreId = lavoratoreId;
    }

    public String getLavoratoreNomeCompleto() {
        return lavoratoreNomeCompleto;
    }

    public void setLavoratoreNomeCompleto(String lavoratoreNomeCompleto) {
        this.lavoratoreNomeCompleto = lavoratoreNomeCompleto;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public String getContratto() {
        return contratto;
    }

    public void setContratto(String contratto) {
        this.contratto = contratto;
    }
}
