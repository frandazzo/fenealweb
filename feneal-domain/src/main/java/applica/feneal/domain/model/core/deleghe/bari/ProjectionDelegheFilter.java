package applica.feneal.domain.model.core.deleghe.bari;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectionDelegheFilter {
    private Date prevDateStart;
    private Date prevDateEnd;
    private Date lastDateStart;
    private Date lastDateEnd;
    private long companyId;

    private String stringLastDateStart;
    private String stringLastDateEnd;
    private boolean revocheDeleghe;
    private boolean revocheBianche;
    private boolean iscrizioniInefficaci;
    private boolean iscrizioniCongelate;
    private boolean iscrizioniIRiconferma;

    public String getStringLastDateStart() {
        return stringLastDateStart;
    }

    public void setStringLastDateStart(String stringLastDateStart) {
        this.stringLastDateStart = stringLastDateStart;

        if (!StringUtils.isEmpty(stringLastDateStart)){
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            try {
                lastDateStart= f.parse(stringLastDateStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    public String getStringLastDateEnd() {
        return stringLastDateEnd;
    }

    public void setStringLastDateEnd(String stringLastDateEnd) {
        this.stringLastDateEnd = stringLastDateEnd;

        if (!StringUtils.isEmpty(stringLastDateEnd)){
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            try {
                lastDateEnd= f.parse(stringLastDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Date getPrevDateEnd() {
        return prevDateEnd;
    }

    public void setPrevDateEnd(Date prevDateEnd) {
        this.prevDateEnd = prevDateEnd;
    }

    public Date getLastDateStart() {
        return lastDateStart;
    }

    public void setLastDateStart(Date lastDateStart) {
        this.lastDateStart = lastDateStart;
    }

    public Date getLastDateEnd() {
        return lastDateEnd;
    }

    public void setLastDateEnd(Date lastDateEnd) {
        this.lastDateEnd = lastDateEnd;
    }

    public Date getPrevDateStart() {

        return prevDateStart;
    }

    public void setPrevDateStart(Date prevDateStart) {
        this.prevDateStart = prevDateStart;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public void setRevocheDeleghe(boolean revocheDeleghe) {
        this.revocheDeleghe = revocheDeleghe;
    }

    public boolean getRevocheDeleghe() {
        return revocheDeleghe;
    }

    public void setRevocheBianche(boolean revocheBianche) {

        this.revocheBianche = revocheBianche;
    }

    public boolean getRevocheBianche() {
        return revocheBianche;
    }

    public void setIscrizioniInefficaci(boolean iscrizioniInefficaci) {
        this.iscrizioniInefficaci = iscrizioniInefficaci;
    }

    public boolean getIscrizioniInefficaci() {
        return iscrizioniInefficaci;
    }

    public void setIscrizioniCongelate(boolean iscrizioniCongelate) {
        this.iscrizioniCongelate = iscrizioniCongelate;
    }

    public boolean getIscrizioniCongelate() {
        return iscrizioniCongelate;
    }

    public void setIscrizioniIRiconferma(boolean iscrizioniIRiconferma) {
        this.iscrizioniIRiconferma = iscrizioniIRiconferma;
    }

    public boolean getIscrizioniIRiconferma() {
        return iscrizioniIRiconferma;
    }
}
