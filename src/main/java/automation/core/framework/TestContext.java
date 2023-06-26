package automation.core.framework;

import automation.selenium.BrowserProvider;
import io.cucumber.java.Scenario;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;

@Component
public class TestContext {

    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);


    private String scenarioName;
    private WebDriver webDriver;

    private final String BROWSER_UNDER_TEST = System.getenv("Browser_Under_Test");
    private final String WEB_DRIVER_ENDPOINT = System.getenv("Browser_Endpoint");


    public void setScenario(Scenario scenario) {
        this.scenarioName = scenario.getName();
    }

    public String getScenarioName() {
        return scenarioName;
    }


    public WebDriver createWebDriver() {
        BrowserProvider browserProvider = Optional.ofNullable(BROWSER_UNDER_TEST).map(String::toUpperCase).map(BrowserProvider::valueOf).orElse(BrowserProvider.CHROME);
        int retryCount = 0;
        int maxRetries = 2;

        String DEFAULT_WEB_DRIVER_ENDPOINT = WEB_DRIVER_ENDPOINT==null ? "http://localhost:4444/wd/hub" : WEB_DRIVER_ENDPOINT;

        while (retryCount < maxRetries) {
            try {
                webDriver = Optional.of(DEFAULT_WEB_DRIVER_ENDPOINT)
                        .map(TestContext::toUrl)
                        .map(u -> (WebDriver) browserProvider.createRemoteWebDriver(u)).orElseGet(browserProvider::createWebDriver);
                webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
                webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                webDriver.manage().window().maximize();
                break;
            } catch (WebDriverException e) {
                retryCount++;
                if (retryCount == maxRetries) {
                    throw new SessionNotCreatedException("The Session Not Created or Webdriver Exception was thrown twice and the cause is : " + e);
                }
            }
        }
        return webDriver;
    }

    private static URL toUrl(String potentialUrl){
        try{
            return new URL(potentialUrl);
        } catch (MalformedURLException e){
            throw new IllegalStateException("Invalid URL" + e);
        }
    }


    public WebDriver getWebDriver() {
        synchronized (this) {
            if (webDriver == null) {
                webDriver = createWebDriver();
            }
            return webDriver;
        }
    }

    public synchronized DevTools getDevTools() {
        webDriver =  new Augmenter().augment(webDriver);

        DevTools devTools = ((HasDevTools) webDriver).getDevTools();
        try {
            devTools.createSessionIfThereIsNotOne();
        } catch (Exception e) {
            logger.warn("devtool session creation failed " + e);
        }
        return devTools;
    }




    public boolean isWebDriverInitialised() {
        return webDriver != null;
    }
}
