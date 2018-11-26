package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CollaboratorRepository;
import applica.feneal.domain.model.setting.Collaboratore;
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
public class CollaboratorHibernateRepository extends HibernateRepository<Collaboratore> implements CollaboratorRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Collaboratore> getEntityType() {
        return Collaboratore.class;
    }

}
