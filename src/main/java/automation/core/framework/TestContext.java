package automation.core.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

@Component
public class TestContext {

    private String scenarioName;
    private WebDriver webDriver;

    public void setScenario(Scenario scenario) {
        this.scenarioName = scenario.getName();
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void createWebDriver() {
        WebDriverManager.chromedriver().mac().setup();
        webDriver = new ChromeDriver();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public boolean isWebDriverInitialised() {
        return webDriver != null;
    }
}

