package automation.selenium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public enum BrowserProvider {

    FIREFOX {
        @Override
        public FirefoxOptions createCapabilities() {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("TakesScreenshot", true);
            return firefoxOptions;
        }

        @Override
        public WebDriver createWebDriver() {
            return new FirefoxDriver(createCapabilities());
        }
    },

    SAFARI {
        @Override
        public SafariOptions createCapabilities() {
            return new SafariOptions();
        }

        @Override
        public WebDriver createWebDriver() {
            return new SafariDriver(createCapabilities());
        }
    },

    CHROME {
        @Override
        public ChromeOptions createCapabilities() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments("--disable-blink-features");
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            chromeOptions.setExperimentalOption("prefs", prefs);
            return chromeOptions;
        }

        @Override
        public WebDriver createWebDriver() {
            return new ChromeDriver(createCapabilities());
        }
    };

    public abstract Capabilities createCapabilities();

    public abstract WebDriver createWebDriver();

    public RemoteWebDriver createRemoteWebDriver(URL remoteUrl) {
        return new RemoteWebDriver(remoteUrl, createCapabilities());
    }

}
