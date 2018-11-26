package applica.feneal.admin.viewmodel.inps;

import java.util.List;

public class IncrocioQuoteInps {
    private String title;
    private String rowCost;
    private String retrunPercent;
    private String retrunPercentEdili;
    private String costoTessera;
    private String effectiveCost;
    private List<FileToValidate> inps;
    private List<FileToValidate> csc;


    public String getCostoTessera() {
        return costoTessera;
    }

    public void setCostoTessera(String costoTessera) {
        this.costoTessera = costoTessera;
    }

    public String getEffectiveCost() {
        return effectiveCost;
    }

    public void setEffectiveCost(String effectiveCost) {
        this.effectiveCost = effectiveCost;
    }

    public String getRetrunPercentEdili() {
        return retrunPercentEdili;
    }

    public void setRetrunPercentEdili(String retrunPercentEdili) {
        this.retrunPercentEdili = retrunPercentEdili;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRowCost() {
        return rowCost;
    }

    public void setRowCost(String rowCost) {
        this.rowCost = rowCost;
    }

    public String getRetrunPercent() {
        return retrunPercent;
    }

    public void setRetrunPercent(String retrunPercent) {
        this.retrunPercent = retrunPercent;
    }

    public List<FileToValidate> getInps() {
        return inps;
    }

    public void setInps(List<FileToValidate> inps) {
        this.inps = inps;
    }

    public List<FileToValidate> getCsc() {
        return csc;
    }

    public void setCsc(List<FileToValidate> csc) {
        this.csc = csc;
    }
}
