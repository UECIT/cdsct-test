package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TriagePage extends PageObject {

  // Past questions section has class 'question' so we have to use this xpath
  @FindBy(xpath = "//app-questionnaire//div[contains(concat(' ',normalize-space(@class),' '),' question ')]")
  private WebElement question;

  @FindBy(className = "radio-button")
  private List<WebElement> answers;

  @FindBy(id = "continue")
  private WebElement continueButton;

  public TriagePage(WebDriver driver) {
    super(driver);
  }

  public void answerQuestion(String questionTextContains, String answer) {
    if (!wait.until(visibilityOf(question)).getText().contains(questionTextContains)) {
      throw new IllegalStateException("question text " + questionTextContains + " did not match current question");
    }

    wait.until(elementToBeClickable(answers.stream()
        .filter(answerEl -> answerEl.getText().contains(answer))
        .findAny()
        .orElseThrow(() -> new NoSuchElementException("no answer with text " + answer))))
        .click();

    wait.until(elementToBeClickable(continueButton)).click();
  }

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(question)).isDisplayed();
  }
}
