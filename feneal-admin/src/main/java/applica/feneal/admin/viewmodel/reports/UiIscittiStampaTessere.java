package applica.feneal.admin.viewmodel.reports;

import java.util.List;

/**
 * Created by angelo on 10/06/2016.
 */
public class UiIscittiStampaTessere {

    private List<UiIscritto> rows;
    private String province;
    private String sector;
    private boolean onlyWithoutTessera;
    private boolean global;


    public List<UiIscritto> getRows() {
        return rows;
    }

    public void setRows(List<UiIscritto> rows) {
        this.rows = rows;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public boolean isOnlyWithoutTessera() {
        return onlyWithoutTessera;
    }

    public void setOnlyWithoutTessera(boolean onlyWithoutTessera) {
        this.onlyWithoutTessera = onlyWithoutTessera;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }
}
