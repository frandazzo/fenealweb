package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.FundRepository;
import applica.feneal.domain.data.core.NotificationRepository;
import applica.feneal.domain.model.core.Notification;
import applica.feneal.domain.model.setting.Fondo;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 08/04/2016.
 */
@Repository
public class NotificationHibernateRepository extends HibernateRepository<Notification> implements NotificationRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Notification> getEntityType() {
        return Notification.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("date", false));
    }

}

