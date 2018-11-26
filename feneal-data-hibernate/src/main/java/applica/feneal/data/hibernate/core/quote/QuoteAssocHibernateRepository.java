package applica.feneal.data.hibernate.core.quote;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.quote.QuoteAssocRepository;
import applica.feneal.domain.model.core.quote.RiepilogoQuoteAssociative;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class QuoteAssocHibernateRepository extends HibernateRepository<RiepilogoQuoteAssociative> implements QuoteAssocRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<RiepilogoQuoteAssociative> getEntityType() {
        return RiepilogoQuoteAssociative.class;
    }

}
