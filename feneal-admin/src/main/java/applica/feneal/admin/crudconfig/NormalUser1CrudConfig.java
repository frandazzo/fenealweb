package applica.feneal.admin.crudconfig;

import applica.feneal.admin.data.Dummy1UserRepositoryWrapper;
import applica.feneal.admin.data.DummyUserRepositoryWrapper;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.mapping.RolesToSinglePropertyMapper;
import applica.feneal.admin.search.NormalUserSearchForm;
import applica.feneal.domain.model.DummyUser;
import applica.feneal.domain.model.DummyUser1;
import applica.feneal.services.impl.setup.AppSetup;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.MailFieldRenderer;
import applica.framework.widgets.fields.renderers.PasswordFieldRenderer;
import org.springframework.stereotype.Component;

@Component
public class NormalUser1CrudConfig implements AppSetup {




    @Override
    public void setup() {

        GridConfigurator.configure(DummyUser1.class, "dummyuser")
                .repository(Dummy1UserRepositoryWrapper.class)
                .column("username", "label.username", false)
                .column("name", "label.name", false)
                .column("surname", "label.surname", false)
                .column("mail", "label.mail", false)
                .column("roles","label.role", false, RolesCellRenderer.class)
                .column("id", "label.viewActivities", false, UserActivitiesCellRenderer.class);


    }
}
