package applica.feneal.admin.viewmodel.lavoratori;

import applica.feneal.domain.model.core.Company;

import java.util.Date;

/**
 * Created by fgran on 03/06/2016.
 */
public class UiPrintedTessera {

    private String date;
    private String printedFrom;
    private int year;
    private Company company;
    private String fiscalCode;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrintedFrom() {
        return printedFrom;
    }

    public void setPrintedFrom(String printedFrom) {
        this.printedFrom = printedFrom;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
}
