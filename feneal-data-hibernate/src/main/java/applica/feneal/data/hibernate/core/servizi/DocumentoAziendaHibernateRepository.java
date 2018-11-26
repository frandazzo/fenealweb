package applica.feneal.data.hibernate.core.servizi;

import applica.feneal.domain.data.core.servizi.DocumentiAziendaRepository;
import applica.feneal.domain.model.core.servizi.DocumentoAzienda;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Repository
public class DocumentoAziendaHibernateRepository extends HibernateRepository<DocumentoAzienda> implements DocumentiAziendaRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", false));
    }

    @Override
    public Class<DocumentoAzienda> getEntityType() {
        return DocumentoAzienda.class;
    }

}