package automation.selenium;


import java.time.Duration;

import automation.context.ContextManager;
import automation.core.framework.BaseStepDef;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import automation.core.framework.TestContext;

@Component
public class SeleniumWait extends BaseStepDef {

    public SeleniumWait(ContextManager contextManager) {
        super(contextManager);
    }

    private FluentWait<WebDriver> createWait() {
        return new FluentWait<>(getContextManager().getRequiredContext(TestContext.class).getWebDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    public WebElement elementToBeClickable(WebElement element) {
        return createWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement visibilityOf(String objectName) {
        WebElement webElement = createWait().until(webDriver -> webDriver.findElement(By.xpath(SeleniumUtils.objectRepository.getProperty(objectName))));
        return createWait().until(ExpectedConditions.visibilityOf(webElement));
    }

}
