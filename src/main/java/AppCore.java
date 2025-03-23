import core.ApplicationFramework;
import core.ClassyRepository;
import core.Gui;
import core.Serializer;
import errorPart.logger.Logger;
import errorPart.logger.SimpleLoggerFactory;
import gui.swing.SwingGui;
import repository.ClassyRepositoryImplementation;
import serializer.GsonSerializer;

public class AppCore {
    public static void main(String[] args) {
        ApplicationFramework appCore = ApplicationFramework.getInstance();
        Gui gui = new SwingGui();
        ClassyRepository classyRepository = new ClassyRepositoryImplementation();
        SimpleLoggerFactory simpleLoggerFactory = new SimpleLoggerFactory();
        Logger fileLogger = simpleLoggerFactory.createLogger("filelogger");
        Logger consoleLogger = simpleLoggerFactory.createLogger("consolelogger");
        Serializer serializer = new GsonSerializer();
        appCore.init(gui, classyRepository, fileLogger, consoleLogger, serializer);

        appCore.run();
    }
}