package uk.nhs.ctp.steps;

import org.springframework.beans.factory.annotation.Autowired;
import uk.nhs.ctp.model.EMS;

public class EMSTest {

  @Autowired
  protected EMS ems;

  @Autowired
  protected String cactusAuthToken;

}
