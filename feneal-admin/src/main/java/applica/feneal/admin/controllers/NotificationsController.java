package applica.feneal.admin.controllers;

import applica.feneal.admin.facade.NotificationsFacade;
import applica.feneal.domain.data.core.NotificationScheduleRepository;
import applica.feneal.domain.model.core.Notification;
import applica.feneal.domain.model.core.NotificationSchedule;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.services.LavoratoreService;
import applica.framework.Filter;
import applica.framework.LoadRequest;
import applica.framework.library.responses.SimpleResponse;
import applica.framework.library.responses.ValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.GET;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fgran on 08/04/2016.
 */
@Controller
public class NotificationsController {

    @Autowired
    private NotificationsFacade notFac;

    @Autowired
    private LavoratoreService lavSvc;

    @Autowired
    private NotificationScheduleRepository notScheduleRep;


    @RequestMapping(value = "/notify/import/dbnazionale/{id}")
    public @ResponseBody  String showNotification(@PathVariable Integer id) throws Exception {

        try{

            NotificationSchedule d = new NotificationSchedule();
            d.setImportId(id);

            //Calcolo la data di esecuzione
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.HOUR, 1);
            Date d1 = c.getTime();

            d.setScheduledDate(d1);

            notScheduleRep.save(d);



            return "ok";
        }catch(Exception e){
            return e.getMessage();
        }



    }


    //OGNI MINUTO
    @Scheduled(fixedDelay=60000)
    public void RetrieveNotificationsToSendFromScheduledNotifications() {


        //recupero la lista degli scheduling da effettuare
        LoadRequest req = LoadRequest.build().filter("scheduledDate", new Date(), Filter.LT);

        List<NotificationSchedule> schedules =  notScheduleRep.find(req).getRows();

        for (NotificationSchedule schedule : schedules) {
            notFac.notifyImportDBNazionale(schedule.getImportId());
            notScheduleRep.delete(schedule.getLid());
        }




    }

    @RequestMapping(value="/notification/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String showNotification(Model model, @PathVariable long id) throws Exception {

        Notification n =  notFac.getNotificationById(id);
        Lavoratore l = lavSvc.getLavoratoreOfOtherCompanyAndCreateItIfNotExistForCurrentCompany(n.getBody());

        model.addAttribute("notification", n);
        model.addAttribute("worker", l);

        if (n.getType().equals(Notification.notification_newexport)){
            //aggiungo le liste dei codici fiscali sconosciuti
            int unknown = notFac.getNumberOfUnknownWorkers(n);
            int withoutDelega = notFac.getNumberOfknownWorkersWithoutDelega(n);
            int known =  notFac.getNumberOfknownWorkers(n);
            model.addAttribute("unknown", unknown );
            model.addAttribute("withoutDelega", withoutDelega);
            model.addAttribute("known",known);
            model.addAttribute("all", unknown + withoutDelega + known);
            //di quelli esistenti ma senza delega
            //e di quelli conosciuti
        }

        if (n.getType().equals(Notification.notification_newexport))
            return "notifications/newimport";

        return "notifications/reportingworker";

    }

    @RequestMapping(value = "/notification/sendthanksreporting", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public
    @ResponseBody
    SimpleResponse sendThanksReporting(Long notificationId) throws Exception {

        notFac.sendThanksReporting(notificationId);

        return new ValueResponse("OK");

    }

}
