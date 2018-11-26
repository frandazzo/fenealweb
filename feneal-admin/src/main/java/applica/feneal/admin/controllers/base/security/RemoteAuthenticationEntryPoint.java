package applica.feneal.admin.controllers.base.security;

import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.security.Security;
import applica.framework.security.token.AuthTokenGenerator;
import applica.framework.security.token.DefaultAuthTokenGenerator;
import applica.framework.security.token.TokenGenerationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Applica
 * User: Ciccio Randazzo
 * Date: 10/11/15
 * Time: 3:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class RemoteAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
        try {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}