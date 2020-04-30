package uk.nhs.ctp.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import uk.nhs.ctp.model.EMS;

@Slf4j
public class Hooks {

  @Autowired
  private EMS ems;

  @Autowired
  private String emsUrl;

  @Autowired
  private WebDriver driver;

  @Before
  public void setup() {
    ems.reset();
    driver.get(emsUrl);
    log.info("Started web driver at {}", emsUrl);
  }

  @After
  public void cleanup(Scenario scenario) {
    if (scenario.isFailed()) {
      byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      scenario.embed(screenshotAs, "image/png", LocalDateTime.now().toString());
    }

    driver.quit();
    log.info("Quit web driver");
  }

}
