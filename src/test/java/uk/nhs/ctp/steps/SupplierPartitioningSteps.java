package uk.nhs.ctp.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.nhs.ctp.actions.CdssSupplierActions;
import uk.nhs.ctp.model.CDSS;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.User;

public class SupplierPartitioningSteps extends EMSTest {

  @Given("two supplier accounts")
  public void sendingReferencedResources() {
    ems.setCdss(CDSS.REFERENCED);
    CdssSupplier cdss1 = CdssSupplier.builder()
        .name(CDSS.REFERENCED.getName())
        .dataRefType(ReferenceType.REFERENCE)
        .paramsRefType(ReferenceType.REFERENCE)
        .baseUrl("http://cdss:8080/fhir/")
        .build();
    ems.setCdssSupplier(cdss1);
    ems.setUser(User.supplier1());

    CdssSupplierActions.loginCreateSupplier(ems);

    CdssSupplierActions.logout(ems);
    ems.setCdss(CDSS.CONTAINED);
    CdssSupplier cdss2 = CdssSupplier.builder()
        .name(CDSS.CONTAINED.getName())
        .dataRefType(ReferenceType.CONTAINED)
        .paramsRefType(ReferenceType.CONTAINED)
        .baseUrl("http://cdss:8080/fhir/")
        .build();
    ems.setCdssSupplier(cdss2);
    ems.setUser(User.supplier2());

    CdssSupplierActions.loginCreateSupplier(ems, false);
  }

  @When("we view supplier A")
  public void viewSupplierA() {

  }

  @Then("supplier B's data is not shown")
  public void noSupplierBData() {

  }
}
