package applica.feneal.services.impl.RSU;


import applica.feneal.domain.data.core.RSU.SedeRSURepository;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.domain.validation.RSU.SedeRsuValidator;
import applica.feneal.services.RSU.SedeRsuService;
import applica.framework.LoadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by felicegramegna on 23/02/2021.
 */
@Service
public class SedeRsuServiceImpl implements SedeRsuService {

    @Autowired
    private SedeRSURepository sedeRSURepository;

    @Autowired
    private SedeRsuValidator sedeRsuValidator;


    @Override
    public SedeRSU getSedeRsuById(long loggedUserId, Long firmId) {
        return sedeRSURepository.get(firmId).orElse(null);
    }

    @Override
    public List<SedeRSU> getAllSediAziendaRsu(long firmId){
        return sedeRSURepository.find(LoadRequest.build().disableOwnershipQuery().filter("aziendaRSU", firmId)).getRows();
    }

//    @Override
//    public void saveOrUpdate(long loggedUserId, AziendaRSU az) throws Exception {
//        String error = azRsuValidator.validate(az);
//        if (org.apache.commons.lang.StringUtils.isEmpty(error))
//        {
//            if(az.getIid() == 0){
//                setCreator(az);
//                updateLastModification(az);
//                azRsuRep.save(az);
//                return;
//            }else{
//                updateLastModification(az);
//                azRsuRep.save(az);
//                return;
//            }
//        }
//        throw new Exception(error);
//    }
//
//
    @Override
    public void delete(long lid, long id) {
        sedeRSURepository.delete(id);
    }

    @Override
    public void saveOrUpdate(long loggedUserId, SedeRSU s) throws Exception {
        String error = sedeRsuValidator.validate(s);
        if (org.apache.commons.lang.StringUtils.isEmpty(error))
        {
            sedeRSURepository.save(s);
            return;
        }
        throw new Exception(error);

    }


}
