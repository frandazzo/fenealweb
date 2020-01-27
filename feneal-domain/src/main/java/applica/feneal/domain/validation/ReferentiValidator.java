package applica.feneal.domain.validation;

import applica.feneal.domain.data.core.ristorniEdilizia.ReferentiRepository;
import applica.feneal.domain.model.core.ristorniEdilizia.Referenti;
import applica.framework.AEntity;
import applica.framework.LoadRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReferentiValidator implements Validator {

    @Autowired
    ReferentiRepository referentiRepository;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Referenti.class);
    }


    @Override
    public void validate(Object o, Errors errors){
        Referenti l = ((Referenti) o);
        if (l == null)
            errors.rejectValue("completeName", null, "Impossibile validare. Elemento da validare nullo o non dello stesso tipo del validatore");




        if (StringUtils.isEmpty(l.getCompleteName()))
            errors.rejectValue("completeName", null, "Nominativo nullo");

        if(l.getProRataShare() <= 0)
            errors.rejectValue("proRataShare", null, "Inserire una percentuale positiva");


        //adesso devo verificare che la ragione sociale dell'azienda sia unica per tutto il territorio
        Referenti a = referentiRepository.find(LoadRequest.build().filter("completeName", l.getCompleteName())).findFirst().orElse(null);
        if (l.getLid() == 0){
            if (a != null)
                errors.rejectValue("completeName", null, "Nominativo già esistente");
        }else{
            if (a != null){
                if (l.getLid() != a.getLid())
                    errors.rejectValue("completeName", null, "Nominativo già esistente");
            }

        }
    }
}
