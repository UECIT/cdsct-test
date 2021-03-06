package uk.nhs.ctp.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageObject {

  protected WebDriver webdriver;
  protected WebDriverWait wait;

  private static final int TIMEOUT_SECONDS = 30;

  public PageObject(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.webdriver = driver;
    this.wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
  }

  public abstract boolean onPage();

}
