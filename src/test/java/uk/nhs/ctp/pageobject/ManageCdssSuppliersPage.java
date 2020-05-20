package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageCdssSuppliersPage extends PageObject {

  public ManageCdssSuppliersPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(id = "createSupplier")
  private WebElement createSupplierButton;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(createSupplierButton)).isDisplayed();
  }
}
