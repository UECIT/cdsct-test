package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType implements SelectableByName {

  PATIENT("Patient"),
  RELATED_PERSON("Related Person");

  private String name;

}
