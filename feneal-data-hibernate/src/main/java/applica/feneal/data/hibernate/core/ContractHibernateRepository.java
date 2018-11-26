package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ContractRepository;
import applica.feneal.domain.model.core.Contract;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Applica
 * User: Angelo
 * Date: 31/05/16
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ContractHibernateRepository extends HibernateRepository<Contract> implements ContractRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Contract> getEntityType() {
        return Contract.class;
    }

}
