package applica.feneal.domain.data.core.ristorniEdilizia;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.ristorniEdilizia.RistornoItem;
import applica.framework.Repository;
import org.hibernate.Session;

public interface RistornoItemRepository extends Repository<RistornoItem> {

    void executeCommand(Command command);

    Session getSession();
}
