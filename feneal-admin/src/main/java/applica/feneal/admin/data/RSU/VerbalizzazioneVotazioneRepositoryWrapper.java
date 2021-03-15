package applica.feneal.admin.data.RSU;

import applica.feneal.domain.data.core.RSU.VerbalizzazioneVotazioneRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.core.RSU.VerbalizzazioneVotazione;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by felicegramegna on 12/03/2021.
 */

@org.springframework.stereotype.Repository
public class VerbalizzazioneVotazioneRepositoryWrapper implements Repository<VerbalizzazioneVotazione> {

    @Autowired
    private VerbalizzazioneVotazioneRepository verVotRep;


    @Override
    public Optional<VerbalizzazioneVotazione> get(Object id) {
        return verVotRep.get(id);
    }

    @Override
    public LoadResponse<VerbalizzazioneVotazione> find(LoadRequest request) {
        final LoadRequest finalRequest = request;
        return verVotRep.find(request);
    }

    @Override
    public void save(VerbalizzazioneVotazione verbalizzazioneVotazione) {
        //verVotRep.save(verbalizzazioneVotazione);
    }

    @Override
    public void delete(Object id) {
        verVotRep.delete(id);
    }

    @Override
    public Class<VerbalizzazioneVotazione> getEntityType() {
        return verVotRep.getEntityType();
    }
}
