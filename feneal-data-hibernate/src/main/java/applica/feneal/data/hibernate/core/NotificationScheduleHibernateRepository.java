package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.NotificationScheduleRepository;
import applica.feneal.domain.model.core.NotificationSchedule;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fgran on 10/02/2017.
 */
@Repository
public class NotificationScheduleHibernateRepository extends HibernateRepository<NotificationSchedule> implements NotificationScheduleRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<NotificationSchedule> getEntityType() {
        return NotificationSchedule.class;
    }
}
