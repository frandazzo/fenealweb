package applica.feneal.data.hibernate.core.servizi;

import applica.feneal.domain.data.core.servizi.ComunicazioniRepository;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Repository
public class ComunicazioniHibernateRepository extends HibernateRepository<Comunicazione> implements ComunicazioniRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", false));
    }

    @Override
    public Class<Comunicazione> getEntityType() {
        return Comunicazione.class;
    }

}