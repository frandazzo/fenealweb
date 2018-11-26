package applica.feneal.services.impl.importData;

import applica.feneal.domain.data.core.ParitheticRepository;
import applica.feneal.domain.data.core.SectorRepository;
import applica.feneal.domain.data.core.SignupDelegationReasonRepository;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.core.Sector;
import applica.feneal.domain.model.setting.CausaleIscrizioneDelega;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fgran on 09/05/2017.
 */
@Component
public class ImportCausaliService {

    @Autowired
    private SectorRepository sectorRep;

    @Autowired
    private ParitheticRepository pariteticRep;

    @Autowired
    private SignupDelegationReasonRepository causaleIscrizioneDelegaRep;


    public void createIfNotExistCausaleIscrizioneDelega(String iscrizione) {
        CausaleIscrizioneDelega d = causaleIscrizioneDelegaRep.find(LoadRequest.build().filter("description", iscrizione)).findFirst().orElse(null);
        if (d == null){
            d = new CausaleIscrizioneDelega();
            d.setDescription(iscrizione);

            causaleIscrizioneDelegaRep.save(d);
        }

    }

    public CausaleIscrizioneDelega getCausaleIscrizione(String description){
        if (!StringUtils.isEmpty(description)){
            return causaleIscrizioneDelegaRep.find(LoadRequest.build().filter("description", description)).findFirst().get();
        }
        return null;
    }

    public Paritethic getEnte(String description){
        if (!StringUtils.isEmpty(description)){
            return pariteticRep.find(LoadRequest.build().filter("description", description)).findFirst().get();
        }
        return null;
    }

    public Sector getSettore(String description) {
        if (!StringUtils.isEmpty(description)){
            return sectorRep.find(LoadRequest.build().filter("description", description)).findFirst().get();
        }
        return null;
    }
}
