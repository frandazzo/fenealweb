package applica.feneal.services.RSU;

import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;

import java.util.List;

/**
 * Created by felicegramegna on 23/02/2021.
 */
public interface AziendaRsuService {
    AziendaRSU getAziendaRsuById(long loggedUserId, Long firmId);

    void saveOrUpdate(long loggedUserId, AziendaRSU az) throws Exception;

    AziendaRSU getAziendaRsuByDescriptionorCreateIfNotExist(String description) throws Exception;

    void delete(long lid, long id);


}
