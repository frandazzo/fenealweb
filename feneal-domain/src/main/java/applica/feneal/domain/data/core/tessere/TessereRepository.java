package applica.feneal.domain.data.core.tessere;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.tessere.Tessera;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 03/06/2016.
 */
public interface TessereRepository  extends Repository<Tessera> {

    void executeCommand(Command command);

    Session getSession();

}