package uk.nhs.ctp.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.nhs.ctp.actions.LoginActions;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.AuditValidationPage;
import uk.nhs.ctp.pageobject.MainPage;

public class AuditValidationSteps extends EMSTest {

  @Given("audits have been generated")
  public void auditsHaveBeenGenerated() {
    ems.setUser(User.admin());
    LoginActions.login(ems);
  }

  @When("I navigate to the validation page")
  public void iNavigateToTheValidationPage() {
    MainPage mainPage = new MainPage(ems.getDriver());
    mainPage.createValidationReport();
  }

  @Then("I can view the audits")
  public void iCanViewTheAudits() {
    AuditValidationPage validationPage = new AuditValidationPage(ems.getDriver());
    assertThat(validationPage.interactionList(), not(empty()));
  }

}
