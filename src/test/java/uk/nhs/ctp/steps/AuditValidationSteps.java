package uk.nhs.ctp.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.actions.CdssSupplierActions;
import uk.nhs.ctp.model.CDSS;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.AuditValidationPage;
import uk.nhs.ctp.pageobject.DeleteCdssPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;
import uk.nhs.ctp.pageobject.UpdateCdssPage;

public class AuditValidationSteps extends EMSTest {

  @Given("audits have been generated")
  public void auditsHaveBeenGenerated() {
    ems.setUser(User.admin());
    CdssSupplier defaultSupplier = CdssSupplier.builder()
        .name(CDSS.DOCKERIZED.getName())
        .dataRefType(ReferenceType.REFERENCE)
        .paramsRefType(ReferenceType.REFERENCE)
        .baseUrl("http://cdss:8080/fhir")
        .authToken(cactusAuthToken)
        .build();
    ems.setCdssSupplier(defaultSupplier);
    CdssSupplierActions.loginCreateSupplier(ems, true);
  }

  @When("I navigate to the validation page")
  public void iNavigateToTheValidationPage() throws Exception {
    MainPage mainPage = new MainPage(ems.getDriver());
    mainPage.home();
    Thread.sleep(10000); // This should be enough time for ES to retrieve the audits
    mainPage.createValidationReport();
  }

  @Then("I can view the audits")
  public void iCanViewTheAudits() {
    AuditValidationPage validationPage = new AuditValidationPage(ems.getDriver());
    assertThat(validationPage.interactionList(), not(empty()));

    removeCdss();
  }

  private void removeCdss() {
    WebDriver driver = ems.getDriver();
    ManageCdssSuppliersPage manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);
    new MainPage(driver).manageCdssSuppliers();
    manageCdssSuppliersPage.edit(ems.getCdssSupplier().getName());
    new UpdateCdssPage(driver).delete();
    new DeleteCdssPage(driver).delete();
    manageCdssSuppliersPage.onPage();
  }
}
