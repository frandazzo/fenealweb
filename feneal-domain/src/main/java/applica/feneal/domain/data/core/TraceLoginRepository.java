package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.TraceLogin;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Applica
 * User: Angelo
 * Date: 14/11/17
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface TraceLoginRepository extends Repository<TraceLogin> {

    void executeCommand(Command command);

    Session getSession();

}
