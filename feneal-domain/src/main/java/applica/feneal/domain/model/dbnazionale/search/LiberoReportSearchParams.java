package applica.feneal.domain.model.dbnazionale.search;

/**
 * Created by fgran on 15/04/2016.
 */
public class LiberoReportSearchParams {

    private String province;
//    private String datefromMonthReport;
//    private String datefromYearReport;
//    private String datetoMonthReport;
//    private String datetoYearReport;
    private String signedTo;
    private String parithetic;
    private String nationality;
    private String livingProvince;
    private String livingCity;
    private String firm;


    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

//    public String getDatefromMonthReport() {
//        return datefromMonthReport;
//    }
//
//    public void setDatefromMonthReport(String datefromMonthReport) {
//        this.datefromMonthReport = datefromMonthReport;
//    }
//
//    public String getDatefromYearReport() {
//        return datefromYearReport;
//    }
//
//    public void setDatefromYearReport(String datefromYearReport) {
//        this.datefromYearReport = datefromYearReport;
//    }
//
//    public String getDatetoMonthReport() {
//        return datetoMonthReport;
//    }
//
//    public void setDatetoMonthReport(String datetoMonthReport) {
//        this.datetoMonthReport = datetoMonthReport;
//    }
//
//    public String getDatetoYearReport() {
//        return datetoYearReport;
//    }
//
//    public void setDatetoYearReport(String datetoYearReport) {
//        this.datetoYearReport = datetoYearReport;
//    }

    public String getSignedTo() {
        return signedTo;
    }

    public void setSignedTo(String signedTo) {
        this.signedTo = signedTo;
    }

    public String getParithetic() {
        return parithetic;
    }

    public void setParithetic(String parithetic) {
        this.parithetic = parithetic;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLivingProvince() {
        return livingProvince;
    }

    public void setLivingProvince(String livingProvince) {
        this.livingProvince = livingProvince;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }
}
