package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.List;

public class UiDettaglioListaLavoroComparisonView {

    private String content;
    private List<Lavoratore> lavoratoriLista_aMinusB;
    private List<Lavoratore> lavoratoriLista_bMinusA;
    private List<Lavoratore> lavoratoriLista_intersection;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Lavoratore> getLavoratoriLista_aMinusB() {
        return lavoratoriLista_aMinusB;
    }

    public void setLavoratoriLista_aMinusB(List<Lavoratore> lavoratoriLista_aMinusB) {
        this.lavoratoriLista_aMinusB = lavoratoriLista_aMinusB;
    }

    public List<Lavoratore> getLavoratoriLista_bMinusA() {
        return lavoratoriLista_bMinusA;
    }

    public void setLavoratoriLista_bMinusA(List<Lavoratore> lavoratoriLista_bMinusA) {
        this.lavoratoriLista_bMinusA = lavoratoriLista_bMinusA;
    }

    public List<Lavoratore> getLavoratoriLista_intersection() {
        return lavoratoriLista_intersection;
    }

    public void setLavoratoriLista_intersection(List<Lavoratore> lavoratoriLista_intersection) {
        this.lavoratoriLista_intersection = lavoratoriLista_intersection;
    }
}
