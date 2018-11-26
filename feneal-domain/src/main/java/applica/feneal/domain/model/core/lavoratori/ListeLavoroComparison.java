package applica.feneal.domain.model.core.lavoratori;

/**
 * Created by fgran on 07/06/2016.
 */
public class ListeLavoroComparison {
    private ListaLavoro intersectionList;
    private ListaLavoro aMinusBList;
    private ListaLavoro bMinusAListr;

    public ListaLavoro getIntersectionList() {
        return intersectionList;
    }

    public void setIntersectionList(ListaLavoro intersectionList) {
        this.intersectionList = intersectionList;
    }

    public ListaLavoro getaMinusBList() {
        return aMinusBList;
    }

    public void setaMinusBList(ListaLavoro aMinusBList) {
        this.aMinusBList = aMinusBList;
    }

    public ListaLavoro getbMinusAListr() {
        return bMinusAListr;
    }

    public void setbMinusAListr(ListaLavoro bMinusAListr) {
        this.bMinusAListr = bMinusAListr;
    }
}
