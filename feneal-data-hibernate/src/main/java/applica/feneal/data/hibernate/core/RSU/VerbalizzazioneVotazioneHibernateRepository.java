package applica.feneal.data.hibernate.core.RSU;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.RSU.VerbalizzazioneVotazioneRepository;
import applica.feneal.domain.model.core.RSU.VerbalizzazioneVotazione;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;


@Repository
public class VerbalizzazioneVotazioneHibernateRepository extends HibernateRepository<VerbalizzazioneVotazione> implements VerbalizzazioneVotazioneRepository {
    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<VerbalizzazioneVotazione> getEntityType() {
        return VerbalizzazioneVotazione.class;
    }
}
