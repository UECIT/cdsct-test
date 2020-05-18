package uk.nhs.ctp.model;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import lombok.Builder;
import lombok.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@Value
@Builder
public class Result {

  private static final String PC_PATH = "//*[text()=\"Primary Concern: \"]/following-sibling::button";

  String primaryConcern;

  public static Result from(WebElement webElement, WebDriverWait wait) {
    // Nasty way of finding primary concern, but the UI doesn't have proper ids/classes etc.
    // Ideally this tab content should be its own PO.
    String primaryConcern = wait.until(visibilityOf(webElement.findElement(By.xpath(PC_PATH))))
        .getText();
    return Result.builder()
        .primaryConcern(primaryConcern)
        .build();
  }

}
