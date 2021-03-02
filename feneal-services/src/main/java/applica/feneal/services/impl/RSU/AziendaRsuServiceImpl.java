package applica.feneal.services.impl.RSU;


import applica.feneal.domain.data.core.RSU.AziendeRSURepository;
import applica.feneal.domain.data.core.RSU.SedeRSURepository;
import applica.feneal.domain.model.Filters;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.validation.RSU.AziendaRsuValidator;
import applica.feneal.services.RSU.AziendaRsuService;
import applica.framework.LoadRequest;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by felicegramegna on 23/02/2021.
 */
@Service
public class AziendaRsuServiceImpl implements AziendaRsuService {

    @Autowired
    private Security sec;

    @Autowired
    private AziendeRSURepository azRsuRep;

    @Autowired
    private SedeRSURepository sedeRSURepository;

    @Autowired
    private AziendaRsuValidator azRsuValidator;

    @Override
    public AziendaRSU getAziendaRsuById(long loggedUserId, Long firmId) {
        if(firmId == null)
            return null;

        return azRsuRep.get(firmId).orElse(null);
    }



    @Override
    public void saveOrUpdate(long loggedUserId, AziendaRSU az) throws Exception {
        String error = azRsuValidator.validate(az);
        if (org.apache.commons.lang.StringUtils.isEmpty(error))
        {
            if(az.getIid() == 0){
                setCreator(az);
                updateLastModification(az);
                azRsuRep.save(az);
                return;
            }else{
                updateLastModification(az);
                azRsuRep.save(az);
                return;
            }
        }
        throw new Exception(error);
    }

    @Override
    public AziendaRSU getAziendaRsuByDescriptionorCreateIfNotExist(String description) throws Exception {

        if (StringUtils.isEmpty(description))
            throw new Exception("Ragione sociale azienda nulla");

        AziendaRSU az = azRsuRep.find(LoadRequest.build().disableOwnershipQuery().filter("description", description)).findFirst().orElse(null);
        if (az != null)
            return az;



        //creo l'azienda
        AziendaRSU a = new AziendaRSU();
        a.setDescription(description);
        a.setPiva("xxxxxxxxxxx");
        a.setProvince(((User) sec.getLoggedUser()).getCompany().getProvinces().get(0).getDescription());
        a.setCity(((User) sec.getLoggedUser()).getCompany().getProvinces().get(0).getDescription());
        saveOrUpdate(((User) sec.getLoggedUser()).getLid(), a);

        return a;
    }

    @Override
    public void delete(long lid, long id) {
        azRsuRep.delete(id);
    }

    private void updateLastModification(AziendaRSU az) {
        User u = ((User) sec.getLoggedUser());
        az.setLastModificationDate(new Date());
        az.setCompanyLastModification(u.getCompany().getDescription());
        az.setUsernameLastModification(u.getUsername());
    }

    private void setCreator(AziendaRSU az) {
        User u = ((User) sec.getLoggedUser());
        az.setCompanyCreator(u.getCompany().getDescription());
        az.setCreateDate(new Date());
        az.setUsernameCreator(u.getUsername());

        az.setLastModificationDate(new Date());
        az.setCompanyLastModification(u.getCompany().getDescription());
        az.setUsernameLastModification(u.getUsername());
    }
}
