package applica.feneal.admin.viewmodel.lavoratori;

import java.util.List;

/**
 * Created by fgran on 06/04/2016.
 */
public class UiCompleteLavoratoreSummary {

    public UiLavoratoreAnagraficaSummary getData() {
        return data;
    }

    public void setData(UiLavoratoreAnagraficaSummary data) {
        this.data = data;
    }

    public UiIscrittoAnnoInCorso getIscrittoData() {
        return iscrittoData;
    }

    public void setIscrittoData(UiIscrittoAnnoInCorso iscrittoData) {
        this.iscrittoData = iscrittoData;
    }

    public UiPrintedTessera getTesseraData() {
        return tesseraData;
    }

    public void setTesseraData(UiPrintedTessera tesseraData) {
        this.tesseraData = tesseraData;
    }

    public List<UiPrintedTessera> getOtherTessereData() {
        return otherTessereData;
    }

    public void setOtherTessereData(List<UiPrintedTessera> otherTessereData) {
        this.otherTessereData = otherTessereData;
    }

    private UiLavoratoreAnagraficaSummary data;

    private UiIscrittoAnnoInCorso iscrittoData;

    private UiPrintedTessera tesseraData;

    private List<UiPrintedTessera> otherTessereData;


}
