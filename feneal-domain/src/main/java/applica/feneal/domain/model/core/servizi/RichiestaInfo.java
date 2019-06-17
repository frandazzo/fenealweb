package applica.feneal.domain.model.core.servizi;

import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.geo.Province;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

import java.util.Date;

/**
 * Created by fgran on 28/04/2016.
 */
public class RichiestaInfo extends SecuredDomainEntity {

    private Date data;
    private String destinatario;
    private String note;
    private Province province;
    private String requestToProvince;
    private Lavoratore lavoratore;
    private boolean sendMail;

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getRequestToProvince() {
        return requestToProvince;
    }

    public void setRequestToProvince(String requestToProvince) {
        this.requestToProvince = requestToProvince;
    }

    public Lavoratore getLavoratore() {
        return lavoratore;
    }

    public void setLavoratore(Lavoratore lavoratore) {
        this.lavoratore = lavoratore;
    }
}
