package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CareAdvice;
import uk.nhs.ctp.model.Result;

public class TriagePage extends PageObject {

  private static final String QUESTION_PATH =
      "//app-questionnaire//div[contains(concat(' ',normalize-space(@class),' '),' question ')]";
  private static final String INTERIM_RESULT_TAB =
      "//div[@class=\"mat-tab-labels\"]/div[.//div[text()=\"Interim result:\"]]";
  private static final String INTERIM_CARE_TAB =
      "//div[@class=\"mat-tab-labels\"]/div[.//div[text()=\"Interim Care Advice\"]]";

  // Past questions section has class 'question' so we have to use this xpath
  @FindBy(xpath = QUESTION_PATH)
  private WebElement question;

  @FindBy(className = "radio-button")
  private List<WebElement> answers;

  @FindBy(id = "continue")
  private WebElement continueButton;

  @FindBy(xpath = INTERIM_RESULT_TAB)
  private WebElement interimResultTab;

  @FindBy(xpath = INTERIM_CARE_TAB)
  private WebElement interimCareAdviceTab;

  //used for both referral requests and care advice depending on which tab has been selected.
  @FindBy(xpath = "//mat-card//mat-card-content")
  private List<WebElement> interimContent;

  public TriagePage(WebDriver driver) {
    super(driver);
  }

  @Override
  public boolean onPage() {
    return wait.until(visibilityOf(question)).isDisplayed();
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

  public List<CareAdvice> getInterimCareAdvice() {
    wait.until(elementToBeClickable(interimCareAdviceTab)).click();
    return wait.until((visibilityOfAllElements(interimContent))).stream()
        .map(CareAdvice::from)
        .collect(Collectors.toList());
  }

  public List<Result> getInterimResult() {
    wait.until(elementToBeClickable(interimResultTab)).click();
    return interimContent.stream()
        .map(Result::from)
        .collect(Collectors.toList());
  }
}
