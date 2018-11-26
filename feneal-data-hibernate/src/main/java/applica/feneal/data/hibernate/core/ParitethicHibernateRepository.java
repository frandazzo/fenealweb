package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ParitethicHibernateRepository extends HibernateRepository<Paritethic> implements ParitheticRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Paritethic> getEntityType() {
        return Paritethic.class;
    }

}
