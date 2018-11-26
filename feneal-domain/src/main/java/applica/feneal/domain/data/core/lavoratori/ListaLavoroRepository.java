package applica.feneal.domain.data.core.lavoratori;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.lavoratori.ListaLavoro;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 04/06/2016.
 */
public interface ListaLavoroRepository extends Repository<ListaLavoro> {

    void executeCommand(Command command);

    Session getSession();

    Long getNumberOfListForWorker( long workerId);

}
