package applica.feneal.domain.data.core.quote;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.quote.DettaglioQuotaAssociativa;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 20/05/2016.
 */
public interface DettaglioQuoteAssociativeRepository extends Repository<DettaglioQuotaAssociativa> {

    void executeCommand(Command command);

    Session getSession();



}