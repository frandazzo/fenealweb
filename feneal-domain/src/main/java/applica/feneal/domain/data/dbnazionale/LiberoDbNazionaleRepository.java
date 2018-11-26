package applica.feneal.domain.data.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 11/05/2016.
 */
public interface LiberoDbNazionaleRepository extends Repository<LiberoDbNazionale> {

    void executeCommand(Command command);

    Session getSession();
}
