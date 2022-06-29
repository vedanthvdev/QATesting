package automation.core.framework;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration( classes = CucumberConfiguration.class)
@ComponentScan(basePackages = "automation")
public class CucumberConfiguration {

}
