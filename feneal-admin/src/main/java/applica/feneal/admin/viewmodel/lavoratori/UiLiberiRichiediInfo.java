package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.admin.viewmodel.reports.UiLibero;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;

import java.util.List;

/**
 * Created by fgran on 16/05/2016.
 */
public class UiLiberiRichiediInfo {

    private String province;
    private List<UiLibero> selectedLiberi;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<UiLibero> getSelectedLiberi() {
        return selectedLiberi;
    }

    public void setSelectedLiberi(List<UiLibero> selectedLiberi) {
        this.selectedLiberi = selectedLiberi;
    }
}
