package applica.feneal.data.hibernate.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleRepository;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 11/05/2016.
 */
@Repository
public class LiberoDBNazionaleHibernateRepository  extends HibernateRepository<LiberoDbNazionale> implements LiberoDbNazionaleRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("cognome", false));
    }

    @Override
    public Class<LiberoDbNazionale> getEntityType() {
        return LiberoDbNazionale.class;
    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }
}