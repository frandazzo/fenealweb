package applica.feneal.admin.viewmodel.deleghe;

import java.util.Date;

/**
 * Created by fgran on 16/05/2016.
 */
public class UiDelegaChangeState {

    private String date;
    private String causaleId;
    private String state;
    private  String delegaId;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCausaleId() {
        return causaleId;
    }

    public void setCausaleId(String causaleId) {
        this.causaleId = causaleId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDelegaId() {
        return delegaId;
    }

    public void setDelegaId(String delegaId) {
        this.delegaId = delegaId;
    }
}
