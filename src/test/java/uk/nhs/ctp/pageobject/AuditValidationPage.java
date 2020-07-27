package uk.nhs.ctp.pageobject;

import org.openqa.selenium.WebDriver;

public class AuditValidationPage extends PageObject {

  public AuditValidationPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean onPage() {
    return true;
  }


}
