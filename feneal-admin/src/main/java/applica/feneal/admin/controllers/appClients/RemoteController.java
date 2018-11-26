package applica.feneal.admin.controllers.appClients;

import applica.feneal.admin.viewmodel.clientAppViewModels.TestVieModel;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;
import applica.framework.security.token.AuthTokenGenerator;
import applica.framework.security.token.DefaultAuthTokenGenerator;
import applica.framework.security.token.TokenGenerationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fgran on 15/08/2016.
 */
@RestController
@RequestMapping("/api")
public class RemoteController {


    @RequestMapping("/connection")
    @PreAuthorize("isAuthenticated()")
    public SimpleResponse connection(HttpServletRequest request) {


        return new ValueResponse("OK");

    }

    @RequestMapping(value = "/freshToken", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public

    SimpleResponse freshToken() {
        AuthTokenGenerator generator = new DefaultAuthTokenGenerator();
        try {
            return new ValueResponse(generator.generate(Security.withMe().getLoggedUser()));
        } catch (TokenGenerationException e) {
            // LoggerClass.error("Sessione scaduta");
            return new ErrorResponse("Sessione scaduta");
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public
    SimpleResponse testJsnoContentType(TestVieModel test) {

        return  new ValueResponse(String.format("%s - $s", String.valueOf(test.getTestnumber()), test.getTestString()));


    }



}
