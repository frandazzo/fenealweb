package applica.feneal.services;

import applica.feneal.domain.model.core.Notification;
import applica.feneal.domain.model.dbnazionale.UtenteDbNazionale;

import java.util.List;

/**
 * Created by fgran on 08/04/2016.
 */
public interface NotificationService {

    void setNotificationRead(long notificationId);

    List<Notification> getLastNotifications();

    Notification getNotificationById(long notificationId);

    void createSignalWorkerNotification(Long workerId, String province, String text) throws Exception;

    void sendNotificationMail(Long workerId, String mail, String text) throws Exception;

    void sendThanksReportingMail(long notificationId) throws Exception;

    void sendNotificationMailToCompanies(List<UtenteDbNazionale> lavs);

    void notifyImportDbNazionale(Integer id);
}
