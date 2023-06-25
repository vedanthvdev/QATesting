package automation.stepdefinition;

import automation.context.ContextManager;
import automation.core.framework.BaseStepDef;
import automation.selenium.SeleniumUtils;
import automation.selenium.SeleniumWait;
import io.cucumber.java.en.When;

public class LoginSteps extends BaseStepDef{
    private final SeleniumUtils seleniumUtils;
    private final SeleniumWait seleniumWait;


    public LoginSteps(ContextManager contextManager
            , SeleniumUtils seleniumUtils, SeleniumWait seleniumWait
    ) {
        super(contextManager);
        this.seleniumUtils = seleniumUtils;
        this.seleniumWait = seleniumWait;
    }


    @When("the user is in login page")
    public void userInHome() {
        seleniumUtils.navigateToUrl("https://www.google.com/maps");
    }

    @When("verify login fields are present")
    public void userVerifyLoginPage() {

    }

}
