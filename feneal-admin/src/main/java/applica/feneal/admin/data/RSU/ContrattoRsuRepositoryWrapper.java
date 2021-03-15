package applica.feneal.admin.data.RSU;

import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.domain.validation.RSU.ContrattoRsuValidator;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by felicegramegna on 08/03/2021.
 */

@org.springframework.stereotype.Repository
public class ContrattoRsuRepositoryWrapper implements Repository<ContrattoRSU> {

    @Autowired
    private ContrattoRSURepository contrattoRSURepository;

    @Autowired
    private ContrattoRsuValidator contrattoRsuValidator;


    @Override
    public Optional<ContrattoRSU> get(Object id) {
        return contrattoRSURepository.get(id);
    }

    @Override
    public LoadResponse<ContrattoRSU> find(LoadRequest request) {
        final LoadRequest finalRequest = request;

        return contrattoRSURepository.find(finalRequest);
    }

    @Override
    public void save(ContrattoRSU contrattoRSU) {
        //per salvare poi usìtilizzo i service e mi controllo che tutto sia valido
        if(!StringUtils.isEmpty(contrattoRSU.getDescription())){
            contrattoRSURepository.save(contrattoRSU);
        }

    }

    @Override
    public void delete(Object id) {
        contrattoRSURepository.delete(id);
    }

    @Override
    public Class<ContrattoRSU> getEntityType() {
        return contrattoRSURepository.getEntityType();
    }
}
