package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.UsersFacade;
import applica.feneal.admin.facade.VersamentiFacade;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuota;
import applica.feneal.admin.viewmodel.quote.UiDettaglioQuotaView;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by angelo on 20/11/2017.
 */
@Controller
public class UsersController {

    @Autowired
    private UsersFacade usersFacade;


    @RequestMapping(value = "/user/{id}/changestatus")
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse changeStatus(@PathVariable("id") Long userId, HttpServletRequest request) {

        try {
            usersFacade.changeStatus(userId);

            return new ValueResponse();
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/user/{id}/changepassword", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse changePassword(@PathVariable("id") Long userId, String newPassword, HttpServletRequest request) {

        try {
            usersFacade.changePassword(userId, newPassword);

            return new ValueResponse();
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


}
