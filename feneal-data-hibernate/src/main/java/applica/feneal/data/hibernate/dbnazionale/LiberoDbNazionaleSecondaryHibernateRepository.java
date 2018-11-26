package applica.feneal.data.hibernate.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleRepository;
import applica.feneal.domain.data.dbnazionale.LiberoDbNazionaleSecondaryRepository;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionaleSecondary;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 18/07/2017.
 */
@Repository
public class LiberoDbNazionaleSecondaryHibernateRepository extends HibernateRepository<LiberoDbNazionaleSecondary> implements LiberoDbNazionaleSecondaryRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("cognome", false));
    }

    @Override
    public Class<LiberoDbNazionaleSecondary> getEntityType() {
        return LiberoDbNazionaleSecondary.class;
    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }
}