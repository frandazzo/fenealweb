package applica.feneal.domain.model.core;

import applica.framework.AEntity;

/**
 * Created by angelo on 14/11/2017.
 */
public class TraceLogin extends AEntity {

    private int counterApp;
    private int counterWebsite;
    private int year;
    private String month;
    private String company;


    public int getCounterApp() {
        return counterApp;
    }

    public void setCounterApp(int counterApp) {
        this.counterApp = counterApp;
    }

    public int getCounterWebsite() {
        return counterWebsite;
    }

    public void setCounterWebsite(int counterWebsite) {
        this.counterWebsite = counterWebsite;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
