package uk.nhs.ctp.actions;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.model.EMS;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageEmsSuppliersPage;

@UtilityClass
public class EmsSupplierActions {

  /**
   * Expects the snack bar by default
   *
   * @param ems
   */
  public void loginCreateSupplier(EMS ems) {
    loginCreateSupplier(ems, true);
  }

  public void loginCreateSupplier(EMS ems, boolean expectSnackbar) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);

    LoginActions.login(ems);
    if (expectSnackbar) {
      mainPage.dismissSnackBar();
    }
    registerSupplierFromMainPage(ems);
  }

  public void registerSupplierFromMainPage(EMS ems) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    var manageEmsSuppliersPage = new ManageEmsSuppliersPage(driver);

    mainPage.manageEmsSuppliers();
    manageEmsSuppliersPage.create(ems.getEmsSupplier());
    manageEmsSuppliersPage.onPage();
    manageEmsSuppliersPage.finishedEditing();
  }
}
