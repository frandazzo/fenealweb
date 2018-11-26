package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ProcedureTypeRepository;
import applica.feneal.domain.model.setting.TipoPratica;
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
public class ProcedureTypeHibernateRepository extends HibernateRepository<TipoPratica> implements ProcedureTypeRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<TipoPratica> getEntityType() {
        return TipoPratica.class;
    }

}
