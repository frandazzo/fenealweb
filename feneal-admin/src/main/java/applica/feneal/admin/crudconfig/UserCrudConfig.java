package applica.feneal.admin.crudconfig;

import applica.feneal.admin.data.UsersRepositoryWrapper;
import applica.feneal.admin.fields.renderers.*;
import applica.feneal.admin.fields.renderers.geo.OptionalCityFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalProvinceFieldRenderer;
import applica.feneal.admin.fields.renderers.geo.OptionalStateFieldRenderer;
//import applica.feneal.admin.mapping.RolesToSinglePropertyMapper;
import applica.feneal.admin.mapping.RolesToSinglePropertyMapper;
import applica.feneal.admin.search.AdminUserSearchForm;
import applica.feneal.domain.model.User;
import applica.framework.security.Security;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.Values;
import applica.framework.widgets.fields.renderers.MailFieldRenderer;
import applica.framework.widgets.fields.renderers.PasswordFieldRenderer;
import applica.framework.widgets.fields.renderers.TextAreaFieldRenderer;
import applica.feneal.services.impl.setup.AppSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/13/15
 * Time: 1:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserCrudConfig implements AppSetup {

    @Autowired
    private Security security;


    @Override
    public void setup() {



        FormConfigurator.configure(User.class, "companyuser")
                .repository(UsersRepositoryWrapper.class)
                .fieldSet("label.account")
                    .field("username", "label.username").param("username", Params.ROW, "couple").param("username", Params.COLS, Values.COLS_6)
                    .field("password", "label.password", PasswordFieldRenderer.class).param("password", Params.PLACEHOLDER, "msg.leave_blank_password").param("password", Params.ROW, "couple").param("password", Params.COLS, Values.COLS_6)
                    .field("company", "label.group", OptionalCompanySelectRenderer.class).param("company", Params.ROW, "couple").param("company", Params.COLS, Values.COLS_6)

                    .field("roles", null, AdminRolesFieldRenderer.class, RolesToSinglePropertyMapper.class)
                    .field("active", "label.active")
                .fieldSet("label.user_info")
                    .field("name", "label.name").param("name", Params.ROW, "couple").param("name", Params.COLS, Values.COLS_6)
                    .field("surname", "label.surname").param("surname", Params.ROW, "couple").param("surname", Params.COLS, Values.COLS_6)
                    .field("mail", "label.mail", MailFieldRenderer.class).param("mail", Params.PLACEHOLDER, "mail@example.com").param("mail", Params.ROW, "couple").param("mail", Params.COLS, Values.COLS_6);
//                    .field("mail", "label.mail", MailFieldRenderer.class).param("mail", Params.PLACEHOLDER, "mail@example.com").param("mail", Params.ROW, "couple").param("mail", Params.COLS, Values.COLS_6)
//                    .field("image", "label.image", UserImageFieldRenderer.class).param("image", Params.ROW, "couple").param("image", Params.COLS, Values.COLS_6)
//                .fieldSet("label.user_address")
//                    .field("country", "label.country", OptionalStateFieldRenderer.class).param("country", Params.ROW, "couple").param("country", Params.COLS, Values.COLS_6)
//                    .field("province", "label.province", OptionalProvinceFieldRenderer.class).param("province", Params.ROW, "couple").param("province", Params.COLS, Values.COLS_6)
//                    .field("address", "label.address", TextAreaFieldRenderer.class).param("address", Params.ROW, "couple").param("address", Params.COLS, Values.COLS_6)
//                    .field("city", "label.city", OptionalCityFieldRenderer.class).param("city", Params.ROW, "couple").param("city", Params.COLS, Values.COLS_6)
//                    .field("cap", "label.cap").param("cap", Params.ROW, "couple").param("cap", Params.COLS, Values.COLS_6);
////




        GridConfigurator.configure(User.class, "companyuser")
                .repository(UsersRepositoryWrapper.class)
                .searchForm(AdminUserSearchForm.class)
                .column("company", "label.group", true)
                .column("username", "label.username", true)
                .column("name", "label.name", false)
                .column("surname", "label.surname", false)
                .column("mail", "label.mail", false)
                .column("roles","label.role", false, RolesCellRenderer.class)
                .column("active", "label.active", false);

    }
}

