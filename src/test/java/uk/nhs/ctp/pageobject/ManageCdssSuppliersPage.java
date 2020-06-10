package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.TableRow;

public class ManageCdssSuppliersPage extends PageObject {

  public ManageCdssSuppliersPage(WebDriver driver) {
    super(driver);
  }

  private static final String SUPPLIERS_TABLE_PATH = "//table/tbody/tr";

  @FindBy(id = "createSupplier")
  private WebElement createSupplierButton;

  @FindBy(xpath = SUPPLIERS_TABLE_PATH)
  private List<WebElement> suppliers;


  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(createSupplierButton)).isDisplayed();
  }

  public void createNewSupplier() {
    wait.until(elementToBeClickable(createSupplierButton)).click();
  }

  public List<CdssSupplier> supplierList() {
    return suppliers.stream()
        .map(TableRow::new)
        .map(CdssSupplier::from)
        .collect(Collectors.toList());
  }

  public void edit(String name) {
    String xpath = "//tr[td[text()=\"" + name + "\"]]//span[@class=\"actionButton\"]";
    WebElement editButton = webdriver.findElement(By.xpath(xpath));
    wait.until(elementToBeClickable(editButton)).click();
  }
}
