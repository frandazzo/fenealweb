package applica.feneal.data.hibernate.core;

import applica.feneal.domain.data.Command;
import applica.feneal.domain.data.core.UserOptionsRepository;
import applica.feneal.domain.data.core.VisibleFunctionRepository;
import applica.feneal.domain.model.core.VisibleFunction;
import applica.feneal.domain.model.setting.option.UserOptions;
import applica.framework.data.hibernate.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fgran on 11/04/2016.
 */
@Repository
public class UserOptionsHibernateRepository extends HibernateRepository<UserOptions> implements UserOptionsRepository {


    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

    @Override
    public Class<UserOptions> getEntityType() {
        return UserOptions.class;
    }

}
