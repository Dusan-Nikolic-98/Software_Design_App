package errorPart.logger;

public class SimpleLoggerFactory {

    public SimpleLoggerFactory() {
    }

    public Logger createLogger(String loggerType) {
        if (loggerType.equalsIgnoreCase("FileLogger")) {
            return new FileLogger();
        }
        else if (loggerType.equalsIgnoreCase("ConsoleLogger")) {
            return new ConsoleLogger();
        }
        return null;
    }
}