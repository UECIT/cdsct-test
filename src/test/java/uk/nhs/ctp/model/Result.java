package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Value;
import org.openqa.selenium.WebElement;

@Value
@Builder
public class Result {

  public static Result from(WebElement webElement) {
    return Result.builder()
        .build();
  }

}
