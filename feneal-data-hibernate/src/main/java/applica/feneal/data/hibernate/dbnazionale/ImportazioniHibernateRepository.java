package applica.feneal.data.hibernate.dbnazionale;

import applica.feneal.domain.data.dbnazionale.ImportazioniRepository;
import applica.feneal.domain.model.dbnazionale.Importazione;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Repository
public class ImportazioniHibernateRepository extends HibernateRepository<Importazione> implements ImportazioniRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", true));
    }

    @Override
    public Class<Importazione> getEntityType() {
        return Importazione.class;
    }

}