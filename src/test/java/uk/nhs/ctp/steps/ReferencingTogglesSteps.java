package uk.nhs.ctp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.CreateNewSupplierPage;
import uk.nhs.ctp.pageobject.LoginPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;
import uk.nhs.ctp.pageobject.UpdateSupplierPage;

public class ReferencingTogglesSteps extends EMSTest {

  private MainPage mainPage;
  private ManageCdssSuppliersPage manageCdssSuppliersPage;
  private CreateNewSupplierPage createNewSupplierPage;

  @Given("a new CDSS supplier")
  public void aNewCDSSSupplier() {
    ems.setUser(User.admin());
    WebDriver driver = ems.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    mainPage = new MainPage(driver);
    manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);
    createNewSupplierPage = new CreateNewSupplierPage(driver);

    CdssSupplier cdssSupplier = CdssSupplier.builder()
        .name("CDSS Test Supplier (Toggles)")
        .baseUrl("http://localhost:8080/fhir/")
        .build();

    ems.setCdssSupplier(cdssSupplier);

    loginPage.login(ems.getUser());
    mainPage.dismissSnackBar();
    mainPage.manageCdssSuppliers();
    manageCdssSuppliersPage.createNewSupplier();
    createNewSupplierPage.create(cdssSupplier);
  }

  @Given("sending referenced resources")
  public void sendingReferencedResources() {
    CdssSupplier cdssSupplier = ems.getCdssSupplier();
    cdssSupplier.toBuilder()
        .dataRefType(ReferenceType.REFERENCE)
        .paramsRefType(ReferenceType.REFERENCE);

    manageCdssSuppliersPage.edit(cdssSupplier.getName());
    UpdateSupplierPage updateSupplierPage = new UpdateSupplierPage(ems.getDriver());
    updateSupplierPage.update(cdssSupplier);
  }

  @When("we start a triage journey")
  public void weStartATriageJourney() {
  }

  @Then("the triage journey displays the first question")
  public void theTriageJourneyDisplaysTheFirstQuestion() {
  }

  @Given("sending contained resources")
  public void sendingContainedResources() {
  }
}
