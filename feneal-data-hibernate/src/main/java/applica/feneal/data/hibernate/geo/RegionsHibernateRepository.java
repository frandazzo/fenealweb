package applica.feneal.data.hibernate.geo;

import applica.feneal.domain.data.geo.RegonsRepository;

import applica.feneal.domain.model.geo.Region;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/9/15
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RegionsHibernateRepository extends HibernateRepository<Region> implements RegonsRepository {


    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("description", false));
    }

    @Override
    public Class<Region> getEntityType() {
        return Region.class;
    }


}