package applica.feneal.domain.data.core.ristorniEdilizia;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.framework.Repository;
import org.hibernate.Session;

public interface ReferentiRepository extends Repository<Referenti> {

    void executeCommand(Command command);

    Session getSession();
}
