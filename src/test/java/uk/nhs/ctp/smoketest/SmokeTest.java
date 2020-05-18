package uk.nhs.ctp.smoketest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import uk.nhs.ctp.model.Answers;
import uk.nhs.ctp.model.CDSS;
import uk.nhs.ctp.model.Jurisdiction;
import uk.nhs.ctp.model.Patient;
import uk.nhs.ctp.model.SelectionMode;
import uk.nhs.ctp.model.ServiceDefinition;
import uk.nhs.ctp.model.SettingContext;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.model.UserType;
import uk.nhs.ctp.pageobject.LoginPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.TriagePage;
import uk.nhs.ctp.steps.EMSTest;

@RunWith(SpringRunner.class)
public class SmokeTest extends EMSTest {

  @Autowired
  private String emsUrl;

  private LoginPage loginPage;
  private MainPage mainPage;
  private TriagePage triagePage;

  @Before
  public void setup() {
    WebDriver driver = ems.reset();
    driver.get(emsUrl);
    loginPage = new LoginPage(driver);
    mainPage = new MainPage(driver);
    triagePage = new TriagePage(driver);
  }

  @Test
  public void smokeTest() {
    login();
    startTriage();
    performTriage();
  }

  private void login() {
    loginPage.login(User.admin());
    assertThat(mainPage.onPage(), is(true));
  }

  private void startTriage() {
    mainPage.dismissSnackBar();
    mainPage.selectPatient(Patient.JOE_BLOGGS);
    mainPage.selectSettingContext(SettingContext.ONLINE);
    mainPage.selectUserType(UserType.PATIENT);
    mainPage.selectJurisdiction(Jurisdiction.UK);
    mainPage.selectSelectionMode(SelectionMode.MANUAL);
    mainPage.selectCDSS(CDSS.DOCKERIZED);
    mainPage.selectServiceDefinition(ServiceDefinition.CHEST_PAINS);
    mainPage.launchTriage();
    assertThat(triagePage.onPage(), is(true));
  }

  private void performTriage() {
    triagePage.answerQuestion("Has the pain in your chest been caused by an injury?", Answers.YES);
    triagePage.answerQuestion("Have you been stabbed or shot by a gun?", Answers.YES);
    assertThat(triagePage.getInterimCareAdvice(), hasSize(1));
    assertThat(triagePage.getInterimResult().isPresent(), is(false));
    triagePage.answerQuestion("Bleeding from the injury?", Answers.YES);
    assertThat(triagePage.getInterimResult().isPresent(), is(true));
    assertThat(triagePage.getInterimCareAdvice(), hasSize(2));
    triagePage.answerQuestion("There is excessive bleeding, pulsating blood loss", Answers.YES);
    assertThat(triagePage.getResultTitle(), is("Call 999"));
  }

  @After
  public void cleanup() {
    ems.getDriver().close();
  }

}
