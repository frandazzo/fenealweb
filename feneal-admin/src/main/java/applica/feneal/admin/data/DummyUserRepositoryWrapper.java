package applica.feneal.admin.data;

import applica.feneal.domain.model.DummyUser;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.framework.LoadRequest;
import applica.framework.LoadResponse;
import applica.framework.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/19/15
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
@org.springframework.stereotype.Repository
public class DummyUserRepositoryWrapper implements Repository<DummyUser> {

    @Autowired
    private UsersRepositoryWrapper wrp;

    @Override
    public Optional<DummyUser> get(Object id) {
        User entity = wrp.get(id).orElse(null);
        if (entity == null)
            return Optional.empty();
        DummyUser u = new DummyUser();


        u.setActive(entity.isActive());
        u.setCompany(entity.getCompany());
        u.setDefaultProvince(entity.getDefaultProvince());
        u.setName(entity.getName());
        u.setSurname(entity.getSurname());
        u.setId(entity.getId());
        u.setPassword(entity.getPassword());
        u.setUsername(entity.getUsername());
        u.setActivationCode(entity.getActivationCode());
        u.setImage(entity.getImage());
        u.setMail(entity.getMail());
        u.setRoles((java.util.List<Role>) entity.getRoles());
        u.setRegistrationDate(entity.getRegistrationDate());
        u.setDecPass(entity.getDecPass());



        Optional<DummyUser> result = Optional.of(u);
        return result;
    }

    @Override
    public LoadResponse<DummyUser> find(LoadRequest request) {
        LoadResponse<User> u = wrp.find(request);
        List<DummyUser> l = new ArrayList<>();
        List<User> data = u.getRows();

        for (User entity : data) {
            DummyUser uu = new DummyUser();

            uu.setActive(entity.isActive());
            uu.setCompany(entity.getCompany());
            uu.setDefaultProvince(entity.getDefaultProvince());
            uu.setName(entity.getName());
            uu.setSurname(entity.getSurname());
            uu.setId(entity.getId());
            uu.setPassword(entity.getPassword());
            uu.setUsername(entity.getUsername());
            uu.setActivationCode(entity.getActivationCode());
            uu.setImage(entity.getImage());
            uu.setMail(entity.getMail());
            uu.setRoles((java.util.List<Role>) entity.getRoles());
            uu.setRegistrationDate(entity.getRegistrationDate());
            uu.setDecPass(entity.getDecPass());

            l.add(uu);
        }
        LoadResponse<DummyUser> res = new LoadResponse<>();
        res.setRows(l);
        res.setTotalRows(u.getTotalRows());



        return res;
    }

    @Override
    public void save(DummyUser entity) {

        User u = new User();

        u.setActive(entity.isActive());
        u.setCompany(entity.getCompany());
        u.setDefaultProvince(entity.getDefaultProvince());
        u.setName(entity.getName());
        u.setSurname(entity.getSurname());
        u.setId(entity.getId());
        u.setPassword(entity.getPassword());
        u.setUsername(entity.getUsername());
        u.setActivationCode(entity.getActivationCode());
        u.setImage(entity.getImage());
        u.setMail(entity.getMail());
        u.setRoles((java.util.List<Role>) entity.getRoles());
        u.setRegistrationDate(entity.getRegistrationDate());
        u.setDecPass(entity.getDecPass());

        wrp.save(u);
    }

    @Override
    public void delete(Object id) {
        wrp.delete(id);
    }

    @Override
    public Class<DummyUser> getEntityType() {
        return DummyUser.class;
    }
}