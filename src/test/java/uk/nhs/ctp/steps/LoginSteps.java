package uk.nhs.ctp.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.nhs.ctp.model.User;
import uk.nhs.ctp.pageobject.LoginPage;
import uk.nhs.ctp.pageobject.MainPage;

public class LoginSteps extends EMSTest {

  @Given("^an admin user$")
  public void setupAdminUser() {
    ems.setUser(User.admin());
  }

  @When("^the user logs in$")
  public void login() {
    LoginPage loginPage = new LoginPage(ems.getDriver());
    loginPage.login(ems.getUser());
  }

  @Then("^the EMS home page is displayed$")
  public void assertHomePage() {
    MainPage mainPage = new MainPage(ems.getDriver());
    assertThat(mainPage.onPage(), is(true));
  }
}
