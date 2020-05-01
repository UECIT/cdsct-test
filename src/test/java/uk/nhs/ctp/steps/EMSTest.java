package uk.nhs.ctp.steps;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import uk.nhs.ctp.context.Context;
import uk.nhs.ctp.model.EMS;

@ContextConfiguration(classes = {Context.class})
public class EMSTest {

  @Autowired
  protected WebDriver driver;

  @Autowired
  protected EMS ems;

}
