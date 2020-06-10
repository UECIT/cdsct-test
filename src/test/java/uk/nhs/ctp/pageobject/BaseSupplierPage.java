package uk.nhs.ctp.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseSupplierPage extends PageObject {

  public BaseSupplierPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(xpath = "//input[@formcontrolname=\"name\"]")
  protected WebElement nameTextBox;

  @FindBy(xpath = "//input[@formcontrolname=\"baseUrl\"]")
  protected WebElement baseUrlTextBox;

  @FindBy(id = "authToken")
  protected WebElement authTokenTextBox;

  @FindBy(id = "param-ref-ref-button")
  protected WebElement inputParametersByReferenceButton;

  @FindBy(id = "param-ref-res-button")
  protected WebElement inputParametersByResourceButton;

  @FindBy(id = "data-ref-ref-button")
  protected WebElement inputDataByReferenceButton;

  @FindBy(id = "data-ref-res-button")
  protected WebElement inputDataByResourceButton;

  @FindBy(id = "supportedVersion")
  protected WebElement supportedVersionDropdown;

}
