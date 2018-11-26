package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.Notification;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 08/04/2016.
 */

public interface NotificationRepository  extends Repository<Notification> {

        void executeCommand(Command command);

        Session getSession();

        }
