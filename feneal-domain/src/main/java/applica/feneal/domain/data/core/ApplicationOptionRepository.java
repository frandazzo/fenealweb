package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.Paritethic;
import applica.feneal.domain.model.setting.option.ApplicationOptions;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationOptionRepository extends Repository<ApplicationOptions> {

    void executeCommand(Command command);

    Session getSession();

}
