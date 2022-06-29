package automation.core.framework;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import automation.core.AutomationException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class TestHooks {

    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);
    private final TestContext testContext;

    public TestHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void before(Scenario scenario) {
        testContext.setScenario(scenario);
//        testContext.createWebDriver();
        logger.info("Running scenario named {}", testContext.getScenarioName());
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        try {
            if (testContext.isWebDriverInitialised()) {
                LogEntries logEntries = testContext.getWebDriver().manage().logs().get(LogType.BROWSER);
                for (LogEntry entry : logEntries) {
                    logger.info("{}, {}, {}", new Date(entry.getTimestamp()), entry.getLevel(), entry.getMessage());
                }
            }
        } catch (Exception ex) {
            logger.warn("Could not extract browser logs", ex);
        }
        try {
            if (scenario.isFailed()) {
                if (testContext.isWebDriverInitialised() && testContext.getWebDriver() instanceof TakesScreenshot) {
                    final byte[] screenshot = ((TakesScreenshot) testContext.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName());
                }
            }
        } finally {
            try {
                if (testContext.isWebDriverInitialised()) {
                    testContext.getWebDriver().quit();
                }
            } catch (AutomationException e) {
                logger.warn("Could not close webdriver" + e.getMessage(), e);
            }
        }
    }
}
