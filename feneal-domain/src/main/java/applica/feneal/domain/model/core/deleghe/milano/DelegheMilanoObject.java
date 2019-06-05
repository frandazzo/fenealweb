package applica.feneal.domain.model.core.deleghe.milano;

import applica.feneal.domain.model.setting.Collaboratore;

import java.util.ArrayList;
import java.util.List;

public class DelegheMilanoObject {
    private List<DelegaMilano> conCodici = new ArrayList<>();
    private List<DelegaMilano> senzaCodici = new ArrayList<>();
    private List<DelegaMilano> lavImported = new ArrayList<>();
    private List<Collaboratore> collaboratori = new ArrayList<>();


    public List<DelegaMilano> getLavImported() {
        return lavImported;
    }

    public void setLavImported(List<DelegaMilano> lavImported) {
        this.lavImported = lavImported;
    }

    public List<DelegaMilano> getConCodici() {
        return conCodici;
    }

    public void setConCodici(List<DelegaMilano> conCodici) {
        this.conCodici = conCodici;
    }

    public List<DelegaMilano> getSenzaCodici() {
        return senzaCodici;
    }

    public void setSenzaCodici(List<DelegaMilano> senzaCodici) {
        this.senzaCodici = senzaCodici;
    }

    public List<Collaboratore> getCollaboratori() {
        return collaboratori;
    }

    public void setCollaboratori(List<Collaboratore> collaboratori) {
        this.collaboratori = collaboratori;
    }
}
