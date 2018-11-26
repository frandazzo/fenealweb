package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.RevocationReasonRepository;
import applica.feneal.domain.model.setting.CausaleRevoca;
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
public class RevocationReasonHibernateRepository extends HibernateRepository<CausaleRevoca> implements RevocationReasonRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<CausaleRevoca> getEntityType() {
        return CausaleRevoca.class;
    }

}
