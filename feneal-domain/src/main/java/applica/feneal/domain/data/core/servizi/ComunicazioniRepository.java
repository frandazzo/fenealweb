package applica.feneal.domain.data.core.servizi;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.framework.Repository;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by fgran on 28/04/2016.
 */
public interface ComunicazioniRepository extends Repository<Comunicazione> {
    List<Comunicazione> getComunicazioniByLavoratore(long lavoratoreId);

    Session getSession();

    void executeCommand(Command command);
}
