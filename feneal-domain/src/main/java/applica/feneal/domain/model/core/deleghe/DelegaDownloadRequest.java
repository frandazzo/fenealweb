package applica.feneal.domain.model.core.deleghe;

import applica.feneal.domain.model.User;
import applica.framework.AEntity;

import java.util.Date;

public class DelegaDownloadRequest extends AEntity {


    private Delega delega;
    private String requestId;
    private User applicant;
    private long applicantCompanyId;
    private Date date;
    private boolean authorized;

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public long getApplicantCompanyId() {
        return applicantCompanyId;
    }

    public void setApplicantCompanyId(long applicantCompanyId) {
        this.applicantCompanyId = applicantCompanyId;
    }

    public Delega getDelega() {
        return delega;
    }

    public void setDelega(Delega delega) {
        this.delega = delega;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
