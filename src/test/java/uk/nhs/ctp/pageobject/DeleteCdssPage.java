package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteCdssPage extends PageObject {

  @FindBy(tagName = "app-delete-cdss-supplier")
  private WebElement component;

  @FindBy(xpath = "//span[text()=\"Delete Supplier\"]")
  private WebElement deleteSupplierButton;

  public DeleteCdssPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void delete() {
    wait.until(elementToBeClickable(deleteSupplierButton)).click();
  }
}
