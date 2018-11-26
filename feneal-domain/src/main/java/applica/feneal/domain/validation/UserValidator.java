package applica.feneal.domain.validation;

import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.Role;
import applica.feneal.domain.model.User;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 05/11/13
 * Time: 18:26
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private Security secure;


    @Autowired
    private UsersRepository userRep;

    private static final String NUMBER_SMS_REGEX = "^[0-9]{1,}";


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        //imposto di default il valolore della
        if (((User) o).getLid() == 0)
        {

        }

        //se manca lo usernname non posso eseguire verifiche sulla presenza o meno di altri utenti con lo stesso username
        if(!StringUtils.hasLength(((User) o).getUsername())) {
            errors.rejectValue("username", null, "validation.user.username");
            return;
        }

        if(!StringUtils.hasLength(((User) o).getMail())) {
            errors.rejectValue("mail", null, "validation.user.mail");
        }
//        if(((User) o).getSmsNumber() != null && (!StringUtils.hasLength(((User) o).getSmsNumber()) || !Pattern.matches(NUMBER_SMS_REGEX, ((User) o).getSmsNumber()))) {
//            errors.rejectValue("smsNumber", null, "validation.user.smsnumber");
//        }
        if(!StringUtils.hasLength(((User) o).getPassword())) {

            if (((User)o).getLid() > 0){
                //se sto aggiornando mi prendo la sua vecchia password
                User persistentUser = userRep.get(((User)o).getId()).get();
                ((User)o).setPassword(persistentUser.getPassword());
            }else{
                errors.rejectValue("password", null, "validation.user.password");
            }

        }else{
            //imposot io la password criptandola però prima inserisco la password decriptata
            ((User)o).setDecPass(((User)o).getPassword());
            String encodedPassword = new Md5PasswordEncoder().encodePassword(((User)o).getPassword(), null);
            ((User)o).setPassword(encodedPassword);

        }
        if(!StringUtils.hasLength(((User) o).getName())) {
            errors.rejectValue("name", null, "validation.user.name");
        }

        if(!StringUtils.hasLength(((User) o).getSurname())) {
            errors.rejectValue("surname", null, "validation.user.surname");
        }


        //se manca il ruolo devo uscire perche non posso fare gli altri check sulla evebnntuale modifica del ruolo
        if(((User) o).getRoles().size() == 0) {
            errors.rejectValue("roles", null, "validation.user.rolemissing");
            return;
        }


        //se sono arrivato qui vuol dire che sicuramente ho un ruolo impostato e uno username




        //per lo username devo verificare che non esistano altri utenti con lo stesso nome
        if (checkUsernameIsInUse((User) o)){
            errors.rejectValue("username", null, "validation.user.userinuse");
            return;
        }


        //devo adesso verificare che, in caso di aggiornamento, se si trattadi asggiornare utenti con
        // ruoli di amministratore (id 1 ) o amministratore della società (id 2)
        //non è possibile un cmabio di ruolo
        if (checkAdminRoleChanging((User) o)){
            errors.rejectValue("roles", null, "validation.user.rolechange");
            return;
        }


        //verifico se l'utente loggsato puo' creare o aggiornare un utente
        if (isUserCreationOrUpdateForbiden((User) o)){
            errors.rejectValue("roles", null, "validation.user.usercreationforbidden");
            return;
        }

        // //iunltre l'admin, nel momento in cui crea o aggiorna un amminstratore della società
        //deve impostare necessariamente una company per lutente. Alla stessa maniera l'amministratore della società
        //puo' solo creare o aggiornare utenti per la stessa società
        if (!isCompanyCompliant((User)o)){
            errors.rejectValue("company", null, "validation.user.incorrectcompany");
            return;
        }



    }







    private boolean isCompanyCompliant(User o) {

        Role userLoggedRole = (Role)secure.getLoggedUser().getRoles().get(0);
        Role role = (Role)((User)o).getRoles().get(0);

        //se sono amministratore
        if (userLoggedRole.getLid() == 1) {
            //posso creare amministratori della società con una azienda obbligatoria
            if (role.getLid() == 2 ){
                if (o.getCompany() == null)
                    return false;
            }

            return true;

        }else if (userLoggedRole.getLid() == 2) {



            //imposto direttamente la società alla società dell'amministratore senza pensarciiiiii
            //non è carino ma puo' andare!!!!!!!
            o.setCompany(((User)secure.getLoggedUser()).getCompany());

            return true;
        }
        return true;

    }

    private boolean isUserCreationOrUpdateForbiden(User o) {
        Role userLoggedRole = (Role)secure.getLoggedUser().getRoles().get(0);
        Role role = (Role)((User)o).getRoles().get(0);

        //se sto creando un admin o un amministratore della socieà l'utente loggato non poò che
        //essere un admin


        if (userLoggedRole.getLid() == 1) {
            //posso creare solo admin o adminstrtor della società
            if (role.getLid() != 1 && role.getLid() != 2 ){
                return true;
            }

            return false;

        }else if (userLoggedRole.getLid() == 2) {

            //se sto aggiornando un ruolo amministratore della società posso farlo
            if (role.getLid() == 2  && o.getLid() >0){
                return false;
            }

            //se un amministratore della società tenta di creare un altro amministratoree della società o un admin
            //allora lancio l'eccezione
            if (role.getLid() == 1 || role.getLid() == 2 ){
                return true;
            }

            return false;
        }
        else{
            //negli altri casi nonho privilegi
            //questa riga è ridondante perchè è scritta nei permessi statici
            return true;
        }
    }

    private boolean checkAdminRoleChanging(User o) {
        if (((User)o).getLid() > 0){
            User temp = userRep.get(o.getLid()).get();
            Role l = (Role)temp.getRoles().get(0);
            Role l1 = (Role)o.getRoles().get(0);
            if (l.getIid() == 1 || l.getLid() == 2 )
                if (l.getLid() != l1.getLid()){

                    return true;
                }

        }
        return false;
    }

    private boolean checkUsernameIsInUse(User o) {
        User temp = userRep.getUserByUsername(o.getUsername());
        if (temp == null)
            return false;
        if (o.getLid()>0){ //sto aggiornamndo....
            //se sto aggiornando l'unico user con lo stesso username deve avere lo stesso id
            //se cosi non è lancio una validation exception..
            if (o.getLid() == temp.getLid())
                return false;

            return true;

        }

        //in questo caso sto inserendo un nuovo utente e poichè esiste gia un utilizzo dello username...
        return true;


    }
}
