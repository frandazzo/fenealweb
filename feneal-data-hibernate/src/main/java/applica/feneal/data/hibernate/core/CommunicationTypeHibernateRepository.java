package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.CommunicationTypeRepository;
import applica.feneal.domain.model.setting.TipoComunicazione;
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
public class CommunicationTypeHibernateRepository extends HibernateRepository<TipoComunicazione> implements CommunicationTypeRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<TipoComunicazione> getEntityType() {
        return TipoComunicazione.class;
    }

}
