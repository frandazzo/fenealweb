package applica.feneal.data.hibernate.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.RSU.AziendeRSURepository;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by felicegramegna on 22/02/2021.
 */

@Repository
public class AziendeRSUHibernateRepository extends HibernateRepository<AziendaRSU> implements AziendeRSURepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<AziendaRSU> getEntityType() {
        return AziendaRSU.class;
    }
}
