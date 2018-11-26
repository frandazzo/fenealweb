package applica.feneal.admin.data;

import applica.feneal.domain.model.DummyUser;
import applica.feneal.domain.model.DummyUser1;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class Dummy1UserRepositoryWrapper implements applica.framework.Repository<DummyUser1> {

    @Autowired
    private UsersRepositoryWrapper wrp;

    @Override
    public Optional<DummyUser1> get(Object id) {

        return null;
    }

    @Override
    public LoadResponse<DummyUser1> find(LoadRequest request) {
        List<User> data = wrp.find(request).getRows()
                .stream().filter(a -> ((Role) a.getRoles().get(0)).getIid() > 2).collect(Collectors.toList());
        List<DummyUser1> l = new ArrayList<>();


        for (User entity : data) {
            DummyUser1 uu = new DummyUser1();

            uu.setActive(entity.isActive());
            uu.setCompany(entity.getCompany());

            uu.setName(entity.getName());
            uu.setSurname(entity.getSurname());
            uu.setId(entity.getId());

            uu.setUsername(entity.getUsername());

            uu.setMail(entity.getMail());
            uu.setRoles((java.util.List<Role>) entity.getRoles());


            l.add(uu);
        }
        LoadResponse<DummyUser1> res = new LoadResponse<>();
        res.setRows(l);
        res.setTotalRows(data.size());

        return res;
    }

    @Override
    public void save(DummyUser1 entity) {


    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public Class<DummyUser1> getEntityType() {
        return DummyUser1.class;
    }
}