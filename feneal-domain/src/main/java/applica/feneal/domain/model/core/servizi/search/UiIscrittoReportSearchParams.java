package applica.feneal.domain.model.core.servizi.search;

/**
 * Created by fgran on 15/04/2016.
 */
public class UiIscrittoReportSearchParams {

    private String province;
    private String datefromMonthReport;
    private String datefromYearReport;
    private String datetoMonthReport;
    private String datetoYearReport;
    private String typeQuoteCash;
    private String sector;
    private boolean delegationActiveExist;
    private String parithetic;
    private String firm;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

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

    public String getTypeQuoteCash() {
        return typeQuoteCash;
    }

    public void setTypeQuoteCash(String typeQuoteCash) {
        this.typeQuoteCash = typeQuoteCash;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public boolean isDelegationActiveExist() {
        return delegationActiveExist;
    }

    public void setDelegationActiveExist(boolean delegationActiveExist) {
        this.delegationActiveExist = delegationActiveExist;
    }

    public String getParithetic() {
        return parithetic;
    }

    public void setParithetic(String parithetic) {
        this.parithetic = parithetic;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

}
