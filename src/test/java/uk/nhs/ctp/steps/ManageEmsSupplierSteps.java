package uk.nhs.ctp.steps;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import uk.nhs.ctp.actions.EmsSupplierActions;
import uk.nhs.ctp.model.EmsSupplier;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.ManageEmsSuppliersPage;

public class ManageEmsSupplierSteps extends EMSTest {

  @Given("a new EMS supplier")
  public void aNewEmsSupplier() {
    EmsSupplier supplier = EmsSupplier.builder()
        .name("EMS without custom auth")
        .baseUrl("does.not.matter")
        .build();
    ems.setEmsSupplier(supplier);
    ems.setUser(User.admin());
  }

  @Given("a new EMS supplier with an auth token")
  public void aNewEmsSupplierWithAuthToken() {
    EmsSupplier supplier = EmsSupplier.builder()
        .name("EMS with custom auth")
        .baseUrl("does.not.matter")
        .authToken("token")
        .build();
    ems.setEmsSupplier(supplier);
    ems.setUser(User.admin());
  }

  @When("I create the new EMS supplier")
  public void iCreateTheNewSupplier() {
    EmsSupplierActions.loginCreateSupplier(ems);
  }

  @Then("I can view the new EMS Supplier details")
  public void emsSupplierHasAuthToken() {
    ManageEmsSuppliersPage suppliersPage = new ManageEmsSuppliersPage(ems.getDriver());
    suppliersPage.onPage();
    suppliersPage.suppliersListNonEmpty();

    List<EmsSupplier> suppliers = suppliersPage.supplierList();
    assertThat(suppliers, hasItem(sameBeanAs(ems.getEmsSupplier())
        .ignoring("authToken")));

    suppliersPage.edit(ems.getEmsSupplier());

    assertThat(suppliersPage.currentlyEditing(), sameBeanAs(ems.getEmsSupplier()));
  }
}
