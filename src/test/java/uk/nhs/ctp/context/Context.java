package uk.nhs.ctp.context;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.nhs.ctp.model.EMS;

@Configuration
@PropertySource("classpath:application.properties")
@Slf4j
public class Context {

  private static String DRIVER_LOCATION;

  @Value("${ems.url}")
  private String emsUrl;

  @Value("${driver.location}")
  public void setDriverLocation(String driverLocation) {
    DRIVER_LOCATION = driverLocation;
  }

  public static WebDriver driver() {
    if (Boolean.parseBoolean(System.getenv("CI"))) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
      log.info("Starting chrome driver for profile 'CI'");
      WebDriver driver = new ChromeDriver(options);
      driver.manage().window().setSize(new Dimension(1920, 1080));
      return driver;
    }

    System.setProperty("webdriver.chrome.driver", DRIVER_LOCATION);
    log.info("Starting chrome driver at {}", DRIVER_LOCATION);
    return new ChromeDriver();
  }

  @Bean
  public String emsUrl() {
    return emsUrl;
  }

  @Bean
  public EMS ems() {
    return new EMS();
  }
}
