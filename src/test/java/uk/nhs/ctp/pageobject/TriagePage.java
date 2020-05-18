package uk.nhs.ctp.pageobject;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Optional;
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
  private static final String RESULT_TITLE = "//mat-card-title[contains(text(), \"Result\")]";

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

  @FindBy(xpath = "//mat-card//mat-card-content")
  private List<WebElement> interimContent;

  @FindBy(tagName = "app-referral-request")
  private List<WebElement> referralRequest;

  @FindBy(xpath = RESULT_TITLE)
  private WebElement resultTitle;

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

    //TODO: CDSCT-152 Extract care advice to its own id'd component so we don't have to sleep
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return wait.until((visibilityOfAllElements(interimContent))).stream()
        .map(el -> CareAdvice.from(el, wait))
        .collect(Collectors.toList());
  }

  public Optional<Result> getInterimResult() {
    wait.until(elementToBeClickable(interimResultTab)).click();

    if (referralRequest.isEmpty()) {
      return Optional.empty();
    }

    Result result = Result.from(Iterables.getOnlyElement(referralRequest), wait);
    return Optional.of(result);
  }

  public String getResultTitle() {
    return wait.until(visibilityOf(resultTitle))
        .getText()
        .split(": ")[1];
  }
}
