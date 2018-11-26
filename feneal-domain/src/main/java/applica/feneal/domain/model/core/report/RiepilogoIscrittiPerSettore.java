package applica.feneal.domain.model.core.report;

/**
 * Created by fgran on 01/05/2016.
 */
public class RiepilogoIscrittiPerSettore {

    private String settore;

    private int iscrittiEdili;
    private int iscrittiImpiantiFissi;
    private int iscrittiInps;

    private double percIscrittiEdili;
    private double percIscrittiImpiantiFissi;
    private double percIscrittiInps;


    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public int getIscrittiEdili() {
        return iscrittiEdili;
    }

    public void setIscrittiEdili(int iscrittiEdili) {
        this.iscrittiEdili = iscrittiEdili;
    }

    public int getIscrittiImpiantiFissi() {
        return iscrittiImpiantiFissi;
    }

    public void setIscrittiImpiantiFissi(int iscrittiImpiantiFissi) {
        this.iscrittiImpiantiFissi = iscrittiImpiantiFissi;
    }

    public int getIscrittiInps() {
        return iscrittiInps;
    }

    public void setIscrittiInps(int iscrittiInps) {
        this.iscrittiInps = iscrittiInps;
    }

    public double getPercIscrittiEdili() {
        return percIscrittiEdili;
    }

    public void setPercIscrittiEdili(double percIscrittiEdili) {
        this.percIscrittiEdili = percIscrittiEdili;
    }

    public double getPercIscrittiImpiantiFissi() {
        return percIscrittiImpiantiFissi;
    }

    public void setPercIscrittiImpiantiFissi(double percIscrittiImpiantiFissi) {
        this.percIscrittiImpiantiFissi = percIscrittiImpiantiFissi;
    }

    public double getPercIscrittiInps() {
        return percIscrittiInps;
    }

    public void setPercIscrittiInps(double percIscrittiInps) {
        this.percIscrittiInps = percIscrittiInps;
    }
}
