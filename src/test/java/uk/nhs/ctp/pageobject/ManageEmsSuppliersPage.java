package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.EmsSupplier;
import uk.nhs.ctp.model.TableRow;

public class ManageEmsSuppliersPage extends PageObject {

  public ManageEmsSuppliersPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "app-ems-supplier")
  private WebElement component;

  @FindBy(xpath = "//span[contains(text(), \"Create New Supplier\")]")
  private WebElement createButton;

  @FindBy(css = "input.emsName")
  private WebElement nameTextBox;

  @FindBy(css = "input.emsBaseUrl")
  private WebElement baseUrlTextBox;

  @FindBy(css = "input.emsAuthToken")
  private WebElement authTokenTextBox;

  @FindBy(css = "button.saveEms")
  private WebElement saveButton;

  @FindBy(css = "tr.emsSupplier")
  private List<WebElement> supplierRows;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void create(EmsSupplier newSupplier) {

    createButton.click();

    wait.until(visibilityOfAllElements(nameTextBox, baseUrlTextBox));

    nameTextBox.sendKeys(newSupplier.getName());
    baseUrlTextBox.sendKeys(newSupplier.getBaseUrl());
    authTokenTextBox.sendKeys(newSupplier.getAuthToken());

    saveButton.click();
  }

  public List<EmsSupplier> supplierList() {
    return supplierRows.stream()
        .map(TableRow::new)
        .map(EmsSupplier::from)
        .collect(Collectors.toList());
  }
}
