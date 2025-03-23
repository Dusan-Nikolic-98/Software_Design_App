package errorPart.logger;

import core.ApplicationFramework;
import errorPart.msgGen.Message;
import observer.Notification;

import java.io.PrintWriter;

public class FileLogger implements Logger{
    @Override
    public void update(Notification notification) {
        Message msg = (Message) notification.getObjOfNotification();
        log(msg);
    }
    @Override
    public void log(Message msg) {
        try(PrintWriter pw = new PrintWriter("log.txt")){
            pw.println(msg);
            pw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
