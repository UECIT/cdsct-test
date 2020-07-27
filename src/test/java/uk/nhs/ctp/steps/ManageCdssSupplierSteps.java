package uk.nhs.ctp.steps;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.actions.CdssSupplierActions;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.CdsApiVersion;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.DeleteCdssPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;
import uk.nhs.ctp.pageobject.UpdateCdssPage;

public class ManageCdssSupplierSteps extends EMSTest {

  @Given("a new CDSS supplier")
  public void aNewCDSSSupplier() {
    CdssSupplier version2Supplier = CdssSupplier.builder()
        .name("This Cdss Supports V2")
        .baseUrl("does.not.matter")
        .supportedVersion(CdsApiVersion.TWO)
        .paramsRefType(ReferenceType.REFERENCE)
        .dataRefType(ReferenceType.CONTAINED)
        .build();
    ems.setCdssSupplier(version2Supplier);
    ems.setUser(User.admin());
  }

  @When("I create the new supplier")
  public void iCreateTheNewSupplier() {
    CdssSupplierActions.loginCreateSupplier(ems);
  }

  @Then("I can view the new CDSS Supplier details")
  public void iCanViewTheNewCDSSSupplierDetails() {
    WebDriver driver = ems.getDriver();
    ManageCdssSuppliersPage suppliersPage = new ManageCdssSuppliersPage(driver);

    assertThat(suppliersPage.supplierList(),
        hasItem(sameBeanAs(ems.getCdssSupplier())));

    //Tidy up CDSS
    suppliersPage.edit(ems.getCdssSupplier().getName());
    UpdateCdssPage updateCdssPage = new UpdateCdssPage(driver);
    updateCdssPage.delete();
    DeleteCdssPage deleteCdssPage = new DeleteCdssPage(driver);
    deleteCdssPage.delete();
    suppliersPage.onPage();
  }

  @Given("a new CDSS supplier with an auth token")
  public void aNewCDSSSupplierWithAuth() {
    CdssSupplier version2Supplier = CdssSupplier.builder()
        .name("This Cdss has an auth token")
        .baseUrl("does.not.matter")
        .authToken("token")
        .build();
    ems.setCdssSupplier(version2Supplier);
    ems.setUser(User.admin());
  }

  @Then("the created supplier has the auth token")
  public void cdssSupplierHasAuthToken() {
    ManageCdssSuppliersPage suppliersPage = new ManageCdssSuppliersPage(ems.getDriver());
    UpdateCdssPage updatePage = new UpdateCdssPage(ems.getDriver());
    suppliersPage.edit(ems.getCdssSupplier().getName());
    updatePage.onPage();

    assertThat(updatePage.getAuthToken(), equalTo("token"));

    // Tidy up CDSS
    updatePage.delete();
    DeleteCdssPage deleteCdssPage = new DeleteCdssPage(ems.getDriver());
    deleteCdssPage.delete();
    suppliersPage.onPage();
  }

}
