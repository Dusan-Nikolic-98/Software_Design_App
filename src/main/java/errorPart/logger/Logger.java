package errorPart.logger;

import errorPart.msgGen.Message;
import observer.ISubscriber;

public interface Logger extends ISubscriber {
    void log(Message msg);
}
