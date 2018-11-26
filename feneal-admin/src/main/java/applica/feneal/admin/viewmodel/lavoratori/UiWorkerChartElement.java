package applica.feneal.admin.viewmodel.lavoratori;

import java.util.List;

/**
 * Created by fgran on 19/05/2016.
 */
public class UiWorkerChartElement {
    //coordinata x nel grafico
    private int x;
    //coordinata y nel grafico
    private int y;
    //lista dei settori  di iscrizione
    private List<String> settori;
    //id della provincia di iscrizione
    private int value;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<String> getSettori() {
        return settori;
    }

    public void setSettori(List<String> settori) {
        this.settori = settori;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
