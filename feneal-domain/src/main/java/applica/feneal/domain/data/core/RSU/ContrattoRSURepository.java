package applica.feneal.domain.data.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by felicegramegna on 08/03/2021.
 */
public interface ContrattoRSURepository extends Repository<ContrattoRSU> {
    void executeCommand(Command command);

    Session getSession();
}
