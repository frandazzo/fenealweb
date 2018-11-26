package applica.feneal.admin.viewmodel.app.dashboard.aziende;

/**
 * Created by fgran on 06/09/2016.
 */
public class NonIscrittoAzienda {

    private String nome;
    private String fiscale;
    private String provincia;
    private String ente;
    private String liberoAl;
    private String azienda;
    private String iscrittoA;
    private int numIscrizioni;
    private String badge;

    public boolean isDelegheOwner() {
        return delegheOwner;
    }

    public void setDelegheOwner(boolean delegheOwner) {
        this.delegheOwner = delegheOwner;
    }

    private boolean delegheOwner;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getLiberoAl() {
        return liberoAl;
    }

    public void setLiberoAl(String liberoAl) {
        this.liberoAl = liberoAl;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getIscrittoA() {
        return iscrittoA;
    }

    public void setIscrittoA(String iscrittoA) {
        this.iscrittoA = iscrittoA;
    }

    public void setNumIscrizioni(int numIscrizioni) {
        this.numIscrizioni = numIscrizioni;
    }

    public int getNumIscrizioni() {
        return numIscrizioni;
    }


    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getBadge() {
        return badge;
    }
}
