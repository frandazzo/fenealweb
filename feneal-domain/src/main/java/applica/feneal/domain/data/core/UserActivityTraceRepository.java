package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.UserActivityTrace;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Applica
 * User: Angelo
 * Date: 22/11/17
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserActivityTraceRepository extends Repository<UserActivityTrace> {

    void executeCommand(Command command);

    Session getSession();

}
