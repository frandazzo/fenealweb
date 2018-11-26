package applica.feneal.data.hibernate.core.servizi;

import applica.feneal.domain.data.core.servizi.MagazzinoDelegheLecceRepository;
import applica.feneal.domain.model.core.servizi.MagazzinoDelegaLecce;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MagazzinoDelegheLecceHibernateRepository extends HibernateRepository<MagazzinoDelegaLecce> implements MagazzinoDelegheLecceRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", false));
    }

    @Override
    public Class<MagazzinoDelegaLecce> getEntityType() {
        return MagazzinoDelegaLecce.class;
    }

}