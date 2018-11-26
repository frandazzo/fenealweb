package applica.feneal.domain.model.setting;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.utils.SecuredDomainEntity;

/**
 * Created by fgran on 05/04/2016.
 */
public class TipoPratica  extends SecuredDomainEntity {

    private String description;
    private boolean restricted;
    private User jollyUser;
    private User controlPraticaUser;
    private User integrationPraticaUser;
    private User confirmPraticaUser;
    private User rejectedPraticaUser;
    private User closedPraticaUser;


    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public User getJollyUser() {
        return jollyUser;
    }

    public void setJollyUser(User jollyUser) {
        this.jollyUser = jollyUser;
    }

    public User getControlPraticaUser() {
        return controlPraticaUser;
    }

    public void setControlPraticaUser(User controlPraticaUser) {
        this.controlPraticaUser = controlPraticaUser;
    }

    public User getIntegrationPraticaUser() {
        return integrationPraticaUser;
    }

    public void setIntegrationPraticaUser(User integrationPraticaUser) {
        this.integrationPraticaUser = integrationPraticaUser;
    }

    public User getConfirmPraticaUser() {
        return confirmPraticaUser;
    }

    public void setConfirmPraticaUser(User confirmPraticaUser) {
        this.confirmPraticaUser = confirmPraticaUser;
    }

    public User getRejectedPraticaUser() {
        return rejectedPraticaUser;
    }

    public void setRejectedPraticaUser(User rejectedPraticaUser) {
        this.rejectedPraticaUser = rejectedPraticaUser;
    }

    public User getClosedPraticaUser() {
        return closedPraticaUser;
    }

    public void setClosedPraticaUser(User closedPraticaUser) {
        this.closedPraticaUser = closedPraticaUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

