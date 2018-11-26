package applica.feneal.data.hibernate.core.inps;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.inps.InpsFileRepository;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InpsFileHibernateRepository extends HibernateRepository<InpsFile> implements InpsFileRepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<InpsFile> getEntityType() {
        return InpsFile.class;
    }
}
