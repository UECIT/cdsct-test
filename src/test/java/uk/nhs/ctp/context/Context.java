package uk.nhs.ctp.context;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.nhs.ctp.model.EMS;

@Configuration
@PropertySource("classpath:application.properties")
public class Context {

  @Value("${ems.url}")
  private String emsUrl;

  @Value("${driver.location}")
  private String driverLocation;

  @Bean
  public WebDriver driver() {
    System.out.println(driverLocation);
//    System.setProperty("webdriver.chrome.driver", driverLocation);
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
