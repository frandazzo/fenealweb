package applica.feneal.domain.validation;

import applica.feneal.domain.model.core.servizi.Comunicazione;
import applica.feneal.domain.model.core.servizi.RichiestaInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by angelo on 28/04/2016.
 */
@Component
public class RichiestaValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(RichiestaInfo.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        if (((RichiestaInfo) o).getData() == null)
            errors.rejectValue("data", null, "La data è obbligatoria");

        if (StringUtils.isEmpty(((RichiestaInfo) o).getDestinatario()))
            errors.rejectValue("destinatario", null, "Il destinatario è obbligatorio");

        if (StringUtils.isEmpty(((RichiestaInfo) o).getNote()) || "<br>".equals(((RichiestaInfo) o).getNote()))
            errors.rejectValue("note", null, "Le note sono obbligatorie");

    }

}
