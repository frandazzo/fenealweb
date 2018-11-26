package applica.feneal.domain.model.core.servizi.search;

/**
 * Created by angelo on 29/04/2016.
 */
public class UiRichiestaReportSearchParams {

    private String datefromMonthReport;
    private String datefromYearReport;
    private String datetoMonthReport;
    private String datetoYearReport;
    private String province;
    private String recipient;
    private String requestToProvince;  //richiesta al territorio di


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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRequestToProvince() {
        return requestToProvince;
    }

    public void setRequestToProvince(String requestToProvince) {
        this.requestToProvince = requestToProvince;
    }
}
