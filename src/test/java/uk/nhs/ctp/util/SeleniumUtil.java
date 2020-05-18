package uk.nhs.ctp.util;

import java.io.File;
import java.io.FileOutputStream;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@UtilityClass
public class SeleniumUtil {

  public void takeScreenshot(WebDriver driver, String filename) {
    new File(("target/screenshots/")).mkdirs();
    try {
      FileOutputStream out = new FileOutputStream("target/screenshots/screenshot-" + filename + ".png");
      out.write( ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
      out.close();
    } catch (Exception ignored) {

    }
  }

}
