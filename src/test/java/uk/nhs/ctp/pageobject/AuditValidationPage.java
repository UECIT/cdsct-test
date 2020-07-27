package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.Interaction;
import uk.nhs.ctp.model.TableRow;

public class AuditValidationPage extends PageObject {

  public AuditValidationPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "validation-report")
  private WebElement component;

  @FindBy(css = "tr.interaction")
  private List<WebElement> interactionRows;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public List<Interaction> interactionList() {
    try {
      Thread.sleep(5000); //TODO: Do this better, the results aren't rendered straight away.
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return interactionRows.stream()
        .map(TableRow::new)
        .map(Interaction::from)
        .collect(Collectors.toList());
  }


}
