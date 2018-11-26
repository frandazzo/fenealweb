package applica.feneal.data.hibernate.core.servizi;

import applica.feneal.domain.data.core.servizi.RichiesteInfoRepository;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Repository
public class RichiesteInfoHibernateRepository extends HibernateRepository<RichiestaInfo> implements RichiesteInfoRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", false));
    }

    @Override
    public Class<RichiestaInfo> getEntityType() {
        return RichiestaInfo.class;
    }

}
