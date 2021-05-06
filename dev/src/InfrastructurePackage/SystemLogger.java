/*
package InfrastructurePackage;

import org.apache.log4j.*;

public class SystemLogger {

    private static Logger logger = Logger.getLogger("Inventory");
    private static boolean setup = false;

    public static void setup(){
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%d{YYYY-mm-dd HH:mm:ss} [%C:%M:%L] - %m%n";
        layout.setConversionPattern(conversionPattern);

        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setFile("InventoryLog.txt");
        fileAppender.setAppend(true);
        fileAppender.setLayout(layout);
        fileAppender.setMaxFileSize("10KB");
        fileAppender.activateOptions();

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(fileAppender);

    }

    public static Logger getLogger() {
        if(!setup) {
            setup();
            setup = true;
        }
        return logger;
    }
}
*/
