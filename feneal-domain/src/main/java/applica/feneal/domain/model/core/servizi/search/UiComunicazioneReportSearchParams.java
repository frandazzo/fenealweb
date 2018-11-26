package applica.feneal.domain.model.core.servizi.search;

/**
 * Created by angelo on 29/04/2016.
 */
public class UiComunicazioneReportSearchParams {

    private String datefromMonthReport;
    private String datefromYearReport;
    private String datetoMonthReport;
    private String datetoYearReport;
    private String typeCommunication;
    private String causaleComunicazione;
    private String province;


    public String getDatefromMonthReport() {
        return datefromMonthReport;
    }

    public void setDatefromMonthReport(String datefromMonthReport) {
        this.datefromMonthReport = datefromMonthReport;
    }

    public String getDatefromYearReport() {
        return datefromYearReport;
    }

    public void setDatefromYearReport(String datefromYearReport) {
        this.datefromYearReport = datefromYearReport;
    }

    public String getDatetoMonthReport() {
        return datetoMonthReport;
    }

    public void setDatetoMonthReport(String datetoMonthReport) {
        this.datetoMonthReport = datetoMonthReport;
    }

    public String getDatetoYearReport() {
        return datetoYearReport;
    }

    public void setDatetoYearReport(String datetoYearReport) {
        this.datetoYearReport = datetoYearReport;
    }

    public String getTypeCommunication() {
        return typeCommunication;
    }

    public void setTypeCommunication(String typeCommunication) {
        this.typeCommunication = typeCommunication;
    }

    public String getCausaleComunicazione() {
        return causaleComunicazione;
    }

    public void setCausaleComunicazione(String causaleComunicazione) {
        this.causaleComunicazione = causaleComunicazione;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
