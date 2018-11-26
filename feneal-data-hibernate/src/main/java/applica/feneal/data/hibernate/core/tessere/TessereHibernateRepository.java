package applica.feneal.data.hibernate.core.tessere;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.tessere.TessereRepository;
import applica.feneal.domain.model.core.tessere.Tessera;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fgran on 03/06/2016.
 */
@Repository
public class TessereHibernateRepository extends HibernateRepository<Tessera> implements TessereRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Tessera> getEntityType() {
        return Tessera.class;
    }

}
