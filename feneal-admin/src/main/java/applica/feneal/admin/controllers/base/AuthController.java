package applica.feneal.admin.controllers.base;

import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.admin.viewmodel.UILogin;
import applica.feneal.domain.model.core.Company;
import applica.feneal.domain.model.utils.UserLoginData;
import applica.feneal.services.TraceService;
import applica.framework.library.i18n.controllers.LocalizedController;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.AuthenticationException;
import applica.framework.security.Security;
import applica.framework.security.User;
import applica.framework.security.token.AuthTokenGenerator;
import applica.framework.security.token.DefaultAuthTokenGenerator;
import applica.framework.security.token.TokenGenerationException;
import applica.framework.widgets.utils.CrudUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * Applica (www.applica.guru)
 * User: bimbobruno
 * Date: 3/19/13
 * Time: 4:22 PM
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends LocalizedController {

    @Autowired
    private RequestCache cacheRepository; //<-- Questa è la repository che permette di gestire la cache

    @Autowired
    private TraceService traceService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        boolean registrationOk = request.getParameter("registrationOk") == "true";
        boolean activationOk = request.getParameter("activationOk") == "true";

        model.addAttribute("registrationOk", registrationOk);
        model.addAttribute("activationOk", activationOk);

        return "/auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, Model model) {
        try {


            UILogin login = (UILogin) CrudUtils.toEntity(UILogin.EID, request.getParameterMap());



            System.out.println(login.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "/auth/login";
    }

    @RequestMapping(value = "/logout_ok", method = RequestMethod.GET)
    public String logoutOk(Model model) {
        return "/auth/logout_ok";
    }

    @RequestMapping("/fail")
    public String fail(HttpServletRequest request, Model model) {
        model.addAttribute("loginError", "bad username or password");

        return "/auth/login";
    }



    //questo metodo è richiamato daal client per autenticarsi sul server remoto

    @RequestMapping(value = "/remotelogin", method = RequestMethod.POST)
    public @ResponseBody
    SimpleResponse login(String mail, String password, HttpServletRequest request, HttpServletResponse response) {
        try{
            //  request e response sono i due parametri che mantengono
            // i valori buffered di input e output dal gestore di HTTP

            cacheRepository.removeRequest(request, response); // <-- Questo metodo provvederà a pulire la cache del buffer di I/O HTTP


            /**
             *  Adesso abbiamo la necessità (qualora dovessimo comunque riscontrare dei valori concatenati da virgola
             *  di pulire le stringhe manualmente (lo so è un operazione forzata ma necessaria a seguito di questo
             *  problema relativo a Spring Framework).
             *
             *  Per fare ciò trimmo dagli spazi sia la variabile mail che password e successivamente vado ad eliminare
             *  tutto ciò che esiste dopo la virgola.
             *
             */

            mail = mail.trim();

            if (mail.contains(",")) {
                mail = mail.split(",")[0].trim();
            }

            password = password.trim();
            if(password.contains(",")){
                password = password.split(",")[0].trim();
            }

            if (StringUtils.isEmpty(mail) || StringUtils.isEmpty(password)) {
                return new ErrorResponse("Username o passworn no nspecificati");
            }

            try {
                Security.manualLogin(mail, password);
            } catch (AuthenticationException e) {
                return new ErrorResponse("Login non riuscito");
            }

            AuthTokenGenerator generator = new DefaultAuthTokenGenerator();
            try {
                UserLoginData d = new UserLoginData();

                User u = Security.withMe().getLoggedUser();
                ((applica.feneal.domain.model.User) u).setPassword(password);
                String token = generator.generate(u);
                d.setToken(token);
                d.setMail(((applica.feneal.domain.model.User)u).getUsername());
                d.setCompany(((applica.feneal.domain.model.User)u).getCompany().getDescription());
                d.setRole(((applica.feneal.domain.model.User)u).retrieveUserRole().getRole());
                d.setName(((applica.feneal.domain.model.User)u).getName());
                d.setSurname(((applica.feneal.domain.model.User)u).getSurname());
                d.setProvinces(((applica.feneal.domain.model.User)u).getCompany().getProvinces().stream().map(f -> f.getDescription()).collect(Collectors.toList()));



                try{
                    Company company = ((applica.feneal.domain.model.User)u).getCompany();
                    String loggedUserCompany = company.getDescription();
                    int year = FenealDateUtils.getCurrentYear();
                    int month = FenealDateUtils.getCurrentMonth();
                    String monthDescr = FenealDateUtils.getDescriptionByMonthCode(month);
                    traceService.traceLogin(true, year, monthDescr, loggedUserCompany);

                }catch(Exception e){

                }











                return new ValueResponse(d);
            } catch (TokenGenerationException e) {
                return new ErrorResponse("Errore nella generazione del token");

            }
        }catch(Exception err){

            return new ErrorResponse(err.getMessage());
        }

    }
}
