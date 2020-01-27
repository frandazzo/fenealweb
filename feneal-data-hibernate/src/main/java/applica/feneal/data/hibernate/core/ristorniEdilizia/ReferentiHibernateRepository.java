package applica.feneal.data.hibernate.core.ristorniEdilizia;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ristorniEdilizia.ReferentiRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReferentiHibernateRepository extends HibernateRepository<Referenti> implements ReferentiRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Referenti> getEntityType() {
        return Referenti.class;
    }
}
