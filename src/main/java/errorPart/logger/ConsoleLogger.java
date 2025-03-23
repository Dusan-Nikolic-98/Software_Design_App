package errorPart.logger;

import core.ApplicationFramework;
import errorPart.msgGen.Message;
import observer.Notification;

public class ConsoleLogger implements Logger{

    @Override
    public void update(Notification notification) {
        Message msg = (Message) notification.getObjOfNotification();
        log(msg);
    }

    @Override
    public void log(Message msg) {
        System.out.println("console logger: " + msg);
    }
}
