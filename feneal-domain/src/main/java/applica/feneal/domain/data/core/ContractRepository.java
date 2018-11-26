package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.Contract;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Applica
 * User: Angelo
 * Date: 31/05/16
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContractRepository extends Repository<Contract> {

    void executeCommand(Command command);

    Session getSession();

}
