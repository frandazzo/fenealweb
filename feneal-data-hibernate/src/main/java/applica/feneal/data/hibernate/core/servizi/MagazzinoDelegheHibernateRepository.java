package applica.feneal.data.hibernate.core.servizi;

import applica.feneal.domain.data.core.servizi.MagazzinoDelegheRepository;
import applica.feneal.domain.model.core.servizi.MagazzinoDelega;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Repository
public class MagazzinoDelegheHibernateRepository extends HibernateRepository<MagazzinoDelega> implements MagazzinoDelegheRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", false));
    }

    @Override
    public Class<MagazzinoDelega> getEntityType() {
        return MagazzinoDelega.class;
    }

}