package applica.feneal.domain.validation;

import applica.feneal.domain.model.DummyUser;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/19/15
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DummyUserValidator extends UserValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(DummyUser.class);
    }

}
