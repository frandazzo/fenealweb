package applica.feneal.services.impl.aziende;

import applica.feneal.domain.data.core.aziende.AziendeRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.dbnazionale.LiberoDbNazionale;
import applica.feneal.domain.validation.AziendaValidator;
import applica.feneal.services.AziendaService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
@Service
public class AziendaServiceImpl implements AziendaService {

    @Autowired
    private AziendeRepository lavRep;

    @Autowired
    private AziendaValidator lavVal;

    @Autowired
    private Security sec;

    @Override
    public Azienda getAziendaById(long loggedUserId, long firmId) {
        return lavRep.get(firmId).orElse(null);
    }

    @Override
    public Azienda getAziendaByDescription(String description) {
        Azienda az = lavRep.find(LoadRequest.build().filter("description", description)).findFirst().orElse(null);

        return az;
    }

    @Override
    public void saveOrUpdate(long loggedUserId, Azienda az) throws Exception {
        String error = lavVal.validate(az);
        if (org.apache.commons.lang.StringUtils.isEmpty(error))
        {
            lavRep.save(az);
            return;
        }

        throw new Exception(error);
    }

    @Override
    public void delete(long loggedUserId, long idLav) {
        lavRep.delete(idLav);
    }

    @Override
    public List<LiberoDbNazionale> getLiberiByFirmId(long loggedUserId, long firmId) {
        return null;
    }

    @Override
    public Azienda getAziendaByDescriptionorCreateIfNotExist(String description) throws Exception {

        if (StringUtils.isEmpty(description))
            throw new Exception("Ragione sociale azienda nulla");

        Azienda az = lavRep.find(LoadRequest.build().filter("description", description)).findFirst().orElse(null);
        if (az != null)
            return az;



        //creo l'azienda
        Azienda a = new Azienda();
        a.setDescription(description);
        saveOrUpdate(((User) sec.getLoggedUser()).getLid(), a);

        return a;
    }
}
