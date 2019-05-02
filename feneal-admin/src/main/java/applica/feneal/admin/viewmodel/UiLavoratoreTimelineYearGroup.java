package applica.feneal.admin.viewmodel;

import java.util.List;

public class UiLavoratoreTimelineYearGroup {

    private int anno;

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public List<UiLavoratoreTimeLineItem> getItem() {
        return item;
    }

    public void setItem(List<UiLavoratoreTimeLineItem> item) {
        this.item = item;
    }

    private List<UiLavoratoreTimeLineItem> item;



}
