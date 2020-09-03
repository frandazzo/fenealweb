package applica.feneal.admin.facade;

import applica.feneal.admin.viewmodel.UiNotificationData;
import applica.feneal.domain.data.core.deleghe.DelegheRepository;
import applica.feneal.domain.data.core.lavoratori.LavoratoriRepository;
import applica.feneal.domain.model.core.Notification;
import applica.feneal.domain.model.core.deleghe.Delega;
import applica.feneal.domain.model.core.lavoratori.Lavoratore;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;
import applica.feneal.services.NotificationService;
import applica.framework.LoadRequest;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fgran on 08/04/2016.
 */
@Component
public class NotificationsFacade {

    @Autowired
    private DelegheRepository del;

    @Autowired
    private NotificationService notServ;

    @Autowired
    private LavoratoriRepository lavRep;

    public UiNotificationData getNotificationData(){
        List<Notification> not = notServ.getLastNotifications();

        UiNotificationData s = new UiNotificationData();

        List<Notification> unreads = not.stream().filter(f -> !f.isRead()).collect(Collectors.toList());
        s.setUnread(unreads.size());
        s.setNotifications(not);

        return s;

    }

    private List<Notification> getFakes(){

        List<Notification> not = new ArrayList<>();

        Notification n = new Notification();
        n.setId(12);
        n.setDate(new Date());
        n.setRead(true);
        n.setBody("xxxxxxxxxx;ccccccccccc");
        n.setType(Notification.notification_newexport);
        n.setDescription("La provincia di <b>Matera</b> ha provveduto ad esportare i data relativi al semetre 1 2016 per la <b> cassa edile </b> e ci sono 15 lavoratori residenti nel tuo territorio. Guarda chi sono!");

        not.add(n);


        Notification n1 = new Notification();
        n1.setId(23);
        n1.setDate(new Date());
        n1.setRead(false);
        n1.setBody("xxxxxxxxxx;ccccccccccc");
        n1.setType(Notification.notification_sendworkerinfo);
        n1.setDescription("Maurizio D'aurelio ti ha segnalato il lavoratore <b>Francesco Randazzo</b>: dacci una occhiata...");

        not.add(n1);

        return not;
    }

    public Notification getNotificationById(long notificationId) {
//        if (notificationId == 23)
//            return getFakes().get(1);
//        else
//            return getFakes().get(0);
        return notServ.getNotificationById(notificationId);

    }

    public void signalWorker(Long workerId, String province, String mail, String text) throws Exception {
        //la segnalazione di un utente implica l'invio di una mail se l'utente risiede in una provincia
        //per cui non esiste un territorio in fenealweb
        //altrimenti devo inviare una notifica a tutti gli utenti della company

        if (StringUtils.isEmpty(mail))
            notServ.createSignalWorkerNotification(workerId, province, text);
        else
            notServ.sendNotificationMail(workerId, mail, text);
    }

    public void notifyImportDBNazionale(Integer id) {

        notServ.notifyImportDbNazionale(id);

    }

    public int getNumberOfUnknownWorkers(Notification n) {
        String[] fiscalCodes = n.getBody().split(";");
        int result = 0;
        for (String fiscalCode : fiscalCodes) {
            Lavoratore l = lavRep.find(LoadRequest.build().filter("fiscalcode", fiscalCode)).findFirst().orElse(null);
            if (l == null)
                result++;
        }
        return result;
    }

    public int getNumberOfknownWorkersWithoutDelega(Notification n) {
        String[] fiscalCodes = n.getBody().split(";");
        int result = 0;
        for (String fiscalCode : fiscalCodes) {
            Lavoratore l = lavRep.find(LoadRequest.build().filter("fiscalcode", fiscalCode)).findFirst().orElse(null);
            if (l != null){

                //ricercol  edeleghe
                List<Delega> delelgh = del.getDelegheByLavoratore(l.getLid());
                if (delelgh.size() == 0)
                    result++;
            }

        }
        return result;
    }

    public int getNumberOfknownWorkers(Notification n) {
        String[] fiscalCodes = n.getBody().split(";");
        int result = 0;
        for (String fiscalCode : fiscalCodes) {
            Lavoratore l = lavRep.find(LoadRequest.build().filter("fiscalcode", fiscalCode)).findFirst().orElse(null);
            if (l != null){

                //ricercol  edeleghe
                List<Delega> delelgh = del.getDelegheByLavoratore(l.getLid());
                if (delelgh.size() > 0)
                    result++;
            }

        }
        return result;
    }

    public void sendThanksReporting(Long notificationId) throws Exception {

        notServ.sendThanksReportingMail(notificationId);
    }

    /* Notificazione da parte di una company del fatto che essa sta visualizzando i lavoratori di altre company */
    public void notifyCompanies(List<UtenteDbNazionale> lavs) {
        notServ.sendNotificationMailToCompanies(lavs);
    }
}
