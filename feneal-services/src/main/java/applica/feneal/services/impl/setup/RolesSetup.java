package applica.feneal.services.impl.setup;

import applica.feneal.domain.data.RolesRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.utils.LoggerClass;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RolesSetup implements AppSetup {

    @Autowired
    private RolesRepository roleRep;

    //private static final Logger logger = LogManager.getLogger();


    @Override
    public void setup() {

        createRolesIfNotExist();

        updatePermissions();



    }

    private void updatePermissions() {


      //  logger.info("Start update permissions setup");

        //per ogni ruolo imposto la lista di permessi associata.
        //Posso fare questo in una classe di setup dell'applicazione perchè la gestione
        //dei ruoli non è modificabile

        //il ruolo di amministratore ha i permessi per
        // la creazione di utenti amministratori e amministratori della società
        //ha il permesso di creare le società (gruppi)

        List<String> adminPermissions = new ArrayList<String>();
        adminPermissions.addAll(PermissionMap.staticPermissions("user"));
        adminPermissions.addAll(PermissionMap.staticPermissions("company"));
        adminPermissions.addAll(PermissionMap.staticPermissions("sector"));
        adminPermissions.addAll(PermissionMap.staticPermissions("parithetic"));
        adminPermissions.addAll(PermissionMap.staticPermissions("visiblefunction"));
        adminPermissions.addAll(PermissionMap.staticPermissions("widget"));
        adminPermissions.addAll(PermissionMap.staticPermissions("firm"));
        adminPermissions.addAll(PermissionMap.staticPermissions("contractrsu"));
        adminPermissions.addAll(PermissionMap.staticPermissions("firmrsu"));
        adminPermissions.addAll(PermissionMap.staticPermissions("verbvot"));

        long adminId = 1;

        setPermissionsToRole(adminPermissions, adminId);

        //il ruolo di amministratore della società puo' creare utenti
        List<String> groupAdminPermissions = new ArrayList<String>();
        groupAdminPermissions.addAll(PermissionMap.staticPermissions("user"));
       // groupAdminPermissions.addAll(PermissionMap.staticPermissions("companyuseruser"));
        groupAdminPermissions.addAll(PermissionMap.staticPermissions("applicationoption"));

        long groupAdminId = 2;
        setPermissionsToRole(groupAdminPermissions, groupAdminId);


        // Ruolo segretario
        List<String> secretaryPermissions = new ArrayList<String>();
        secretaryPermissions.addAll(PermissionMap.staticPermissions("worker"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("user"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("firm"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("listalavoro"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("quoteassociative"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("delegation"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("revocationreason"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("delegationreason"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("documenttype"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("fund"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("communicationtype"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("communicationreason"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("proceduretype"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("collaborator"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("signupdelegationreason"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("membersreport"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("notmembersreport"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("quotereport"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("importationsreport"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("documento"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("documentoazienda"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("richiesta"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("comunicazione"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("magazzinodeleghe"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("magazzinodeleghelecce"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("contract"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("applicationoption"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("referenti"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("firmrsu"));
        secretaryPermissions.addAll(PermissionMap.staticPermissions("verbvot"));

        long secretaryId = 3;
        setPermissionsToRole(secretaryPermissions, secretaryId);

        // Ruolo funzionario1
        List<String> executive1Permissions = new ArrayList<String>();
        executive1Permissions.addAll(PermissionMap.staticPermissions("worker"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("firm"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("listalavoro"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("quoteassociative"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("delegation"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("revocationreason"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("delegationreason"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("documenttype"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("fund"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("communicationtype"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("communicationreason"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("proceduretype"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("collaborator"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("signupdelegationreason"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("documento"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("documentoazienda"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("richiesta"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("comunicazione"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("magazzinodeleghe"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("magazzinodeleghelecce"));
        executive1Permissions.addAll(PermissionMap.staticPermissions("contract"));


        long executive1Id = 4;
        setPermissionsToRole(executive1Permissions, executive1Id);

        // Ruolo funzionario2
        List<String> executive2Permissions = new ArrayList<String>();
        executive2Permissions.addAll(PermissionMap.staticPermissions("worker"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("firm"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("listalavoro"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("quoteassociative"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("delegation"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("revocationreason"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("delegationreason"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("documenttype"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("fund"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("communicationtype"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("communicationreason"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("proceduretype"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("collaborator"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("signupdelegationreason"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("documento"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("documentoazienda"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("richiesta"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("comunicazione"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("magazzinodeleghe"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("magazzinodeleghelecce"));
        executive2Permissions.addAll(PermissionMap.staticPermissions("contract"));


        long executive2Id = 5;
        setPermissionsToRole(executive2Permissions, executive2Id);

        // Ruolo Regionale
        List<String> regionalPermissions = new ArrayList<String>();
        regionalPermissions.addAll(PermissionMap.staticPermissions("worker"));
        regionalPermissions.addAll(PermissionMap.staticPermissions("firm"));
        regionalPermissions.addAll(PermissionMap.staticPermissions("membersreport"));
        regionalPermissions.addAll(PermissionMap.staticPermissions("notmembersreport"));
        regionalPermissions.addAll(PermissionMap.staticPermissions("quotereport"));
        regionalPermissions.addAll(PermissionMap.staticPermissions("importationsreport"));

        long regionalId = 6;
        setPermissionsToRole(regionalPermissions, regionalId);


        // Ruolo Nazionale
        List<String> nationalPermissions = new ArrayList<String>();
        nationalPermissions.addAll(PermissionMap.staticPermissions("worker"));
        nationalPermissions.addAll(PermissionMap.staticPermissions("firm"));
        nationalPermissions.addAll(PermissionMap.staticPermissions("membersreport"));
        nationalPermissions.addAll(PermissionMap.staticPermissions("notmembersreport"));
        nationalPermissions.addAll(PermissionMap.staticPermissions("quotereport"));
        nationalPermissions.addAll(PermissionMap.staticPermissions("importationsreport"));




        long nationalId = 7;
        setPermissionsToRole(nationalPermissions, nationalId);


//
//
//
//        //moderator
//        List<String> moderatorPermissions = new ArrayList<String>();
//        moderatorPermissions.addAll(PermissionMap.staticPermissions("moderatorTeleconsulto"));
//        long moderator = 3;
//        setPermissionsToRole(moderatorPermissions, moderator);
//
//        //request doctor
//        List<String> rqdPermissions = new ArrayList<String>();
//        rqdPermissions.addAll(PermissionMap.staticPermissions("teleconsulto"));
//        long requestdoctor = 4;
//        setPermissionsToRole(rqdPermissions, requestdoctor);
//
//        //responsedoctor
//        List<String> rsdPermissions = new ArrayList<String>();
//        rsdPermissions.addAll(PermissionMap.staticPermissions("responseTeleconsulto"));
//        long respdoctor = 5;
//        setPermissionsToRole(rsdPermissions, respdoctor);
//
//
//
//        //requestresponse doctor
//        List<String> reqrespPerm = new ArrayList<String>();
//        reqrespPerm.addAll(PermissionMap.staticPermissions("teleconsulto"));
//        reqrespPerm.addAll(PermissionMap.staticPermissions("responseTeleconsulto"));
//        long reqrespd = 6;
//        setPermissionsToRole(reqrespPerm, reqrespd);
//
//
//
//        //respreq doctor
//        List<String> respreqPerm = new ArrayList<String>();
//        respreqPerm.addAll(PermissionMap.staticPermissions("teleconsulto"));
//        respreqPerm.addAll(PermissionMap.staticPermissions("responseTeleconsulto"));
//        long respreqd = 7;
//        setPermissionsToRole(respreqPerm, respreqd);




//        logger.info("end update permissions setup");



    }

    private void setPermissionsToRole(List<String> adminPermissions, long adminId) {
        Role role = roleRep.get(adminId).get();
        role.setPermissions(adminPermissions);
        roleRep.save(role);
    }


    private void createRolesIfNotExist() {


        try {

            //tutti i ruoli sono predeterminati e sono riferiti agli attori del sistema!!!!!!
            //inoltre un utente può avere un solo ruolo


            LoggerClass.info("Start save roles setup");

            List<Role> roles = roleRep.find(null).getRows();
            if (roles.size() == 0){

                //ruoolo di amministratore
                Role admin = new Role();
                admin.setRole("admin");


                roleRep.save(admin);

                //ruolo amministratorer feneal
                Role groupAdmin = new Role();
                groupAdmin.setRole("AMMINISTRATORE");

                roleRep.save(groupAdmin);


                //ruolo di segretario territoriale
                Role moderator = new Role();
                moderator.setRole("SEGRETARIO");

                roleRep.save(moderator);

                //ruolo di medico funzionario territoriale di primo livello
                Role rd = new Role();
                rd.setRole("FUNZIONARIO1");

                roleRep.save(rd);

                //ruolo di funzionario di secondo livello
                Role rd7 = new Role();
                rd7.setRole("FUNZIONARIO2");

                roleRep.save(rd7);


                Role r1d = new Role();
                r1d.setRole("REGIONALE");

                roleRep.save(r1d);

                Role rd6 = new Role();
                rd6.setRole("NAZIONALE");

                roleRep.save(rd6);





            }




            LoggerClass.info("End save roles setup");
        }catch(Exception ex){

            ex.printStackTrace();
            LoggerClass.error(ex.getMessage());

        }

    }



    }

