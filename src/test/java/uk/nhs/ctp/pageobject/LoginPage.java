package uk.nhs.ctp.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.User;

public class LoginPage extends PageObject {

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(name = "username")
  private WebElement usernameField;

  @FindBy(name = "password")
  private WebElement passwordField;

  @FindBy(className = "login-button")
  private WebElement loginButton;

  public void login(User user) {
    usernameField.sendKeys(user.getUsername());
    passwordField.sendKeys(user.getPassword());
    loginButton.click();
  }

}
