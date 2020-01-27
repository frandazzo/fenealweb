package applica.feneal.data.hibernate.core.ristorniEdilizia;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ristorniEdilizia.RistornoItemRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.RistornoItem;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RistornoItemHibernateRepository extends HibernateRepository<RistornoItem> implements RistornoItemRepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<RistornoItem> getEntityType() {
        return RistornoItem.class;
    }
}
