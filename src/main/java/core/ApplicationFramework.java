package core;


import errorPart.enums.EventType;
import errorPart.logger.ConsoleLogger;
import errorPart.logger.FileLogger;
import errorPart.logger.Logger;
import errorPart.msgGen.Message;
import errorPart.msgGen.MessageGenerator;
import errorPart.msgGen.MessageGeneratorImplementation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ApplicationFramework {
    protected Gui gui;
    protected ClassyRepository classyRepository;
    protected MessageGenerator messageGenerator; //TODO mejbi
    protected ConsoleLogger consoleLogger;
    protected FileLogger fileLogger;
    protected Serializer serializer;

    private ApplicationFramework(){

    }
    private static ApplicationFramework instance = null;
    public static ApplicationFramework getInstance(){
        if(instance == null)
            instance = new ApplicationFramework();

        return instance;
    }

    //i treba mi jedna za init

    public void init(Gui gui, ClassyRepository classyRepository, Logger l1, Logger l2, Serializer serializer){
        this.gui = gui;
        this.classyRepository = classyRepository;
        this.messageGenerator = new MessageGeneratorImplementation();
        this.messageGenerator.addSubscriber(l1);
        this.messageGenerator.addSubscriber(l2);
        this.serializer = serializer;
    }
    public void run(){this.gui.start();}

}
