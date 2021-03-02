package applica.feneal.data.hibernate.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.RSU.SedeRSURepository;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SedeRSUHibernateRepository extends HibernateRepository<SedeRSU> implements SedeRSURepository {
    @Override
    public void executeCommand(Command command)  {
        command.execute();
    }

    @Override
    public Class<SedeRSU> getEntityType() {
        return SedeRSU.class;
    }
}
