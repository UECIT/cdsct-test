package uk.nhs.ctp.actions;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.model.EMS;
import uk.nhs.ctp.pageobject.CreateNewCdssPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;

@UtilityClass
public class CdssSupplierActions {

  /**
   * Expects the snack bar by default
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
    registerCDSSFromMainPage(ems);
  }

  public void registerCDSSFromMainPage(EMS ems) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    ManageCdssSuppliersPage manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);
    CreateNewCdssPage createNewCdssPage = new CreateNewCdssPage(driver);

    mainPage.manageCdssSuppliers();
    manageCdssSuppliersPage.createNewSupplier();
    createNewCdssPage.create(ems.getCdssSupplier());
    manageCdssSuppliersPage.onPage();
  }

}
