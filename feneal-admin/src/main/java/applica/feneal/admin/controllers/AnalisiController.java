package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.domain.model.User;
import applica.feneal.services.AnalisysService;
import applica.feneal.services.StatisticService;
import applica.framework.library.options.OptionsManager;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class AnalisiController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private Security sec;

    @Autowired
    private OptionsManager optMan;

    @Autowired
    private AnalisysService statServ;

    @RequestMapping(value = "/analisi/riepilogo",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse riepilogo(HttpServletRequest request) {

        try {

            HashMap<String, Object> model = new HashMap<String, Object>();

            String view = "analisi/riepilogo";

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, view, model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }



    @RequestMapping(value = "/analisi/pivot",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse pivot(HttpServletRequest request) {

        try {

            String urlAnalisiIscritti = optMan.get("data.analisiiscritti.url");

            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("datasource", urlAnalisiIscritti);

            manageActivity();

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "analisi/pivot", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value = "/analisi/analisiiscritticompleti",method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse analisiIscrittiCompleti(HttpServletRequest request) {

        try {

            String urlAnalisiIscritti = optMan.get("data.analisiiscritti.url");

            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("datasource", urlAnalisiIscritti);

            manageActivity();

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "analisi/analisiiscritticompleti", model, LocaleContextHolder.getLocale(), request);
            return new ValueResponse(content);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }


    @RequestMapping(value="/analisi/riepilogo/categoria", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse getIscrittiPerCategoria(@RequestParam(value="year", required=true) int year,
                                                                @RequestParam(value="region", required=false) String region
    ) {




        try {

            return new ValueResponse(statServ.getIscrittiPerSettore(year, region));
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/analisi/riepilogo/areageografica", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse getIscrittiPerAreaGeografica(@RequestParam(value="year", required=true) int year,
                                                                     @RequestParam(value="region", required=false) String region) {

        try {

            return new ValueResponse(statServ.getIscrittiPerAreaGeografica(year, region));
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value="/analisi/riepilogo/anni", method= RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody SimpleResponse getAnniIscrizioni() {

        try {

            return new ValueResponse(statServ.getAnniIscrizioni());
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    private void manageActivity() {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            traceFacade.traceActivity(user, "Analisi iscritti", null, null);

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

}

