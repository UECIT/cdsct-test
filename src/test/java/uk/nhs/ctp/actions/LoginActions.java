package uk.nhs.ctp.actions;

import lombok.experimental.UtilityClass;
import org.junit.platform.commons.util.Preconditions;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.model.EMS;
import uk.nhs.ctp.pageobject.LoginPage;
import uk.nhs.ctp.pageobject.MainPage;

@UtilityClass
public class LoginActions {
  public void login(EMS ems) {
    WebDriver driver = ems.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    Preconditions.notNull(ems.getUser(), "Login requires a user on the EMS object");
    loginPage.login(ems.getUser());
  }

  public void logout(EMS ems) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    mainPage.logout();
  }
}
