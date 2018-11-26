package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiLavoratori;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiLavoratoriForExport;
import applica.feneal.admin.viewmodel.trace.UIUserActivityTrace;
import applica.feneal.admin.viewmodel.trace.UiTraceLoginView;
import applica.feneal.admin.viewmodel.trace.UiUserActivityTraceView;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.TraceLogin;
import applica.feneal.domain.model.core.UserActivityTrace;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.services.TraceService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by angelo on 18/11/2017.
 */
@Controller
public class TraceController {

    @Autowired
    private ViewResolver viewResolver;

    @Autowired
    private TraceFacade traceFacade;

    @Autowired
    private TraceService traceServ;


    @RequestMapping(value = "/trace/logins", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewTraceLogins(HttpServletRequest request) {

        try {

            UiTraceLoginView traceLoginView = new UiTraceLoginView();
            List<TraceLogin> traceLogins = traceServ.retrieveTraceLogins();

            HashMap<String, Object> model = new HashMap<String, Object>();

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "trace/traceLogins", model, LocaleContextHolder.getLocale(), request);
            traceLoginView.setTraceLogins(traceLogins);
            traceLoginView.setContent(content);

            return new ValueResponse(traceLoginView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/trace/activities", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse viewUserTraceActivities(HttpServletRequest request, Long userId) {

        try {

            UiUserActivityTraceView userTraceActivitiesView = new UiUserActivityTraceView();
            List<UIUserActivityTrace> traceActivities = traceFacade.retrieveUserActivitiesTrace(userId);

            HashMap<String, Object> model = new HashMap<String, Object>();

            PartialViewRenderer renderer = new PartialViewRenderer();
            String content = renderer.render(viewResolver, "trace/traceActivities", model, LocaleContextHolder.getLocale(), request);
            userTraceActivitiesView.setTraceActivities(traceActivities);
            userTraceActivitiesView.setContent(content);

            return new ValueResponse(userTraceActivitiesView);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }

    }

    @RequestMapping(value = "/trace/{id}/detail", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse retrieveActivityDetail(@PathVariable long id, HttpServletRequest request) {
        try {
            String activityDetail = traceServ.getActivityDetail(id);
            return new ValueResponse(activityDetail);
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = "/trace/exportexcel", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    SimpleResponse traceExportExcel(@RequestBody UiLavoratoriForExport data, HttpServletRequest request) {
        try {
            manageActivityExportExcel(data);
            return new ValueResponse("ok");
        } catch(Exception e) {
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }


    private void manageActivityExportExcel(UiLavoratoriForExport data) {

        try {
            User user = (User) Security.withMe().getLoggedUser();

            String activity = String.format("Esportazione dati Excel (contesto: %s)", data.getContext());
            traceFacade.traceActivity(user, activity, createDetailForExportExcel(data.getFiscalCodeRows()), null);

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

    private String createDetailForExportExcel(List<String> rows) {

        StringBuilder b = new StringBuilder();
        b.append("Nell'esportazione in Excel sono stati stampati i seguenti iscritti: <br><br>");

        for (String cf : rows) {   // C.F.
            b.append(String.format("%s <br>", cf));
        }

        return b.toString();
    }
}


