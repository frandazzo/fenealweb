package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.VisibleFunction;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VisibleFunctionRepository extends Repository<VisibleFunction> {

    void executeCommand(Command command);

    Session getSession();

}
