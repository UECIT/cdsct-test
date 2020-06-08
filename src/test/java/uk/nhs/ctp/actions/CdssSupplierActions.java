package uk.nhs.ctp.actions;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import uk.nhs.ctp.model.EMS;
import uk.nhs.ctp.pageobject.CreateNewSupplierPage;
import uk.nhs.ctp.pageobject.LoginPage;
import uk.nhs.ctp.pageobject.MainPage;
import uk.nhs.ctp.pageobject.ManageCdssSuppliersPage;

@UtilityClass
public class CdssSupplierActions {

  public void loginCreateSupplier(EMS ems) {
    loginCreateSupplier(ems, true);
  }

  public void loginCreateSupplier(EMS ems, boolean expectSnackbar) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);

    login(ems);
    if (expectSnackbar) {
      mainPage.dismissSnackBar();
    }
    registerCDSS(ems);
  }

  public void registerCDSS(EMS ems) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    ManageCdssSuppliersPage manageCdssSuppliersPage = new ManageCdssSuppliersPage(driver);
    CreateNewSupplierPage createNewSupplierPage = new CreateNewSupplierPage(driver);

    mainPage.manageCdssSuppliers();
    manageCdssSuppliersPage.createNewSupplier();
    createNewSupplierPage.create(ems.getCdssSupplier());
    manageCdssSuppliersPage.onPage();
  }

  public void login(EMS ems) {
    WebDriver driver = ems.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    loginPage.login(ems.getUser());
  }

  public void logout(EMS ems) {
    WebDriver driver = ems.getDriver();
    MainPage mainPage = new MainPage(driver);
    mainPage.logout();
  }

}