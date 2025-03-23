package errorPart.msgGen;

import errorPart.enums.EventType;
import observer.IPublisher;
import observer.Notification;

import java.sql.Timestamp;

public interface MessageGenerator extends IPublisher {
    void generateMessage(EventType eventType);
    Notification getNotification();
}
