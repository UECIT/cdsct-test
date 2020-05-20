package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CdssSupplier;

public class CreateNewSupplierPage extends PageObject {

  public CreateNewSupplierPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "app-create-cdss-supplier")
  private WebElement component;

  @FindBy(id = "param-ref-ref-button")
  private WebElement inputParametersByReferenceButton;

  @FindBy(id = "param-ref-res-button")
  private WebElement inputParametersByResourceButton;

  @FindBy(id = "data-ref-ref-button")
  private WebElement inputDataByReferenceButton;

  @FindBy(id = "data-ref-res-button")
  private WebElement inputDataByResourceButton;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void create(CdssSupplier newSupplier) {

  }
}
