package applica.feneal.admin.viewmodel.reports;

import java.util.List;

/**
 * Created by fgran on 01/06/2016.
 */
public class UiRequestInfoAiTerritori {
    private String province;
    private String destinatario;
    private String htmlLiberi;
    private List<UiLibero> selectedRows;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getHtmlLiberi() {
        return htmlLiberi;
    }

    public void setHtmlLiberi(String htmlLiberi) {
        this.htmlLiberi = htmlLiberi;
    }

    public List<UiLibero> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(List<UiLibero> selectedRows) {
        this.selectedRows = selectedRows;
    }
}
