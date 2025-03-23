package observer;

import lombok.Getter;

@Getter
public class Notification {
    private Object objOfNotification;
    private NotificationType notificationType;

    public Notification(Object objOfNotification, NotificationType notificationType) {
        this.objOfNotification = objOfNotification;
        this.notificationType = notificationType;
    }
    public Notification(NotificationType nt){
        this.notificationType = nt;
    }
    @Override
    public String toString(){
        return notificationType.toString();
    }
}
