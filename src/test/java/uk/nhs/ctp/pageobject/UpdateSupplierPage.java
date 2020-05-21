package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CdssSupplier;

public class UpdateSupplierPage extends BaseSupplierPage {

  public UpdateSupplierPage(WebDriver driver) { super(driver); }

  @FindBy(tagName = "app-update-cdss-supplier")
  private WebElement component;

  @FindBy(xpath = "//button[@type=\"submit\"]")
  private WebElement updateButton;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void update(CdssSupplier cdssSupplier) {
    wait.until(visibilityOfAllElements(nameTextBox, baseUrlTextBox));

    nameTextBox.clear();
    baseUrlTextBox.clear();

    nameTextBox.sendKeys(cdssSupplier.getName());
    baseUrlTextBox.sendKeys(cdssSupplier.getBaseUrl());
    if (cdssSupplier.getDataRefType() != null) {
      switch (cdssSupplier.getDataRefType()) {
        case CONTAINED:
          inputDataByResourceButton.click();
          break;
        case REFERENCE:
          inputDataByReferenceButton.click();
          break;
      }
    }

    if (cdssSupplier.getParamsRefType() != null) {
      switch (cdssSupplier.getParamsRefType()) {
        case CONTAINED:
          inputParametersByResourceButton.click();
          break;
        case REFERENCE:
          inputParametersByReferenceButton.click();
          break;
      }
    }

    updateButton.click();
  }
}
