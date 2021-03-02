package applica.feneal.domain.data.core.RSU;

import applica.feneal.domain.data.Command;

import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by felicegramegna on 24/02/2021.
 */
public interface SedeRSURepository extends Repository<SedeRSU> {
    void executeCommand(Command command);

    Session getSession();
}
