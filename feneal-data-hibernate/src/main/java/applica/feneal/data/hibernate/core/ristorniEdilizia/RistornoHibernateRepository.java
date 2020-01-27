package applica.feneal.data.hibernate.core.ristorniEdilizia;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ristorniEdilizia.RistornoRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.Ristorno;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RistornoHibernateRepository extends HibernateRepository<Ristorno> implements RistornoRepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Ristorno> getEntityType() {
        return Ristorno.class;
    }
}
