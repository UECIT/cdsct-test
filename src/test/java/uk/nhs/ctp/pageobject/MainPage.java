package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import uk.nhs.ctp.model.CDSS;
import uk.nhs.ctp.model.Jurisdiction;
import uk.nhs.ctp.model.Patient;
import uk.nhs.ctp.model.SelectableByName;
import uk.nhs.ctp.model.SelectionMode;
import uk.nhs.ctp.model.ServiceDefinition;
import uk.nhs.ctp.model.SettingContext;
import uk.nhs.ctp.model.UserType;

public class MainPage extends PageObject {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public static final String ADMIN_BUTTON_PATH = "//button[span[text()=\"Admin\"]]";
  public static final String HOME_BUTTON_PATH = "//button[span[text()=\"Home\"]]";
  public static final String MANAGE_CDSS_SUPPLIERS_PATH = "//button[@routerlink=\"/suppliers\"]";

  private static final String PATIENT_BUTTONS_PATH = "(//patient-selection//button)[position()<last()]";
  private static final String SETTING_CONTEXT_BUTTONS_PATH = "//triage-selection[.//div[text()=\"Select Setting Context\"]]//button";
  private static final String USER_TYPE_BUTTONS_PATH = "//triage-selection[.//div[text()=\"Select User Type\"]]//button";
  private static final String JURISDICTION_BUTTONS_PATH = "//triage-selection[.//div[text()=\"Select Jurisdiction\"]]//button";
  private static final String SELECTION_MODE_BUTTONS_PATH = "//triage-selection[.//div[text()=\"Service Definition Selection Mode\"]]//button";
  private static final String CDSS_SELECTION_BUTTONS_PATH = "//triage-selection[.//div[text()=\"Select CDSS\"]]//button";
  private static final String LAUNCH_PATH = "//div[@class=\"launchSection\"]//button";

  @FindBy(xpath = HOME_BUTTON_PATH)
  private WebElement homeButton;

  @FindBy(xpath = ADMIN_BUTTON_PATH)
  private WebElement adminButton;

  @FindBy(xpath = MANAGE_CDSS_SUPPLIERS_PATH)
  private WebElement manageCdssSuppliersButton;

  @FindBy(xpath = PATIENT_BUTTONS_PATH)
  private List<WebElement> patientButtons;

  @FindBy(xpath = SETTING_CONTEXT_BUTTONS_PATH)
  private List<WebElement> settingContextButtons;

  @FindBy(xpath = USER_TYPE_BUTTONS_PATH)
  private List<WebElement> userTypeButtons;

  @FindBy(xpath = JURISDICTION_BUTTONS_PATH)
  private List<WebElement> jurisdictionButtons;

  @FindBy(xpath = SELECTION_MODE_BUTTONS_PATH)
  private List<WebElement> selectionModeButtons;

  @FindBy(xpath = CDSS_SELECTION_BUTTONS_PATH)
  private List<WebElement> cdssSelectionButtons;

  @FindBy(id = "mat-select-0")
  private WebElement serviceSelectionDropdown;

  @FindBy(tagName = "app-main")
  private WebElement component;

  @FindBy(xpath = LAUNCH_PATH)
  private WebElement launchButton;

  @FindBy(className = "menuBar")
  private WebElement menuBar;

  @FindBy(tagName = "snack-bar-container")
  private WebElement snackBar;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void dismissSnackBar() {
    if (wait.until(visibilityOf(snackBar)).isDisplayed()) {
      WebElement understandButton = snackBar.findElement(By.className("mat-button"));
      wait.until(elementToBeClickable(understandButton)).click();
    }
    wait.until(not(presenceOfAllElementsLocatedBy(tagName("snack-bar-container"))));
  }

  public void home() {
    wait.until(elementToBeClickable(homeButton)).click();
    onPage();
  }

  public void manageCdssSuppliers() {
    wait.until(elementToBeClickable(adminButton)).click();
    wait.until(elementToBeClickable(manageCdssSuppliersButton)).click();
  }

  public void selectPatient(Patient patient) {
    selectButtonWithText(patientButtons, patient);
  }

  public void selectSettingContext(SettingContext context) {
    selectButtonWithText(settingContextButtons, context);
  }

  public void selectUserType(UserType userType) {
    selectButtonWithText(userTypeButtons, userType);
  }

  public void selectJurisdiction(Jurisdiction jurisdiction) {
    selectButtonWithText(jurisdictionButtons, jurisdiction);
  }

  public void selectSelectionMode(SelectionMode selectionMode) {
    selectButtonWithText(selectionModeButtons, selectionMode);
  }

  public void selectCDSS(CDSS cdss) {
    selectButtonWithText(cdssSelectionButtons, cdss);
  }

  public void selectServiceDefinition(ServiceDefinition serviceDefinition) {
    // Angular does not use 'select's so we have to do this manually
    wait.until(elementToBeClickable(serviceSelectionDropdown)).click();
    WebElement option = webdriver.findElement(
        By.xpath("//mat-option/span[contains(.,'" + serviceDefinition.getName() + "')]"));
    wait.until(elementToBeClickable(option)).click();
  }

  public boolean triageReady() {
    return wait.until(visibilityOf(launchButton)).isEnabled();
  }

  public void launchTriage() {
    if (!triageReady()) {
      throw new IllegalStateException("Launch button not enabled");
    }
    wait.until(elementToBeClickable(launchButton)).click();
  }

  private void selectButtonWithText(List<WebElement> elements, SelectableByName option) {
    WebElement button = wait.until(ExpectedConditions.visibilityOfAllElements(elements)).stream()
        .filter(elem -> elem.getText().contains(option.getName()))
        .findAny()
        .orElseThrow(() -> new NoSuchElementException("no button with text " + option.getName()));

    button.click();
    wait.until(attributeContains(button, "class", "selected"));
  }
}
