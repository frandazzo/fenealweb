package applica.feneal.domain.validation;

import applica.framework.AEntity;

import java.util.List;

/**
 * Created by fgran on 07/04/2016.
 */
public interface DomainClassValidator {

    String validate(AEntity elem) throws Exception;

}
