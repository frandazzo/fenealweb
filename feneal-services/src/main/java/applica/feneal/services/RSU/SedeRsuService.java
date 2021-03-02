package applica.feneal.services.RSU;

import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;

import java.util.List;

/**
 * Created by felicegramegna on 25/02/2021.
 */
public interface SedeRsuService {
    SedeRSU getSedeRsuById(long loggedUserId, Long firmId);
    List<SedeRSU> getAllSediAziendaRsu(long firmId);
    void delete(long lid, long id);
    void saveOrUpdate(long loggedUserId, SedeRSU s) throws Exception;
}
