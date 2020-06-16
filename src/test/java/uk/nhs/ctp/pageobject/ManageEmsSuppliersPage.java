package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.EmsSupplier;
import uk.nhs.ctp.model.TableRow;
import uk.nhs.ctp.util.SeleniumUtil;

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

  @FindBy(xpath = "//button//*[text()=\"Cancel\"]")
  private WebElement cancelButton;

  @FindBy(css = "tr.emsSupplier")
  private List<WebElement> supplierRows;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void create(EmsSupplier newSupplier) {
    wait.until(visibilityOf(createButton)).click();
    wait.until(visibilityOfAllElements(nameTextBox, baseUrlTextBox, authTokenTextBox));

    SeleniumUtil.setValue(nameTextBox, newSupplier.getName(), wait);
    SeleniumUtil.setValue(baseUrlTextBox, newSupplier.getBaseUrl(), wait);
    SeleniumUtil.setValue(authTokenTextBox, newSupplier.getAuthToken(), wait);

    wait.until(elementToBeClickable(saveButton)).click();
  }

  public void suppliersListNonEmpty() {
    wait.until(visibilityOfAllElements(supplierRows));
  }

  public List<EmsSupplier> supplierList() {
    return supplierRows.stream()
        .map(TableRow::new)
        .map(EmsSupplier::from)
        .collect(Collectors.toList());
  }

  private Optional<WebElement> supplierRow(EmsSupplier emsSupplier) {
    return supplierRows.stream()
        .filter(row -> EmsSupplier.from(new TableRow(row)).getName().equals(emsSupplier.getName()))
        .findFirst();
  }

  public void edit(EmsSupplier emsSupplier) {
    var supplierRow = supplierRow(emsSupplier);
    supplierRow.ifPresent(row -> row
        .findElement(By.xpath(".//span[text()=\"Edit\"]"))
        .click());

    wait.until(visibilityOf(nameTextBox));
  }

  public EmsSupplier currentlyEditing() {
    return EmsSupplier.builder()
        .name(nameTextBox.getAttribute("value"))
        .baseUrl(baseUrlTextBox.getAttribute("value"))
        .authToken(authTokenTextBox.getAttribute("value"))
        .build();
  }

  public void finishedEditing() {
    wait.until(SeleniumUtil.absenceOf(nameTextBox));
  }

  public void cancelEdit() {
    wait.until(elementToBeClickable(cancelButton)).click();
    finishedEditing();
  }

  public void delete(EmsSupplier emsSupplier) {
    var supplierRow = supplierRow(emsSupplier);

    supplierRow.ifPresent(row -> {
      WebElement removeButton = row.findElement(By.xpath(".//span[text()=\"Remove\"]"));
      wait.until(elementToBeClickable(removeButton)).click();
      wait.until(SeleniumUtil.absenceOf(row));
    });
  }
}
