package applica.feneal.domain.model.core.report;

/**
 * Created by fgran on 01/05/2016.
 */
public class Sindacalizzaizone {

    private int iscrittiFeneal;
    private int iscrittiFilca;
    private int iscrittiFillea;
    private int liberi;

    /* todo per ciccio: non servono piú per il grafico */
    private double percIscrittiFeneal;
    private double percIscrittiFilca;
    private double percIscrittiFillea;
    private double percIiberi;

    private double tassoSindacalizzazione;

    private String provincia;
    private String ente;


    public String getProvincia() {
        return provincia;
    }

    public void setProbvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public int getIscrittiFeneal() {
        return iscrittiFeneal;
    }

    public void setIscrittiFeneal(int iscrittiFeneal) {
        this.iscrittiFeneal = iscrittiFeneal;
    }

    public int getIscrittiFilca() {
        return iscrittiFilca;
    }

    public void setIscrittiFilca(int iscrittiFilca) {
        this.iscrittiFilca = iscrittiFilca;
    }

    public int getIscrittiFillea() {
        return iscrittiFillea;
    }

    public void setIscrittiFillea(int iscrittiFillea) {
        this.iscrittiFillea = iscrittiFillea;
    }

    public int getLiberi() {
        return liberi;
    }

    public void setLiberi(int liberi) {
        this.liberi = liberi;
    }

    public double getPercIscrittiFeneal() {
        return percIscrittiFeneal;
    }

    public void setPercIscrittiFeneal(double percIscrittiFeneal) {
        this.percIscrittiFeneal = percIscrittiFeneal;
    }

    public double getPercIscrittiFilca() {
        return percIscrittiFilca;
    }

    public void setPercIscrittiFilca(double percIscrittiFilca) {
        this.percIscrittiFilca = percIscrittiFilca;
    }

    public double getPercIscrittiFillea() {
        return percIscrittiFillea;
    }

    public void setPercIscrittiFillea(double percIscrittiFillea) {
        this.percIscrittiFillea = percIscrittiFillea;
    }

    public double getPercIiberi() {
        return percIiberi;
    }

    public void setPercIiberi(double percIiberi) {
        this.percIiberi = percIiberi;
    }

    public double getTassoSindacalizzazione() {
        return tassoSindacalizzazione;
    }

    public void setTassoSindacalizzazione(double tassoSindacalizzazione) {
        this.tassoSindacalizzazione = tassoSindacalizzazione;
    }
}
