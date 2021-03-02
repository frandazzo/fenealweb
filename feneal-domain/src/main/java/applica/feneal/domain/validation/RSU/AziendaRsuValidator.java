package applica.feneal.domain.validation.RSU;

import applica.feneal.domain.data.core.RSU.AziendeRSURepository;
import applica.feneal.domain.model.core.RSU.AziendaRSU;

import applica.feneal.domain.validation.DomainClassValidator;
import applica.framework.AEntity;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by felicegramegna on 23/02/2021.
 */
@Component
public class AziendaRsuValidator implements DomainClassValidator {

    @Autowired
    private AziendeRSURepository azRep;

    @Override
    public String validate(AEntity elem) throws Exception {
        AziendaRSU l = ((AziendaRSU) elem);
        if (l == null)
            throw new Exception("Impossibile validare. Elemento da validare nullo o non dello stesso tipo del validatore");

        StringBuilder b = new StringBuilder();


        if (StringUtils.isEmpty(l.getPiva()) || l.getPiva().length() < 11 || l.getPiva().length() > 11){
            b.append("P.Iva nulla");
            return b.toString();
        }

        if (StringUtils.isEmpty(l.getCity())){
            b.append("Citt&#224; non selezionata");
            return b.toString();
        }

        if (StringUtils.isEmpty(l.getProvince())){
            b.append("Provincia non selezionata");
            return b.toString();
        }

        if (StringUtils.isEmpty(l.getDescription())){
            b.append("Ragione sociale nulla");
            return b.toString();
        }



        //adesso devo verificare che la p.iva dell'azienda sia unica per tutto il territorio
        AziendaRSU a = azRep.find(LoadRequest.build().filter("piva", l.getPiva())).findFirst().orElse(null);
        if (l.getLid() == 0){
            if (a != null)
                b.append("ATTENZIONE! P.Iva gi&#224; esistente");

        }else{
            if (a != null){
                if (l.getLid() != a.getLid())
                    b.append("ATTENZIONE! P.Iva gi&#224; esistente");
            }

        }


        String error = b.toString();


        return error;
    }
}
