package applica.feneal.data.hibernate.core.inps;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.inps.InpsFileRepository;
import applica.feneal.domain.data.core.inps.QuotaInpsRepository;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.feneal.domain.model.core.inps.QuotaInps;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuotaInpsHibernateRepository extends HibernateRepository<QuotaInps> implements QuotaInpsRepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<QuotaInps> getEntityType() {
        return QuotaInps.class;
    }
}
