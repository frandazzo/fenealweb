package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.admin.viewmodel.reports.UiLibero;

import java.util.List;

/**
 * Created by fgran on 01/06/2016.
 */
public class UiRequestInfoWorker {
    private String requestToProvince;
    private String destinatario;
    private String note;


    public String getRequestToProvince() {
        return requestToProvince;
    }

    public void setRequestToProvince(String requestToProvince) {
        this.requestToProvince = requestToProvince;
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
}
