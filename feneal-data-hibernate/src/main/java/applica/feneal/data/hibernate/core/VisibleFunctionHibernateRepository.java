package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.VisibleFunctionRepository;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.VisibleFunction;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class VisibleFunctionHibernateRepository extends HibernateRepository<VisibleFunction> implements VisibleFunctionRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<VisibleFunction> getEntityType() {
        return VisibleFunction.class;
    }

}
