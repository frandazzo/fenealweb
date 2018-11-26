package applica.feneal.domain.data.core.inps;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.framework.Repository;
import org.hibernate.Session;

public interface InpsFileRepository extends Repository<InpsFile> {

    void executeCommand(Command command);

    Session getSession();

}
