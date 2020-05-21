package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

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

    saveButton.click();
  }

}
