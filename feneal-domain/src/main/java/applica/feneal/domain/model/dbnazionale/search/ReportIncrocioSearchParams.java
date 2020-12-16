package applica.feneal.domain.model.dbnazionale.search;

public class ReportIncrocioSearchParams {
    private String province;
    private String sector;
    private String data;
    private String includeIscrittiProvincia;
    private String datefromYearReport;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getDatefromYearReport() {
        return datefromYearReport;
    }

    public void setDatefromYearReport(String datefromYearReport) {
        this.datefromYearReport = datefromYearReport;
    }

    public String getIncludeIscrittiProvincia() {
        return includeIscrittiProvincia;
    }

    public void setIncludeIscrittiProvincia(String includeIscrittiProvincia) {
        this.includeIscrittiProvincia = includeIscrittiProvincia;
    }
}
