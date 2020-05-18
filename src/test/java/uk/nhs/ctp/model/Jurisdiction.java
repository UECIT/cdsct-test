package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Jurisdiction implements SelectableByName {

  UK("United Kingdom of Great Britain and Northern Ireland (the)"),
  TOKELAU("Tokelau");

  private String name;

}
