package uk.nhs.ctp.model;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.context.Context;

@Data
public class EMS {

  WebDriver driver;
  User user;
  CdssSupplier cdssSupplier;
  CDSS cdss;

  /**
   * Reset the test environment
   * @return a new webdriver for the test context
   */
  public WebDriver reset() {
    this.user = null;
    driver = Context.driver();
    return driver;
  }
}
