package errorPart.msgGen;

import errorPart.enums.MessageType;

import java.sql.Timestamp;

public class Message {
    public MessageType messageType;
    public Timestamp timestamp;
    public String msg;
    public Message(MessageType messageType, Timestamp timestamp, String msg) {
        this.messageType= messageType;
        this.timestamp = timestamp;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "["+messageType.toString() +"] [" + timestamp.toString() + "] [" + msg + "]";
    }

}
