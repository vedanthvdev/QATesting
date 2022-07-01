package automation.core.framework;

import io.cucumber.core.cli.Main;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Handler;
import java.util.logging.Logger;

public class Launcher {

    public static void main(String... args) {
        removeHandlers(Logger.getLogger(Launcher.class.getName()));
        SLF4JBridgeHandler.install();
        Main.main(args);
    }

    private static void removeHandlers(Logger logger) {
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        if (logger.getParent() != null) {
            removeHandlers(logger.getParent());
        }
    }
}
