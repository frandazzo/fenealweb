package applica.feneal.admin.viewmodel.app.dashboard.report;

/**
 * Created by fgran on 15/09/2016.
 */
public class AppReportNonIscritti {
//    nome: 'Francesco Randazzo',
//    fiscale: 'rndfnc77l14f052f',
//    provincia: 'Bolzano',
//    ente: 'CASSA EDILE',
//    liberoAl: '31/12/2016',
//    azienda: 'Alla Spa',
//    iscrittoA: 'FILLEA',
    private String nome;
    private String fiscale;
    private String provincia;
    private String ente;
    private String liberoAl;
    private String azienda;
    private String iscrittoA;
    private String badge;
    //numero di risultati denormalizzato
    private int results;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

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

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
