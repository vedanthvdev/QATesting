package automation.core.framework;

import java.util.Date;

import automation.context.ContextManager;
import automation.core.AutomationException;
import automation.petapi.PetClientService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestHooks extends BaseStepDef {

    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);

    private final PetClientService petClientService;


    public TestHooks(ContextManager contextManager, PetClientService petClientService) {
        super(contextManager);
        this.petClientService = petClientService;
    }

    @Before(order = 0)
    public void before(Scenario scenario) {
        getContextManager().resetContext();

        TestContext testContext = new TestContext();
        getContextManager().setContext(TestContext.class, testContext);

        testContext.setScenario(scenario);
        logger.info("Running scenario named {}", testContext.getScenarioName());
    }

    @Before
    public void apisetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
        RestAssured.authentication.equals(getToken());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", getToken())
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    @After(order = 4)
    public void captureScreenshot(Scenario scenario) {
        TestContext testContext = getContextManager().getRequiredContext(TestContext.class);
        try {
            if (testContext.isWebDriverInitialised()) {
                LogEntries logEntries = testContext.getWebDriver().manage().logs().get(LogType.BROWSER);
                for (LogEntry entry : logEntries) {
                    logger.info("{}, {}, {}", new Date(entry.getTimestamp()), entry.getLevel(), entry.getMessage());
                }
            }
        } catch (Exception ex) {
            logger.warn("Could not extract browser logs", ex);
        } finally {
            try {
                if (scenario.isFailed() && testContext.isWebDriverInitialised()) {
                    final byte[] screenshot = ((TakesScreenshot) testContext.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName());
                }
            } catch (AutomationException e) {
                logger.warn("Could not capture screenshot" + e.getMessage(), e);
            }
        }

    }

    @After(order = 0)
    public void driverQuit() {
        TestContext testContext = getContextManager().getRequiredContext(TestContext.class);
        try {
            if (testContext.isWebDriverInitialised()) {
                WebDriver driver = testContext.getWebDriver();
                driver.close();
                driver.quit();
            }
        } catch (AutomationException e) {
            logger.warn("Could not close webdriver" + e.getMessage(), e);
        }
        getContextManager().removeContext();
    }

    @After("@delete_all_pets")
    public void deletePets() {
        petClientService.deleteAllPets();
    }

    private String getToken() {
        return "special-key";
    }

}
