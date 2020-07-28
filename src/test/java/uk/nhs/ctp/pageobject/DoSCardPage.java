package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DoSCardPage extends PageObject {

  public DoSCardPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(tagName = "app-dos-display")
  private WebElement component;

  @FindBy(className = "search")
  private WebElement searchButton;

  private static final By SERVICE_NAMES = By.cssSelector(".service-selection .mat-radio-label-content");

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void searchForServices() {
    wait.until(elementToBeClickable(searchButton)).click();
  }

  public List<String> getServices() {
    return wait.until(numberOfElementsToBeMoreThan(SERVICE_NAMES, 0)).stream()
        .map(WebElement::getText)
        .collect(Collectors.toUnmodifiableList());
  }
}
