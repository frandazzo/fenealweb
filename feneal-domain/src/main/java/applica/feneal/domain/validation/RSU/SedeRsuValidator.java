package applica.feneal.domain.validation.RSU;

import applica.feneal.domain.data.core.RSU.AziendeRSURepository;
import applica.feneal.domain.model.core.RSU.AziendaRSU;
import applica.feneal.domain.model.core.RSU.SedeRSU;
import applica.feneal.domain.validation.DomainClassValidator;
import applica.framework.AEntity;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by felicegramegna on 25/02/2021.
 */
@Component
public class SedeRsuValidator implements DomainClassValidator {

    @Override
    public String validate(AEntity elem) throws Exception {
        SedeRSU l = ((SedeRSU) elem);
        if (l == null)
            throw new Exception("Impossibile validare. Elemento da validare nullo o non dello stesso tipo del validatore");

        StringBuilder b = new StringBuilder();

        if (StringUtils.isEmpty(l.getDescription())){
            b.append("Descrizione nulla");
            return b.toString();
        }

        String error = b.toString();


        return error;
    }
}
