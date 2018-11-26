package applica.feneal.admin.data;


import applica.feneal.admin.utils.SessionUtils;
import applica.feneal.domain.model.User;
import applica.framework.data.security.OwnerProvider;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Francesco on 18/10/2015.
 */
public class CompanyOwnerProvider implements OwnerProvider {
    @Autowired
    private Security security;

    @Autowired

    private SessionUtils session;


    public CompanyOwnerProvider() {
    }

    public Object provide() {


            Security var10000 = this.security;
            User user = (User) Security.withMe().getLoggedUser();

            if (user == null)
                user = (User)session.get("loggedUser");

            if (user == null || user.getCompany() == null)
                return -1;

            return user.getCompany().getLid();

        }


    }

