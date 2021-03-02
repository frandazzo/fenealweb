package applica.feneal.admin.crudconfig;

import applica.framework.security.authorization.Permissions;
import applica.framework.widgets.acl.CrudPermission;
import applica.framework.widgets.acl.CrudSecurityConfigurer;
import applica.feneal.services.impl.setup.PermissionMap;
import applica.feneal.services.impl.setup.AppSetup;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/12/15
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PermissionsSetup implements AppSetup {
    @Override
    public void setup() {

        //effettuo la registraizone dei permessi per ogni entità mappata su un fomr del framework
        registerPermissions("user");
        configureCrudSecurityConfigurer("user", PermissionMap.staticPermissions("user"));
        configureCrudSecurityConfigurer("dummyuser", PermissionMap.staticPermissions("user"));
        configureCrudSecurityConfigurer("companyuser", PermissionMap.staticPermissions("user"));

        registerPermissions("listalavoro");
        configureCrudSecurityConfigurer("listalavoro", PermissionMap.staticPermissions("listalavoro"));

        registerPermissions("firm");
        configureCrudSecurityConfigurer("firm", PermissionMap.staticPermissions("firm"));

        registerPermissions("company");
        configureCrudSecurityConfigurer("company", PermissionMap.staticPermissions("company"));

        registerPermissions("firmrsu");
        configureCrudSecurityConfigurer("firmrsu", PermissionMap.staticPermissions("firmrsu"));

        registerPermissions("documento");
        configureCrudSecurityConfigurer("documento", PermissionMap.staticPermissions("documento"));

        registerPermissions("documentoazienda");
        configureCrudSecurityConfigurer("documentoazienda", PermissionMap.staticPermissions("documentoazienda"));

        registerPermissions("magazzinodeleghe");
        configureCrudSecurityConfigurer("magazzinodeleghe", PermissionMap.staticPermissions("magazzinodeleghe"));

        registerPermissions("magazzinodeleghelecce");
        configureCrudSecurityConfigurer("magazzinodeleghelecce", PermissionMap.staticPermissions("magazzinodeleghe"));




        registerPermissions("richiesta");
        configureCrudSecurityConfigurer("richiesta", PermissionMap.staticPermissions("richiesta"));

        registerPermissions("comunicazione");
        configureCrudSecurityConfigurer("comunicazione", PermissionMap.staticPermissions("comunicazione"));

        registerPermissions("sector");
        configureCrudSecurityConfigurer("sector", PermissionMap.staticPermissions("sector"));

        registerPermissions("parithetic");
        configureCrudSecurityConfigurer("parithetic", PermissionMap.staticPermissions("parithetic"));

        registerPermissions("visiblefunction");
        configureCrudSecurityConfigurer("visiblefunction", PermissionMap.staticPermissions("visiblefunction"));

        registerPermissions("widget");
        configureCrudSecurityConfigurer("widget", PermissionMap.staticPermissions("widget"));

        registerPermissions("applicationoption");
        configureCrudSecurityConfigurer("applicationoption", PermissionMap.staticPermissions("applicationoption"));

        registerPermissions("contract");
        configureCrudSecurityConfigurer("contract", PermissionMap.staticPermissions("contract"));

        registerPermissions("worker");
        configureCrudSecurityConfigurer("worker", PermissionMap.staticPermissions("worker"));

        registerPermissions("quoteassociative");
        configureCrudSecurityConfigurer("quoteassociative", PermissionMap.staticPermissions("quoteassociative"));

        registerPermissions("delegation");
        configureCrudSecurityConfigurer("delegation", PermissionMap.staticPermissions("delegation"));

        registerPermissions("delegationreason");
        configureCrudSecurityConfigurer("delegationreason", PermissionMap.staticPermissions("delegationreason"));

        registerPermissions("revocationreason");
        configureCrudSecurityConfigurer("revocationreason", PermissionMap.staticPermissions("revocationreason"));

        registerPermissions("documenttype");
        configureCrudSecurityConfigurer("documenttype", PermissionMap.staticPermissions("documenttype"));

        registerPermissions("fund");
        configureCrudSecurityConfigurer("fund", PermissionMap.staticPermissions("fund"));

        registerPermissions("communicationtype");
        configureCrudSecurityConfigurer("communicationtype", PermissionMap.staticPermissions("communicationtype"));

        registerPermissions("communicationreason");
        configureCrudSecurityConfigurer("communicationreason", PermissionMap.staticPermissions("communicationreason"));

        registerPermissions("proceduretype");
        configureCrudSecurityConfigurer("proceduretype", PermissionMap.staticPermissions("proceduretype"));

        registerPermissions("collaborator");
        configureCrudSecurityConfigurer("collaborator", PermissionMap.staticPermissions("collaborator"));

        registerPermissions("signupdelegationreason");
        configureCrudSecurityConfigurer("signupdelegationreason", PermissionMap.staticPermissions("signupdelegationreason"));

        registerPermissions("membersreport");
        configureCrudSecurityConfigurer("membersreport", PermissionMap.staticPermissions("membersreport"));

        registerPermissions("notmembersreport");
        configureCrudSecurityConfigurer("notmembersreport", PermissionMap.staticPermissions("notmembersreport"));

        registerPermissions("quotereport");
        configureCrudSecurityConfigurer("quotereport", PermissionMap.staticPermissions("quotereport"));

        registerPermissions("importationsreport");
        configureCrudSecurityConfigurer("importationsreport", PermissionMap.staticPermissions("importationsreport"));


        registerPermissions("referenti");
        configureCrudSecurityConfigurer("referenti", PermissionMap.staticPermissions("referenti"));

//        registerPermissions("clinicalcategory");
//        registerPermissions("clinicalcenter");
//        registerPermissions("teleconsulto");
//        registerPermissions("responseTeleconsulto");
//        registerPermissions("moderatorTeleconsulto");
//        registerPermissions("moderatorTeleconsulto");
//        registerPermissions("webuser");
//        registerPermissions("rules");
//
//
//
//
//        //per ogni permesso registrato lo associo alle crud view

//        configureCrudSecurityConfigurer("webuser", PermissionMap.staticPermissions("webuser"));
//        configureCrudSecurityConfigurer("rules", PermissionMap.staticPermissions("rules"));

//
//
//        configureCrudSecurityConfigurer("clinicalcategory", PermissionMap.staticPermissions("clinicalcategory"));
//        configureCrudSecurityConfigurer("clinicalcenter", PermissionMap.staticPermissions("clinicalcenter"));
//
//        configureCrudSecurityConfigurer("teleconsulto", PermissionMap.staticPermissions("teleconsulto"));
//        configureCrudSecurityConfigurer("responseTeleconsulto", PermissionMap.staticPermissions("responseTeleconsulto"));
//        configureCrudSecurityConfigurer("moderatorTeleconsulto", PermissionMap.staticPermissions("moderatorTeleconsulto"));

    }



    private void registerPermissions(String crudEntityName){
        for (String usersPermission : PermissionMap.staticPermissions(crudEntityName)) {
            Permissions.instance().registerStatic(usersPermission);
        }
    }


    private void configureCrudSecurityConfigurer(String crudEntityName, List<String> crudPermissions){


        CrudSecurityConfigurer.instance().configure(crudEntityName, CrudPermission.NEW, crudPermissions.stream().filter(c -> c.endsWith("new")).findFirst().get());
        CrudSecurityConfigurer.instance().configure(crudEntityName, CrudPermission.LIST, crudPermissions.stream().filter(c -> c.endsWith("list")).findFirst().get());
        CrudSecurityConfigurer.instance().configure(crudEntityName, CrudPermission.SAVE, crudPermissions.stream().filter(c -> c.endsWith("save")).findFirst().get());
        CrudSecurityConfigurer.instance().configure(crudEntityName, CrudPermission.EDIT, crudPermissions.stream().filter(c -> c.endsWith("edit")).findFirst().get());
        CrudSecurityConfigurer.instance().configure(crudEntityName, CrudPermission.DELETE, crudPermissions.stream().filter(c -> c.endsWith("delete")).findFirst().get());



    }

}
