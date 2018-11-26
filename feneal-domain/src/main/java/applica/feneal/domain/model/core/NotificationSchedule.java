package applica.feneal.domain.model.core;

import applica.framework.AEntity;
import applica.framework.LEntity;

import java.util.Date;

/**
 * Created by fgran on 10/02/2017.
 */
public class NotificationSchedule extends AEntity {

    private Date scheduledDate;
    private boolean executed;
    private int importId;


    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }
}
