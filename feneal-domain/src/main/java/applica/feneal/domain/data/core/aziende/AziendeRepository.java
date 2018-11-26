package applica.feneal.domain.data.core.aziende;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 07/04/2016.
 */
public interface AziendeRepository extends Repository<Azienda> {

    void executeCommand(Command command);

    Session getSession();

}