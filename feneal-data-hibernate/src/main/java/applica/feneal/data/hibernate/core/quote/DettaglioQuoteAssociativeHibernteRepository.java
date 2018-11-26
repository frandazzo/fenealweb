package applica.feneal.data.hibernate.core.quote;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.quote.DettaglioQuoteAssociativeRepository;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.framework.data.hibernate.HibernateRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 * Created by fgran on 20/05/2016.
 */
@Repository
public class DettaglioQuoteAssociativeHibernteRepository extends HibernateRepository<DettaglioQuotaAssociativa> implements DettaglioQuoteAssociativeRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }



    @Override
    public Class<DettaglioQuotaAssociativa> getEntityType() {
        return DettaglioQuotaAssociativa.class;
    }

}
