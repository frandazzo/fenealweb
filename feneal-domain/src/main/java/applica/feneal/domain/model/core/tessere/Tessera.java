package applica.feneal.domain.model.core.tessere;

import applica.framework.AEntity;

import java.util.Date;

/**
 * Created by fgran on 03/06/2016.
 */
public class Tessera extends AEntity {

    private Date date;
    private String printedFrom;
    private int year;
    private long companyId;
    private String fiscalCode;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
}
