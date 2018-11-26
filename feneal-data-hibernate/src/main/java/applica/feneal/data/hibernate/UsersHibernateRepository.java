package applica.feneal.data.hibernate;

import applica.feneal.domain.data.Command;
import applica.framework.LoadRequest;
import applica.framework.Sort;
import applica.framework.data.hibernate.HibernateRepository;
import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 28/10/13
 * Time: 17:22
 */
@Repository
public class UsersHibernateRepository extends HibernateRepository<User> implements UsersRepository {

    @Override
    public Class<User> getEntityType() {
        return User.class;
    }

    @Override
    public List<Sort> getDefaultSorts() {
        return Arrays.asList(new Sort("username", false));
    }


    @Override
    public User getUserByUsername(String username) {
        LoadRequest req = LoadRequest.build().filter("username", username);
        return find(req).findFirst().orElse(null);

    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }
}
