package applica.feneal.domain.data;

import applica.feneal.domain.model.User;
import applica.framework.Repository;
import org.hibernate.Session;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 28/10/13
 * Time: 17:21
 */
public interface UsersRepository extends Repository<User> {
    public User getUserByUsername(String username);


    void executeCommand(Command command);

    Session getSession();
}
