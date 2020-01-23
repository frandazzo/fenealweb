package applica.feneal.admin.controllers.base.security;


import applica.feneal.domain.data.UsersRepository;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.utils.UsersDetails;
import applica.framework.LoadRequest;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by applica on 12/15/15.
 */
public class ClientAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsersRepository rep;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SecurityContextHolder.getContext().setAuthentication(null);

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) authentication);
        String username = ((String) token.getPrincipal());
        String password = ((String) token.getCredentials());



        User user = null;
        //devo verificare le username e la password
        User u = rep.getUserByUsername(username);

        if (u == null)
            throw new BadCredentialsException("Bad credentials");
        if (!u.getPassword().equals(password))
            throw new BadCredentialsException("Bad credentials");

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username, password, new ArrayList());
        UsersDetails details = new UsersDetails(user);
        result.setDetails(details);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(result);



        return result;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
