package applica.feneal.data.hibernate.geo;

import applica.feneal.domain.data.geo.ProvinceRepository;

import applica.feneal.domain.model.geo.Province;
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
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProvincesHibernateRepository extends HibernateRepository<Province> implements ProvinceRepository {


    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("description", false));
    }

    @Override
    public Class<Province> getEntityType() {
        return Province.class;
    }


}