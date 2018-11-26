package applica.feneal.domain.validation;

import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.core.servizi.Documento;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by angelo on 28/04/2016.
 */
@Component
public class CommunicationValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Comunicazione.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        if (((Comunicazione) o).getData() == null)
            errors.rejectValue("data", null, "La data è obbligatoria");

        if (((Comunicazione) o).getTipo() == null)
            errors.rejectValue("tipo", null, "Il tipo è obbligatorio");

        if (((Comunicazione) o).getCausale() == null)
            errors.rejectValue("causale", null, "La causale è obbligatoria");

        if (StringUtils.isEmpty(((Comunicazione) o).getOggetto()))
            errors.rejectValue("oggetto", null, "L'oggetto è obbligatorio");

    }

}
