package applica.feneal.data.hibernate.dbnazionale;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.dbnazionale.LavoratorePrevediRepository;
import applica.feneal.domain.model.dbnazionale.LavoratorePrevedi;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class LavoratorePrevediHibernateRepository extends HibernateRepository<LavoratorePrevedi> implements LavoratorePrevediRepository {

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("surname", false));
    }

    @Override
    public Class<LavoratorePrevedi> getEntityType() {
        return LavoratorePrevedi.class;
    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }
}