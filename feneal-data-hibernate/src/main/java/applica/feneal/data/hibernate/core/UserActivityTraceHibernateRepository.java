package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.UserActivityTraceRepository;
import applica.feneal.domain.model.core.UserActivityTrace;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Applica
 * User: Angelo
 * Date: 22/11/17
 * Time: 19:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserActivityTraceHibernateRepository extends HibernateRepository<UserActivityTrace> implements UserActivityTraceRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<UserActivityTrace> getEntityType() {
        return UserActivityTrace.class;
    }

}
