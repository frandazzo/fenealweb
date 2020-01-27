package applica.feneal.domain.data.core.ristorniEdilizia;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.ristorniEdilizia.Ristorno;
import applica.framework.Repository;
import org.hibernate.Session;

public interface RistornoRepository extends Repository<Ristorno> {

    void executeCommand(Command command);

    Session getSession();
}
