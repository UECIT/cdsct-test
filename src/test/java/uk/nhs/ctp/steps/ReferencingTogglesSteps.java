package uk.nhs.ctp.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.actions.CdssSupplierActions;
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
import uk.nhs.ctp.pageobject.DeleteCdssPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;
import uk.nhs.ctp.pageobject.TriagePage;
import uk.nhs.ctp.pageobject.UpdateCdssPage;

public class ReferencingTogglesSteps extends EMSTest {

  @Given("sending referenced resources")
  public void sendingReferencedResources() {
    ems.setCdss(CDSS.REFERENCED);
    CdssSupplier cdssSupplier = CdssSupplier.builder()
        .name(CDSS.REFERENCED.getName())
        .dataRefType(ReferenceType.REFERENCE)
        .paramsRefType(ReferenceType.REFERENCE)
        .baseUrl("http://cdss:8080/fhir")
        .authToken(cactusAuthToken)
        .build();
    ems.setCdssSupplier(cdssSupplier);
    ems.setUser(User.admin());

    CdssSupplierActions.loginCreateSupplier(ems);
  }

  @Given("sending contained resources")
  public void sendingContainedResources() {
    ems.setCdss(CDSS.CONTAINED);
    CdssSupplier cdssSupplier = CdssSupplier.builder()
        .name(CDSS.CONTAINED.getName())
        .dataRefType(ReferenceType.CONTAINED)
        .paramsRefType(ReferenceType.CONTAINED)
        .baseUrl("http://cdss:8080/fhir")
        .authToken(cactusAuthToken)
        .build();
    ems.setCdssSupplier(cdssSupplier);
    ems.setUser(User.admin());

    CdssSupplierActions.loginCreateSupplier(ems);
  }

  @When("we start a triage journey")
  public void weStartATriageJourney() {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);

    mainPage.home();
    mainPage.selectPatient(Patient.JOE_BLOGGS);
    mainPage.selectSettingContext(SettingContext.ONLINE);
    mainPage.selectUserType(UserType.PATIENT);
    mainPage.selectJurisdiction(Jurisdiction.UK);
    mainPage.selectSelectionMode(SelectionMode.MANUAL);
    mainPage.selectCDSS(ems.getCdss());
    mainPage.selectServiceDefinition(ServiceDefinition.CHEST_PAINS);
    mainPage.launchTriage();
  }

  @Then("the triage journey displays the first question")
  public void theTriageJourneyDisplaysTheFirstQuestion() {
    WebDriver driver = ems.getDriver();
    TriagePage triagePage = new TriagePage(driver);

    assertThat(triagePage.onPage(), is(true));
    MainPage mainPage = new MainPage(driver);
    mainPage.manageCdssSuppliers();
    ManageCdssSuppliersPage manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);
    manageCdssSuppliersPage.edit(ems.getCdssSupplier().getName());
    UpdateCdssPage updateCdssPage = new UpdateCdssPage(driver);
    updateCdssPage.delete();
    DeleteCdssPage deleteCdssPage = new DeleteCdssPage(driver);
    deleteCdssPage.delete();
    manageCdssSuppliersPage.onPage();
  }

}
