package applica.feneal.services.impl.RSU;

import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.domain.validation.RSU.ContrattoRsuValidator;
import applica.feneal.services.RSU.ContrattoRsuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by felicegramegna on 09/03/2021.
 */
@Service
public class ContrattoRsuServiceImpl implements ContrattoRsuService {

    @Autowired
    private ContrattoRSURepository contrattoRSURepository;

    @Autowired
    private ContrattoRsuValidator contrattoRsuValidator;

    @Override
    public void delete(long lid, long id) {
        contrattoRSURepository.delete(id);
    }

    @Override
    public ContrattoRSU getContrattoRsuById(long loggedUserId, Long conId) {
        if(conId == null)
            return null;

        return contrattoRSURepository.get(conId).orElse(null);
    }

    @Override
    public void saveOrUpdate(long loggedUserId, ContrattoRSU az) throws Exception {
        String error = contrattoRsuValidator.validate(az);
        if (org.apache.commons.lang.StringUtils.isEmpty(error))
        {
            contrattoRSURepository.save(az);
            return;
        }
        throw new Exception(error);
    }
}
