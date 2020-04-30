package uk.nhs.ctp.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends PageObject {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(className = "menuBar")
  private WebElement menuBar;

  public boolean onPage() {
    return wait.until(ExpectedConditions.urlContains("main"))
        && menuBar.isDisplayed();
  }
}
