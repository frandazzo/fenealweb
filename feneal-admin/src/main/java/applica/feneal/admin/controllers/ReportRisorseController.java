package applica.feneal.admin.controllers;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class ReportRisorseController {

    @Autowired
    private ViewResolver viewResolver;

    @RequestMapping(value = "risorse", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewListContact(HttpServletRequest request) {

        try {

            HashMap<String, Object> model = new HashMap<String, Object>();
            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "risorseumane/risorseumane", model, LocaleContextHolder.getLocale(), request);

            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

}
