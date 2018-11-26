package applica.feneal.admin.data;

import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.framework.*;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by bimbobruno on 17/03/15.
 */
@org.springframework.stereotype.Repository
public class UsersRepositoryWrapper implements Repository<User> {

    @Autowired
    private Security security;

    @Autowired
    private UsersRepository usersRepository;



    @Override
    public Optional<User> get(Object id) {
        Optional<User> user = usersRepository.get(id);
        //remove password for edit form
        user.ifPresent(u -> u.setPassword(""));
        return user;
    }

    @Override
    public LoadResponse<User> find(LoadRequest request) {


        final LoadRequest finalRequest = request;


        request.popFilter("roles").ifPresent(filter -> {
            if (StringUtils.hasLength(String.valueOf(filter.getValue())))
                finalRequest.getFilters().add(new Filter("roles", String.valueOf(filter.getValue()),Filter.IN));
        });


        Role r = ((Role) security.getLoggedUser().getRoles().get(0));
        User u = ((User) security.getLoggedUser());
        if (r.getLid() == 1){

            //se faccio qui la query devo fare attenzione al fatto che voglio solamente gli amministratori

            //creo un filtro sulla proprietà dei ruoli per dire al sistema che voglio gli utenti con tali ruoli
            Filter f = new Filter("roles", new ArrayList<String>(Arrays.asList("1", "2")),"in");
            request.getFilters().add(f);
            return usersRepository.find(request);

        }



        if (request == null){
            request = LoadRequest.build().filter("company", u.getCompany().getLid() );
            return usersRepository.find(request);
        }

        request.getFilters().add(new Filter("company", u.getCompany().getLid()));

//

        return usersRepository.find(request);

    }

    @Override
    public void save(User entity) {

        usersRepository.save(entity);
    }

    @Override
    public void delete(Object id) {
        usersRepository.delete(id);
    }

    @Override
    public Class<User> getEntityType() {
        return usersRepository.getEntityType();
    }
}
