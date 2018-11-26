package applica.feneal.services;

import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;

import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
public interface AziendaService {
    Azienda getAziendaById(long loggedUserId, long firmId);
    Azienda getAziendaByDescription(String description);
    void saveOrUpdate(long loggedUserId, Azienda az) throws Exception;

    void delete(long loggedUserId, long idLav);


    List<LiberoDbNazionale> getLiberiByFirmId(long loggedUserId, long firmId);

    Azienda getAziendaByDescriptionorCreateIfNotExist(String description) throws Exception;
}
