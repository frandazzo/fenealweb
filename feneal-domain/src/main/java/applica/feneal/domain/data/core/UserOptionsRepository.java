package applica.feneal.domain.data.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.model.core.VisibleFunction;
import applica.feneal.domain.model.setting.option.UserOptions;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Created by fgran on 11/04/2016.
 */
public interface UserOptionsRepository extends Repository<UserOptions> {

    void executeCommand(Command command);

    Session getSession();

}
