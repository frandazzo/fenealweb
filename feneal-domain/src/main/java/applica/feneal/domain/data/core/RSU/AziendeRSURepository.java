package applica.feneal.domain.data.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by felicegramegna on 22/02/2021.
 */
public interface AziendeRSURepository extends Repository<AziendaRSU> {
    void executeCommand(Command command);

    Session getSession();
}
