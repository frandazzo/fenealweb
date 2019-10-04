package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.TraceFacade;
import applica.feneal.admin.viewmodel.app.dashboard.lavoratori.UiLavoratoriForExport;
import applica.feneal.admin.viewmodel.trace.UIUserActivityTrace;
import applica.feneal.admin.viewmodel.trace.UiTraceLoginView;
import applica.feneal.admin.viewmodel.trace.UiUserActivityTraceView;
import applica.feneal.domain.model.User;
import applica.feneal.domain.model.core.TraceLogin;
import applica.feneal.services.TraceService;
import applica.framework.library.responses.ErrorResponse;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import applica.framework.library.ui.PartialViewRenderer;
import applica.framework.security.Security;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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



    // da qui

//    @RequestMapping(value = "/estraifile", method = RequestMethod.GET)
//    @PreAuthorize("isAuthenticated()")
//    public @ResponseBody
//    SimpleResponse estraiFile(HttpServletRequest request) {
//
//        try {
//
//            UiEstraiFile newFile = new UiEstraiFile();
//
//            HashMap<String, Object> model = new HashMap<String, Object>();
//            PartialViewRenderer renderer = new PartialViewRenderer();
//            String content = renderer.render(viewResolver, "trace/traceLogins", model, LocaleContextHolder.getLocale(), request);
//            newFile.setContent(content);
//
//            String path = "C:/Users/felic/Desktop";
//            File selec = new File(path+"/EROGAZIONIESATTO.txt");
//            List<String> fileSelected = transformFile(selec);
//
//            List<lavFile> lavList = convertFile(fileSelected);
//            newFile.setLavFile(lavList);
//
//            return new ValueResponse(newFile);
//        } catch(Exception e) {
//            e.printStackTrace();
//            return new ErrorResponse(e.getMessage());
//        }
//
//    }

//    private List<lavFile> convertFile(List<String> fileSelected) throws ParseException {
//
//        List<lavFile> result = new ArrayList<>();
//        for(String r : fileSelected) {
//
//            for (int i = 0; i < r.length() - 1; i++) {
//                char c = r.charAt(i);
//                char c2 = r.charAt(i + 1);
//                if (StringUtils.equals(String.valueOf(c), "0") && StringUtils.equals(String.valueOf(c2), "0") && i < r.length() - 2) {
//                    char c3;
//                    char c4;
//                    if(!StringUtils.isEmpty(String.valueOf(r.charAt(i + 2))) && !StringUtils.isEmpty(String.valueOf(r.charAt(i - 1)))) {
//                         c3 = r.charAt(i + 2);
//                         c4 = r.charAt(i - 1);
//                    }else{
//                         c3 = r.charAt(i);
//                         c4 = r.charAt(i);
//                    }
//
//                    if(StringUtils.equals(String.valueOf(c3), " ") && StringUtils.equals(String.valueOf(c4), ",")){
//                        StringBuilder sb = new StringBuilder();
//                        StringBuilder d = new StringBuilder();
//                        for (int j = i + 3; j < r.length(); j++) {
//                            if (Character.isLetter(r.charAt(j))) {
//                                sb.append(r.charAt(j));
//                            } else if(StringUtils.equals(String.valueOf(r.charAt(j)), "\'") || StringUtils.equals(String.valueOf(r.charAt(j)), "-")) {
//                                sb.append(r.charAt(j));
//                            }
//                            else if(StringUtils.equals(String.valueOf(r.charAt(j)), " ") && Character.isLetter(r.charAt(j+1))){
//                                sb.append(r.charAt(j));
//                            }
//                            else if(j + 6 < r.length()) {
//                                d.append(r.charAt(j+1));
//                                d.append(r.charAt(j + 2));
//                                d.append("/");
//                                d.append(r.charAt(j + 3));
//                                d.append(r.charAt(j + 4));
//                                d.append("/");
//                                d.append(r.charAt(j + 5));
//                                d.append(r.charAt(j + 6));
//                            } else{
//                                j = r.length() - 1;
//                            }
//                        }
//                        String data = d.toString();
//                        String str = sb.toString();
//
//
//                        if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty(data)) {
//                            lavFile lav = new lavFile();
//                            lav.setData(data);
//                            lav.setNominativo(str);
//                            result.add(lav);
//                        }
//                    }
//
//                    }
//                }
//            }
//        return result;
//    }


//a qui cancellare

//    private List<String> transformFile(File myfile) throws Exception{
//        BufferedReader abc = new BufferedReader(new FileReader(myfile));
//        List<String> data = new ArrayList<String>();
//        String s;
//        while((s=abc.readLine())!=null) {
//            data.add(s);
//            System.out.println(s);
//        }
//        abc.close();
//
//        return data;
//    }
}
