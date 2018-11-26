package applica.feneal.domain.model.utils;

import applica.framework.StringIdEntity;
import applica.framework.data.hibernate.annotations.IgnoreMapping;
import applica.framework.security.UserDetailsImpl;

/**
 * Created by fgran on 15/08/2016.
 */
@IgnoreMapping
public class UsersDetails extends UserDetailsImpl implements StringIdEntity {

    private String id;

    public UsersDetails(applica.framework.security.User user) {
        super(user);
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = (String)id;
    }

    @Override
    public void setSid(String sid) {
        this.id = sid;
    }

    @Override
    public String getSid() {
        return this.id;
    }
}
