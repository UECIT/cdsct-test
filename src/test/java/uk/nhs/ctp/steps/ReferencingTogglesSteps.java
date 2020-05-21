package uk.nhs.ctp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.nhs.ctp.actions.CdssSupplierActions;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.User;

public class ReferencingTogglesSteps extends EMSTest {

  @Given("sending referenced resources")
  public void sendingReferencedResources() {
    CdssSupplier cdssSupplier = CdssSupplier.builder()
        .name("CDSS Test Supplier (Reference)")
        .dataRefType(ReferenceType.REFERENCE)
        .paramsRefType(ReferenceType.REFERENCE)
        .baseUrl("http://localhost:8080/fhir/")
        .build();
    ems.setCdssSupplier(cdssSupplier);
    ems.setUser(User.admin());

    CdssSupplierActions.loginCreateSupplier(ems);
  }

  @Given("sending contained resources")
  public void sendingContainedResources() {
    CdssSupplier cdssSupplier = CdssSupplier.builder()
        .name("CDSS Test Supplier (Contained)")
        .dataRefType(ReferenceType.CONTAINED)
        .paramsRefType(ReferenceType.CONTAINED)
        .baseUrl("http://localhost:8080/fhir/")
        .build();
    ems.setCdssSupplier(cdssSupplier);
    ems.setUser(User.admin());

    CdssSupplierActions.loginCreateSupplier(ems);
  }

  @When("we start a triage journey")
  public void weStartATriageJourney() {
  }

  @Then("the triage journey displays the first question")
  public void theTriageJourneyDisplaysTheFirstQuestion() {
  }


}
