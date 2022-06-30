package automation.core.framework;

import automation.petservice.pet.PetClientService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class TestHooks {

    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);
    private final TestContext testContext;
    private final PetClientService petClientService;

    public TestHooks(TestContext testContext, PetClientService petClientService) {
        this.testContext = testContext;
        this.petClientService = petClientService;
    }

    @Before
    public void before(Scenario scenario) {
        testContext.setScenario(scenario);
//        testContext.createWebDriver();
        logger.info("Running scenario named {}", testContext.getScenarioName());
    }

    @Before
    public void apisetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";
        RestAssured.authentication.equals("specia-key");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", getToken())
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    @After("@delete_all_pets")
    public void deletePets() {
        petClientService.deleteAllPets();
    }

    private String getToken() {
        return "special-key";
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
//        try {
//            if (testContext.isWebDriverInitialised()) {
//                LogEntries logEntries = testContext.getWebDriver().manage().logs().get(LogType.BROWSER);
//                for (LogEntry entry : logEntries) {
//                    logger.info("{}, {}, {}", new Date(entry.getTimestamp()), entry.getLevel(), entry.getMessage());
//                }
//            }
//        } catch (Exception ex) {
//            logger.warn("Could not extract browser logs", ex);
//        }
//        try {
//            if (scenario.isFailed()) {
//                if (testContext.isWebDriverInitialised() && testContext.getWebDriver() instanceof TakesScreenshot) {
//                    final byte[] screenshot = ((TakesScreenshot) testContext.getWebDriver()).getScreenshotAs(OutputType.BYTES);
//                    scenario.attach(screenshot, "image/png", scenario.getName());
//                }
//            }
//        } finally {
//            try {
//                if (testContext.isWebDriverInitialised()) {
//                    testContext.getWebDriver().quit();
//                }
//            } catch (AutomationException e) {
//                logger.warn("Could not close webdriver" + e.getMessage(), e);
//            }
//        }
//    }
}
}
