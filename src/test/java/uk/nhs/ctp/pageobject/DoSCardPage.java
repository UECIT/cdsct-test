package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DoSCardPage extends PageObject {

  public DoSCardPage(WebDriver driver) {
    super(driver);
  }

  private static final String SEARCH_SUMMARY_HEADER_PATH = "//mat-card-title[text()=\"Services Summary: \"]";

  @FindBy(tagName = "app-dos-display")
  private WebElement component;

  @FindBy(className = "search")
  private WebElement searchButton;

  @FindBy(xpath = SEARCH_SUMMARY_HEADER_PATH)
  private WebElement searchSummaryHeader;

  @FindBy(css = ".service-selection .mat-radio-label-content")
  private List<WebElement> serviceNames;

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(component)).isDisplayed();
  }

  public void searchForServices() {
    wait.until(elementToBeClickable(searchButton)).click();
  }

  public List<String> getServices() {
    wait.until(visibilityOf(searchSummaryHeader));
    return serviceNames.stream()
        .map(WebElement::getText)
        .collect(Collectors.toUnmodifiableList());
  }
}
