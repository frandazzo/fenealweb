package applica.feneal.admin.viewmodel;

import applica.feneal.domain.model.core.Notification;

import java.util.List;

/**
 * Created by fgran on 08/04/2016.
 */
public class UiNotificationData {

    private int unread;
    private List<Notification> notifications;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
