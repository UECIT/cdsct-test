package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.nhs.ctp.model.CareAdvice;
import uk.nhs.ctp.model.Result;

public class TriagePage extends PageObject {

  public static final String QUESTION_PATH =
      "//app-questionnaire//div[contains(concat(' ',normalize-space(@class),' '),' question ')]";

  // Past questions section has class 'question' so we have to use this xpath
  @FindBy(xpath = QUESTION_PATH)
  private WebElement question;

  @FindBy(className = "radio-button")
  private List<WebElement> answers;

  @FindBy(id = "continue")
  private WebElement continueButton;

  @FindBy(id = "mat-tab-label-0-0") //these ids for the tabs are unreliable - fix
  private WebElement interimResultTab;

  @FindBy(id = "mat-tab-label-0-1")
  private WebElement interimCareAdviceTab;

  @FindBy(xpath = "//mat-card//mat-card-content") //used for both referral requests and care advice
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
    return interimContent.stream()
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
