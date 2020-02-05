package applica.feneal.admin.viewmodel.ristornibari;

import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.domain.model.core.ristorniEdilizia.RistornoBariObject;

import java.util.List;

public class UiDettaglioRistornoBari {

    private String content;
    private RistornoBariObject ristornoBariObject;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RistornoBariObject getRistornoBariObject() {
        return ristornoBariObject;
    }

    public void setRistornoBariObject(RistornoBariObject ristornoBariObject) {
        this.ristornoBariObject = ristornoBariObject;
    }
}
