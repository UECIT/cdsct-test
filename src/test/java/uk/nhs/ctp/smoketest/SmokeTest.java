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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.nhs.ctp.context.Context;
import uk.nhs.ctp.model.Answers;
import uk.nhs.ctp.model.CDSS;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.Jurisdiction;
import uk.nhs.ctp.model.Patient;
import uk.nhs.ctp.model.SelectionMode;
import uk.nhs.ctp.model.ServiceDefinition;
import uk.nhs.ctp.model.SettingContext;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.model.UserType;
import uk.nhs.ctp.pageobject.CreateNewCdssPage;
import uk.nhs.ctp.pageobject.DeleteCdssPage;
import uk.nhs.ctp.pageobject.LoginPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;
import uk.nhs.ctp.pageobject.TriagePage;
import uk.nhs.ctp.pageobject.UpdateCdssPage;
import uk.nhs.ctp.steps.EMSTest;

@ContextConfiguration(classes = Context.class)
@RunWith(SpringRunner.class)
public class SmokeTest extends EMSTest {

  @Autowired
  private String emsUrl;

  private LoginPage loginPage;
  private MainPage mainPage;
  private TriagePage triagePage;
  private ManageCdssSuppliersPage manageCdssSuppliersPage;
  private CreateNewCdssPage createNewCdssPage;
  private UpdateCdssPage updateCdssPage;
  private DeleteCdssPage deleteCdssPage;

  @Before
  public void setup() {
    WebDriver driver = ems.reset();
    driver.get(emsUrl);
    loginPage = new LoginPage(driver);
    mainPage = new MainPage(driver);
    triagePage = new TriagePage(driver);

    manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);
    createNewCdssPage = new CreateNewCdssPage(driver);
    updateCdssPage = new UpdateCdssPage(driver);
    deleteCdssPage = new DeleteCdssPage(driver);
  }

  @Test
  public void smokeTest() {
    login();
    createCDSS();
    startTriage();
    performTriage();
    removeCdss();
  }

  private void login() {
    loginPage.login(User.admin());
    assertThat(mainPage.onPage(), is(true));
    mainPage.dismissSnackBar();
  }

  private void createCDSS() {
    ems.setCdssSupplier(CdssSupplier.builder()
        .name(CDSS.DOCKERIZED.getName())
        .dataRefType(ReferenceType.REFERENCE)
        .paramsRefType(ReferenceType.REFERENCE)
        .baseUrl("http://cdss:8080/fhir")
        .authToken(cactusAuthToken)
        .build());

    mainPage.manageCdssSuppliers();
    manageCdssSuppliersPage.createNewSupplier();
    createNewCdssPage.create(ems.getCdssSupplier());
    manageCdssSuppliersPage.onPage();
  }

  private void startTriage() {
    mainPage.home();
    mainPage.onPage();

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

  private void removeCdss() {
    mainPage.manageCdssSuppliers();
    manageCdssSuppliersPage.edit(ems.getCdssSupplier().getName());
    updateCdssPage.delete();
    deleteCdssPage.delete();
  }

  @After
  public void cleanup() {
    ems.getDriver().close();
  }

}
