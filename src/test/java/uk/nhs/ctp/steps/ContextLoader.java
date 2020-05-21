package uk.nhs.ctp.steps;

import io.cucumber.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import uk.nhs.ctp.context.Context;

@ContextConfiguration(classes = Context.class)
@SpringBootTest
public class ContextLoader {

  @Before
  public void setup() {

  }

}
