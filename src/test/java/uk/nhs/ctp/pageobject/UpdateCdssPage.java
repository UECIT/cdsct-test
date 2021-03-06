package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateCdssPage extends BaseCdssPage {

  public UpdateCdssPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "app-update-cdss-supplier")
  private WebElement component;

  @FindBy(xpath = "//button[@type=\"submit\"]")
  private WebElement updateButton;

  @FindBy(xpath = "//span[text()=\"Delete Supplier\"]")
  private WebElement deleteSupplierButton;
  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public String getAuthToken() {
    return wait.until(visibilityOf(authTokenTextBox)).getAttribute("value");
  }

  public void delete() {
    wait.until(elementToBeClickable(deleteSupplierButton)).click();
  }
}
