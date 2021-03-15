package applica.feneal.data.hibernate.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;

import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by felicegramegna on 08/03/2021.
 */

@Repository
public class ContrattoRSUHibernateRepository extends HibernateRepository<ContrattoRSU> implements ContrattoRSURepository {

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<ContrattoRSU> getEntityType() {
        return ContrattoRSU.class;
    }
}
