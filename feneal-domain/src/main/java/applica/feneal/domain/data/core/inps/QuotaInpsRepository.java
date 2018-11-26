package applica.feneal.domain.data.core.inps;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.inps.InpsFile;
import applica.feneal.domain.model.core.inps.QuotaInps;
import applica.framework.Repository;
import org.hibernate.Session;

public interface QuotaInpsRepository extends Repository<QuotaInps> {

    void executeCommand(Command command);

    Session getSession();

}