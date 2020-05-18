package uk.nhs.ctp.model;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import lombok.Builder;
import lombok.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@Value
@Builder
public class CareAdvice {

  String title;

  public static CareAdvice from(WebElement element, WebDriverWait wait) {
    // Nasty way of finding title, but the UI doesn't have proper ids/classes etc.
    // Ideally this tab content should be its own PO.
    String title = wait.until(visibilityOfAllElements(element.findElements(By.tagName("p")))).stream()
        .filter(el -> el.getText().toLowerCase().contains("title"))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("no title found for care plan"))
        .getText()
        .split(" ")[1];

    return CareAdvice.builder()
        .title(title)
        .build();
  }

}
