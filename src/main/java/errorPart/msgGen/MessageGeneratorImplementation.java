package errorPart.msgGen;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import errorPart.enums.MessageType;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageGeneratorImplementation implements MessageGenerator{
    private ArrayList<ISubscriber> subs;
    Notification notification;
    Message msg;
    public MessageGeneratorImplementation(){
        this.subs = new ArrayList<>();
        notification = null;
        this.msg = null;
    }

    @Override
    public void generateMessage(EventType eventType) {
        if (eventType==EventType.CANNOT_ADD_CHILD_TO_LEAF) {
            msg = new Message(MessageType.ERROR, new Timestamp(System.currentTimeMillis()), eventType.toString());
            notification = new Notification(msg, NotificationType.CANNOT_ADD_CHILD_TO_LEAF);
//            ApplicationFramework.getInstance().setMessage(new Message(MessageType.ERROR,
//                    new Timestamp(System.currentTimeMillis()), eventType.toString()));
        }
        else if (eventType==EventType.CANNOT_REMOVE_ROOT) {
            msg = new Message(MessageType.ERROR, new Timestamp(System.currentTimeMillis()), eventType.toString());
            notification = new Notification(msg, NotificationType.CANNOT_REMOVE_ROOT);
//            ApplicationFramework.getInstance().setMessage(new Message(MessageType.ERROR,
//                    new Timestamp(System.currentTimeMillis()), eventType.toString()));
        }
        else if (eventType==EventType.NAME_EMPTY) {
            msg = new Message(MessageType.WARNING, new Timestamp(System.currentTimeMillis()), eventType.toString());
            notification = new Notification(msg, NotificationType.NAME_EMPTY);
//            ApplicationFramework.getInstance().setMessage(new Message(MessageType.WARNING,
//                    new Timestamp(System.currentTimeMillis()), eventType.toString()));
        }
        else if(eventType == EventType.PROJECT_NOT_SELECTED){
            msg = new Message(MessageType.ERROR, new Timestamp(System.currentTimeMillis()), eventType.toString());
            notification = new Notification(msg, NotificationType.PROJECT_NOT_SELECTED);
        }
        else if(eventType == EventType.DIAGRAM_NOT_SELECTED){
            msg = new Message(MessageType.WARNING, new Timestamp(System.currentTimeMillis()), eventType.toString());
            notification = new Notification(msg, NotificationType.DIAGRAM_NOT_SELECTED);
        }
        notifySubscribers(notification);
    }

    @Override
    public Notification getNotification() {
        return notification;
    }

    @Override
    public void addSubscriber(ISubscriber iSubscriber) {
        this.subs.add(iSubscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber iSubscriber) {
        this.subs.remove(iSubscriber);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for(ISubscriber sub: this.subs){
            sub.update(notification);
        }
    }
}
