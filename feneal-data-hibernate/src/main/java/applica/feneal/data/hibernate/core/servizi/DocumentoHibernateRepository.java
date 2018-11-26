package applica.feneal.data.hibernate.core.servizi;

import applica.feneal.domain.data.core.servizi.DocumentiRepository;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
@Repository
public class DocumentoHibernateRepository  extends HibernateRepository<Documento> implements DocumentiRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("data", false));
    }

    @Override
    public Class<Documento> getEntityType() {
        return Documento.class;
    }

}