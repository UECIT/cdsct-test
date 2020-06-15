package uk.nhs.ctp.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.actions.CdssSupplierActions;
import uk.nhs.ctp.actions.LoginActions;
import uk.nhs.ctp.model.CDSS;
import uk.nhs.ctp.model.CdssSupplier;
import uk.nhs.ctp.model.CdssSupplier.ReferenceType;
import uk.nhs.ctp.model.SelectionMode;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;

public class SupplierPartitioningSteps extends EMSTest {

  CdssSupplier cdss1 = CdssSupplier.builder()
      .name(CDSS.SUPPLIER_A.getName())
      .dataRefType(ReferenceType.REFERENCE)
      .paramsRefType(ReferenceType.REFERENCE)
      .baseUrl("http://cdss:8080/fhir/")
      .build();

  CdssSupplier cdss2 = CdssSupplier.builder()
      .name(CDSS.SUPPLIER_B.getName())
      .dataRefType(ReferenceType.CONTAINED)
      .paramsRefType(ReferenceType.CONTAINED)
      .baseUrl("http://cdss:8080/fhir/")
      .build();

  @Given("two supplier accounts")
  public void createSupplierCDSSInstances() {
    ems.setCdss(CDSS.REFERENCED);
    ems.setCdssSupplier(cdss1);
    ems.setUser(User.supplier1());

    CdssSupplierActions.loginCreateSupplier(ems);

    LoginActions.logout(ems);

    ems.setCdss(CDSS.CONTAINED);
    ems.setCdssSupplier(cdss2);
    ems.setUser(User.supplier2());

    CdssSupplierActions.loginCreateSupplier(ems, false);
  }

  @When("we view supplier A")
  public void viewSupplierA() {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    ManageCdssSuppliersPage manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);

    LoginActions.logout(ems);

    ems.setUser(User.supplier1());
    LoginActions.login(ems);

    mainPage.onPage();
  }

  @Then("supplier B's data is not shown")
  public void noSupplierBData() {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    mainPage.selectSelectionMode(SelectionMode.MANUAL);
    mainPage.selectCDSS(CDSS.SUPPLIER_A);

    List<String> cdssNames = mainPage.listCDSS();
    assertThat(cdssNames, not(hasItem(CDSS.SUPPLIER_B.name())));
  }
}
