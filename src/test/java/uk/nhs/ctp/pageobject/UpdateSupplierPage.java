package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateSupplierPage extends BaseSupplierPage {

  public UpdateSupplierPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "app-update-cdss-supplier")
  private WebElement component;

  @FindBy(xpath = "//button[@type=\"submit\"]")
  private WebElement updateButton;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public String getAuthToken() {
    return wait.until(visibilityOf(authTokenTextBox)).getAttribute("value");
  }
}
