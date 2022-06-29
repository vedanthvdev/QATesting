package automation.core.framework;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestStepFinished;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionEventListener implements ConcurrentEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionEventListener.class);

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepFinished.class, e -> {
            if (e.getResult().getError() != null) {
                logger.error("Failure during test step", e.getResult().getError());
            }
        });
        publisher.registerHandlerFor(TestCaseFinished.class, e -> {
            if (e.getResult().getError() != null) {
                logger.error("Failure during test case", e.getResult().getError());
            }
        });
    }
}
