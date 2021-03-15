package applica.feneal.domain.validation.RSU;

import applica.feneal.domain.data.core.RSU.ContrattoRSURepository;
import applica.feneal.domain.model.core.RSU.ContrattoRSU;
import applica.feneal.domain.validation.DomainClassValidator;
import applica.framework.AEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by felicegramegna on 08/03/2021.
 */
@Component
public class ContrattoRsuValidator implements DomainClassValidator {

    @Autowired
    private ContrattoRSURepository contrattoRSURepository;

    @Override
    public String validate(AEntity elem) throws Exception {
        ContrattoRSU l = ((ContrattoRSU) elem);
        if (l == null)
            throw new Exception("Impossibile validare. Elemento da validare nullo o non dello stesso tipo del validatore");

        StringBuilder b = new StringBuilder();


        if(l.getRsuMin() < 1){
            b.append("Rsu Minimo non valido!");
            return b.toString();
        }

        if(l.getRsuMax() < 1 || l.getRsuMax() < l.getRsuMin()){
            b.append("Rsu Massimo non valido!");
            return b.toString();
        }

        if (StringUtils.isEmpty(l.getDescription())){
            b.append("Impossibile creare un contratto senza una DESCRIZIONE");
            return b.toString();
        }

        String error = b.toString();


        return error;
    }
}
