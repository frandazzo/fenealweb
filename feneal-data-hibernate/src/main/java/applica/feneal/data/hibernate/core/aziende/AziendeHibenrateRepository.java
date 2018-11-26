package applica.feneal.data.hibernate.core.aziende;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
@Repository
public class AziendeHibenrateRepository extends HibernateRepository<Azienda> implements AziendeRepository {

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<Azienda> getEntityType() {
        return Azienda.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("description", false));
    }


    @Override
    public LoadResponse<Azienda> find(LoadRequest loadRequest) {
        return super.find(loadRequest);
    }
}