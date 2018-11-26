package applica.feneal.admin.controllers.base.security;

import applica.feneal.admin.utils.FenealDateUtils;
import applica.feneal.domain.model.core.Company;
import applica.feneal.services.TraceService;
import applica.framework.security.Security;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fgran on 14/11/2017.
 */
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private TraceService traceService;

    private Log logger = LogFactory.getLog(getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        try {
            applica.framework.security.User u = Security.withMe().getLoggedUser();
            Company company = ((applica.feneal.domain.model.User)u).getCompany();
            if (company != null) {
                String loggedUserCompany = company.getDescription();
                int year = FenealDateUtils.getCurrentYear();
                int month = FenealDateUtils.getCurrentMonth();
                String monthDescr = FenealDateUtils.getDescriptionByMonthCode(month);

                String header = request.getHeader("content-type");

                if (!StringUtils.isEmpty(header)){
                    if (header.equals("application/x-www-form-urlencoded")){
                        //login da sito
                        logger.info("autenticazione da sito");
                        traceService.traceLogin(false, year, monthDescr, loggedUserCompany);
                    } else {
                        //login da app
                        logger.info("autenticazione da app");
                        traceService.traceLogin(true, year, monthDescr, loggedUserCompany);
                    }
                }
            }
        } catch (Exception e){
            //non fa nulla
            e.printStackTrace();
        }

        super.onAuthenticationSuccess(request, response, authentication);


    }

}
