package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;

import java.util.List;

/**
 * Created by fgran on 01/06/2016.
 */
public class UiListaLavoroSendSms {

    private List<Lavoratore> lavoratori;
    private String province;
    private String campagna;
    private String text;


    public List<Lavoratore> getLavoratori() {
        return lavoratori;
    }

    public void setLavoratori(List<Lavoratore> lavoratori) {
        this.lavoratori = lavoratori;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCampagna() {
        return campagna;
    }

    public void setCampagna(String campagna) {
        this.campagna = campagna;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
