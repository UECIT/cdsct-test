package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Patient implements SelectableByName {

  JOE_BLOGGS("Mr Joe Bloggs");

  /**
   * Full name represents how the patient is displayed on screen on the main page
   */
  private String name;

}
