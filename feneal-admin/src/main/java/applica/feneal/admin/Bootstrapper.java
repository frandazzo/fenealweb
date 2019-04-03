package applica.feneal.admin;

import applica.feneal.admin.crudconfig.CrudConfSetup;
import applica.feneal.admin.data.UsersRepositoryWrapper;
import applica.feneal.admin.fields.renderers.PermissionsFieldRenderer;
import applica.feneal.admin.fields.renderers.RolesFieldRenderer;
import applica.feneal.admin.fields.renderers.UserImageFieldRenderer;
import applica.feneal.admin.search.RoleSearchForm;
import applica.feneal.admin.search.MailSearchForm;
import applica.feneal.domain.data.RolesRepository;
import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.feneal.services.SetupService;
import applica.framework.AEntity;
import applica.framework.library.options.OptionsManager;
import applica.framework.licensing.LicenseManager;
import applica.framework.widgets.CrudConfiguration;
import applica.framework.widgets.CrudConstants;
import applica.framework.widgets.CrudFactory;
import applica.framework.widgets.Grid;
import applica.framework.widgets.builders.FormConfigurator;
import applica.framework.widgets.builders.GridConfigurator;
import applica.framework.widgets.cells.renderers.DefaultCellRenderer;
import applica.framework.widgets.acl.CrudPermission;
import applica.framework.widgets.acl.CrudSecurityConfigurer;
import applica.framework.widgets.fields.Params;
import applica.framework.widgets.fields.renderers.DatePickerRenderer;
import applica.framework.widgets.fields.renderers.DefaultFieldRenderer;
import applica.framework.widgets.fields.renderers.MailFieldRenderer;
import applica.framework.widgets.fields.renderers.PasswordFieldRenderer;
import applica.framework.widgets.forms.processors.DefaultFormProcessor;
import applica.framework.widgets.forms.renderers.DefaultFormRenderer;
import applica.framework.widgets.grids.renderers.DefaultGridRenderer;
import applica.framework.security.authorization.Permissions;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import applica.framework.library.utils.NullableDateConverter;

import java.util.Date;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 2/21/13
 * Time: 3:37 PM
 */
public class Bootstrapper {

    @Autowired
    UsersRepository rep;

    static {
        AEntity.strategy = AEntity.IdStrategy.Long;
    }

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OptionsManager options;

    @Autowired
    private CrudFactory crudFactory;

    @Autowired
    private SetupService setupService;

    @Autowired
    private CrudConfSetup crudSetup;


    public void init() {
        LicenseManager.instance().setUser(options.get("applica.framework.licensing.user"));
        LicenseManager.instance().mustBeValid();

        setupService.setup();
        crudSetup.setup();





    }

//    private void configureCrudSecurity() {
//        Permissions.instance().registerStatic("users:new");
//        Permissions.instance().registerStatic("users:list");
//        Permissions.instance().registerStatic("users:save");
//        Permissions.instance().registerStatic("users:edit");
//        Permissions.instance().registerStatic("users:delete");
//
//        CrudSecurityConfigurer.instance().configure("user", CrudPermission.NEW, "users:new");
//        CrudSecurityConfigurer.instance().configure("user", CrudPermission.LIST, "users:list");
//        CrudSecurityConfigurer.instance().configure("user", CrudPermission.SAVE, "users:save");
//        CrudSecurityConfigurer.instance().configure("user", CrudPermission.EDIT, "users:edit");
//        CrudSecurityConfigurer.instance().configure("user", CrudPermission.DELETE, "users:delete");
//
//        Permissions.instance().registerStatic("roles:new");
//        Permissions.instance().registerStatic("roles:list");
//        Permissions.instance().registerStatic("roles:save");
//        Permissions.instance().registerStatic("roles:edit");
//        Permissions.instance().registerStatic("roles:delete");
//
//        CrudSecurityConfigurer.instance().configure("role", CrudPermission.NEW, "roles:new");
//        CrudSecurityConfigurer.instance().configure("role", CrudPermission.LIST, "roles:list");
//        CrudSecurityConfigurer.instance().configure("role", CrudPermission.SAVE, "roles:save");
//        CrudSecurityConfigurer.instance().configure("role", CrudPermission.EDIT, "roles:edit");
//        CrudSecurityConfigurer.instance().configure("role", CrudPermission.DELETE, "roles:delete");
//    }

//    private void registerForms() {
//        FormConfigurator.configure(User.class, "user")
//                .repository(UsersRepositoryWrapper.class)
//                .tab("label.user_info")
//                .fieldSet("label.account")
//                .field("mail", "label.mail", MailFieldRenderer.class).param("mail", Params.PLACEHOLDER, "mail@example.com")
//                .field("password", "label.password", PasswordFieldRenderer.class).param("password", Params.PLACEHOLDER, "msg.leave_blank_password")
//                .field("active", "label.active")
//                .fieldSet("label.profile")
//                .field("registrationDate", "label.registration_date", DatePickerRenderer.class)
//                .field("image", "label.image", UserImageFieldRenderer.class)
//                .fieldSet("label.roles")
//                .field("roles", null, RolesFieldRenderer.class);
//
//        FormConfigurator.configure(Role.class, "role")
//                .repository(RolesRepository.class)
//                .field("role", "label.name")
//                .field("permissions", "label.permissions", PermissionsFieldRenderer.class);
//    }
//
//    private void registerGrids() {
//        GridConfigurator.configure(User.class, "user")
//                .repository(UsersRepository.class)
//                .searchForm(MailSearchForm.class)
//                .column("mail", "label.mail", true)
//                .column("active", "label.active", false);
//
//        GridConfigurator.configure(Role.class, "role")
//                .repository(RolesRepository.class)
//                .searchForm(RoleSearchForm.class)
//                .column("role", "label.name", true);
//
//    }
}
