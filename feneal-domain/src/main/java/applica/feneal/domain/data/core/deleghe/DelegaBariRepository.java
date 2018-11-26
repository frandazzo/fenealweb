package applica.feneal.domain.data.core.deleghe;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.deleghe.bari.DelegaBari;
import applica.framework.Repository;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;


public interface DelegaBariRepository extends Repository<DelegaBari> {

    void executeCommand(Command command);

    Session getSession();

    List<DelegaBari> getDelegheByLavoratore(long lavoratoreId);

    DelegaBari getDelegaByLavoratoreAndData(long lavoratoreId, Date date);


}
