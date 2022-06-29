//package automation.core.selenium;
//
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//import java.util.Properties;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.stereotype.Component;
//
//import automation.core.framework.ObjectLoader;
//import automation.core.framework.TestContext;
//
//@Component
//public class SeleniumUtils {
//
//    private final TestContext testContext;
//    private final SeleniumWait seleniumWait;
//    public static final Properties objectRepository = ObjectLoader.loadYaml("objectrepository.yml");
//
//    public SeleniumUtils(TestContext testContext, SeleniumWait seleniumWait) {
//        this.testContext = testContext;
//        this.seleniumWait = seleniumWait;
//    }
//
//    public void navigateToUrl(String url) {
//        testContext.getWebDriver().get(url);
//    }
//
//    public WebElement findElement(String objectName) {
//        return findElement(By.xpath(objectRepository.getProperty(objectName)));
//    }
//
//    public WebElement findElement(By locator) {
//        WebDriver webDriver = testContext.getWebDriver();
//        try {
//            return new WebDriverWait(webDriver, Duration.of(10, ChronoUnit.SECONDS))
//                    .pollingEvery(Duration.ofMillis(500))
//                    .until(driver -> driver.findElement(locator));
//        } catch (TimeoutException ex) {
//            throw new NoSuchElementException("Could not find element", ex);
//        }
//    }
//    public void writeText(String xpath, String value) {
//        writeText(findElement(xpath), value);
//    }
//
//    public void writeText(WebElement element, String value) {
//        element.sendKeys(value);
//    }
//
//    public String readText(String objectName) {
//        return seleniumWait.visibilityOf(objectName).getText();
//    }
//
//    public void click(String objectName) {
//        doClick(seleniumWait.visibilityOf(objectName));
//    }
//
//    private void doClick(WebElement element) {
//        seleniumWait.elementToBeClickable(element).click();
//    }
//}
