package applica.feneal.domain.model.core.inps;

import applica.feneal.domain.model.utils.SecuredDomainEntity;

public class InpsFile extends SecuredDomainEntity {
    private String filename;
    private String filepath;
    private boolean csc;
    private RistornoInps ristornoInps;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public boolean isCsc() {
        return csc;
    }

    public void setCsc(boolean csc) {
        this.csc = csc;
    }

    public RistornoInps getRistornoInps() {
        return ristornoInps;
    }

    public void setRistornoInps(RistornoInps ristornoInps) {
        this.ristornoInps = ristornoInps;
    }
}
