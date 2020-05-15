package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Value;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

@Value
@Builder
public class CareAdvice {

  String title;

  public static CareAdvice from(WebElement element) {
    // Nasty way of finding title, but the UI doesn't have proper ids/classes etc.
    String title = element.findElements(By.tagName("p")).stream()
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
