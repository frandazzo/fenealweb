package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.NotificationSchedule;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 10/02/2017.
 */
public interface NotificationScheduleRepository  extends Repository<NotificationSchedule> {

    void executeCommand(Command command);

    Session getSession();

}
