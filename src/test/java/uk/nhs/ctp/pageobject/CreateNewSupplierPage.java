package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CdssSupplier;

public class CreateNewSupplierPage extends BaseSupplierPage {

  public CreateNewSupplierPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "app-create-cdss-supplier")
  private WebElement component;

  @FindBy(xpath = "//button[@type=\"submit\"]")
  private WebElement saveButton;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void create(CdssSupplier newSupplier) {
    wait.until(visibilityOfAllElements(nameTextBox, baseUrlTextBox));

    nameTextBox.sendKeys(newSupplier.getName());
    baseUrlTextBox.sendKeys(newSupplier.getBaseUrl());
    authTokenTextBox.sendKeys(newSupplier.getAuthToken());
    if (newSupplier.getDataRefType() != null) {
      switch (newSupplier.getDataRefType()) {
        case CONTAINED:
          inputDataByResourceButton.click();
          break;
        case REFERENCE:
          inputDataByReferenceButton.click();
          break;
      }
    }

    if (newSupplier.getParamsRefType() != null) {
      switch (newSupplier.getParamsRefType()) {
        case CONTAINED:
          inputParametersByResourceButton.click();
          break;
        case REFERENCE:
          inputParametersByReferenceButton.click();
          break;
      }
    }

    // Angular does not use 'select's so we have to do this manually
    if (newSupplier.getSupportedVersion() != null) {
      wait.until(elementToBeClickable(supportedVersionDropdown)).click();
      WebElement option = webdriver.findElement(
          By.xpath("//mat-option/span[contains(.,'" + newSupplier.getSupportedVersion().getDisplay() + "')]"));
      wait.until(elementToBeClickable(option)).click();
    }

    saveButton.click();
  }

}
