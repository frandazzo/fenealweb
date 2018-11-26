package applica.feneal.services.impl.setup;

import applica.feneal.domain.data.RolesRepository;
import applica.feneal.domain.data.UsersRepository;

import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.utils.LoggerClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AdminSetup implements AppSetup {



    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository roleRep;

    //private static final Logger logger = LogManager.getLogger();


    @Override
    public void setup() {
        LoggerClass.info("Start check admin exist setup");
        createAdminIfNotExist();
        LoggerClass.info("End check admin exist setup");
    }



    public void createAdminIfNotExist() {


        try
        {





        User u = usersRepository.get(1L).orElse(null);

        if (u == null){
            Role admin = roleRep.get(1L).get();

            String encodedPassword = new Md5PasswordEncoder().encodePassword("applica", null);

            User user = new User();
            user.setName("Admin");
            user.setSurname("Admin");
            user.setMail("admin@applica.guru");
            user.setPassword(encodedPassword);
            user.setUsername("administrator");
            user.setActive(true);
            List<Role> roles = new ArrayList<Role>();
            roles.add(admin);
            user.setRoles(roles);


            usersRepository.save(user);
        }


        }catch(Exception ex){
            LoggerClass.error(ex.getMessage());
        }





    }

}
