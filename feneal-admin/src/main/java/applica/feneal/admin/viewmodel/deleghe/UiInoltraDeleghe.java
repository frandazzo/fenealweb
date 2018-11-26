package applica.feneal.admin.viewmodel.deleghe;

import applica.feneal.admin.viewmodel.reports.UiDelega;

import java.util.List;

/**
 * Created by fgran on 16/05/2016.
 */
public class UiInoltraDeleghe {

    private String date;
    private List<UiDelega> selectedDeleghe;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<UiDelega> getSelectedDeleghe() {
        return selectedDeleghe;
    }

    public void setSelectedDeleghe(List<UiDelega> selectedDeleghe) {
        this.selectedDeleghe = selectedDeleghe;
    }
}
