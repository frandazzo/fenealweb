package applica.feneal.domain.model.core.report;

/**
 * Created by fgran on 01/05/2016.
 */
public class RiepilogoIscrizione {

    private String territorio;
    private String settore;
    private String ente;
    private int anno;
    private int numIscritti;

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
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

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getNumIscritti() {
        return numIscritti;
    }

    public void setNumIscritti(int numIscritti) {
        this.numIscritti = numIscritti;
    }
}
