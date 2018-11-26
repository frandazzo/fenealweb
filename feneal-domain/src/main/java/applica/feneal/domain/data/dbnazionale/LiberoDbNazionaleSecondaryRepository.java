package applica.feneal.domain.data.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionaleSecondary;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 18/07/2017.
 */
public interface LiberoDbNazionaleSecondaryRepository extends Repository<LiberoDbNazionaleSecondary> {

    void executeCommand(Command command);

    Session getSession();
}
