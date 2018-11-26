package applica.feneal.admin.viewmodel.app.dashboard.lavoratori;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
public class UiLavoratori {

    private List<Lavoratore> lavoratori;

    public List<Lavoratore> getLavoratori() {
        return lavoratori;
    }

    public void setLavoratori(List<Lavoratore> lavoratori) {
        this.lavoratori = lavoratori;
    }
}
