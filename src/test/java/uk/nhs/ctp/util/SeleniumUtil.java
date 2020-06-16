package uk.nhs.ctp.util;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementValue;

import java.io.File;
import java.io.FileOutputStream;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@UtilityClass
public class SeleniumUtil {

  public void takeScreenshot(WebDriver driver, String filename) {
    new File(("target/screenshots/")).mkdirs();
    try {
      FileOutputStream out = new FileOutputStream(
          "target/screenshots/screenshot-" + filename + ".png");
      out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
      out.close();
    } catch (Exception ignored) {

    }
  }

  /**
   * Sends value to an input element using sendKeys and waits to make sure the value has been accepted
   */
  public void setValue(WebElement element, String value, WebDriverWait wait) {
    element.clear();
    wait.until(textToBePresentInElementValue(element, ""));
    element.sendKeys(value);
    wait.until(textToBePresentInElementValue(element, value));
  }

  /**
   * Condition to check if an element is not displayed, or cannot be found in the DOM
   */
  public ExpectedCondition<Boolean> absenceOf(WebElement element) {
    return new ExpectedCondition<>() {
      @NullableDecl
      @Override
      public Boolean apply(@NullableDecl WebDriver webDriver) {
        try {
          return element.isDisplayed() ? null : true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
          return true;
        }
      }

      @Override
      public String toString() {
        return "notVisible " + element;
      }
    };
  }

}
