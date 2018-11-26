package applica.feneal.domain.validation;

import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.aziende.Azienda;
import applica.feneal.domain.model.core.servizi.Documento;
import applica.framework.AEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by angelo on 28/04/2016.
 */
@Component
public class DocumentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Documento.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        if (((Documento) o).getData() == null)
            errors.rejectValue("data", null, "La data è obbligatoria");

        if (((Documento) o).getTipo() == null)
            errors.rejectValue("tipo", null, "Il tipo è obbligatorio");

    }

}
