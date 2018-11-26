package applica.feneal.data.hibernate.core.inps;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.inps.InpsFileRepository;
import applica.feneal.domain.data.core.inps.RistornoInpsRepository;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.feneal.domain.model.core.inps.RistornoInps;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RistornoInpsHibernateRepository extends HibernateRepository<RistornoInps> implements RistornoInpsRepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<RistornoInps> getEntityType() {
        return RistornoInps.class;
    }
}
