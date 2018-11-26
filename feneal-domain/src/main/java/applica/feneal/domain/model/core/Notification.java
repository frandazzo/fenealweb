package applica.feneal.domain.model.core;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.utils.SecuredDomainEntity;
import applica.framework.AEntity;

import java.util.Date;

/**
 * Created by fgran on 08/04/2016.
 */
public class Notification extends AEntity {

    public static final String notification_newexport = "Aggiornamento db nazionale";
    public static final String notification_sendworkerinfo = "Segnalazione lavoratore";

    private Date date;
    private String description;
    private String briefDescription;

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    private String type;
    private boolean read;

    private String body;
    //identificativo del territorio a cui è diretta
    private long recipientCompanyId;
    private User sender;


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getRecipientCompanyId() {
        return recipientCompanyId;
    }

    public void setRecipientCompanyId(long recipientCompanyId) {
        this.recipientCompanyId = recipientCompanyId;
    }






    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
