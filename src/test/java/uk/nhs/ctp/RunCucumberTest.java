package uk.nhs.ctp;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import uk.nhs.ctp.context.Context;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/report"})
@SpringBootTest
public class RunCucumberTest {
}
