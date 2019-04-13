package applica.feneal.domain.data.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.framework.Repository;
import org.hibernate.Session;

public interface LavoratorePrevediRepository extends Repository<LavoratorePrevedi> {

    void executeCommand(Command command);

    Session getSession();
}