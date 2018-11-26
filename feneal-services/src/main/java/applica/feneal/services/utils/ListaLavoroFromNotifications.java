package applica.feneal.services.utils;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgran on 10/06/2016.
 */
public class ListaLavoroFromNotifications {

    private List<Lavoratore> known = new ArrayList<>();
    private List<Lavoratore> unknown= new ArrayList<>();
    private List<Lavoratore> withoutdelega= new ArrayList<>();

    public List<Lavoratore> getKnown() {
        return known;
    }

    public void setKnown(List<Lavoratore> known) {
        this.known = known;
    }

    public List<Lavoratore> getUnknown() {
        return unknown;
    }

    public void setUnknown(List<Lavoratore> unknown) {
        this.unknown = unknown;
    }

    public List<Lavoratore> getWithoutdelega() {
        return withoutdelega;
    }

    public void setWithoutdelega(List<Lavoratore> withoutdelega) {
        this.withoutdelega = withoutdelega;
    }
}
