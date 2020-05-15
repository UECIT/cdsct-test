package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

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
    wait.until(visibilityOf(usernameField)).sendKeys(user.getUsername());
    wait.until(visibilityOf(passwordField)).sendKeys(user.getPassword());
    wait.until(elementToBeClickable(loginButton)).click();
  }

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(loginButton)).isDisplayed();
  }

}
