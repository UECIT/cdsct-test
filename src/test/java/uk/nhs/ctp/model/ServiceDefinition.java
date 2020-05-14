package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceDefinition implements SelectableByName {

  CHEST_PAINS("Chest pains 1st Party");

  /**
   * represents how the service definition is displayed on screen on the main page
   */
  String name;

}
