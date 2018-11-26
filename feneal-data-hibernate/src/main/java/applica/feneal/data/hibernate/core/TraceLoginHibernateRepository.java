package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.TraceLoginRepository;
import applica.feneal.domain.model.core.TraceLogin;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Applica
 * User: Angelo
 * Date: 14/11/17
 * Time: 17:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class TraceLoginHibernateRepository extends HibernateRepository<TraceLogin> implements TraceLoginRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<TraceLogin> getEntityType() {
        return TraceLogin.class;
    }

}
