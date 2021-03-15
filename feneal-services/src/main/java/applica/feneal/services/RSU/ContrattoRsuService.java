package applica.feneal.services.RSU;


import applica.feneal.domain.model.core.RSU.ContrattoRSU;

/**
 * Created by felicegramegna on 09/03/2021.
 */
public interface ContrattoRsuService {
    void delete(long lid, long id);
    ContrattoRSU getContrattoRsuById(long loggedUserId, Long firmId);
    void saveOrUpdate(long loggedUserId, ContrattoRSU az) throws Exception;
}
