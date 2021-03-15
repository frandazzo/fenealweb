package applica.feneal.domain.data.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.RSU.VerbalizzazioneVotazione;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by felicegramegna on 11/03/2021.
 */
public interface VerbalizzazioneVotazioneRepository extends Repository<VerbalizzazioneVotazione> {
    void executeCommand(Command command);

    Session getSession();
}
