package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CDSS implements SelectableByName {

  LOCAL("Local"),
  DOCKERIZED("Dockerized CDSS"),
  REFERENCED("CDSS Test Supplier (Reference)"),
  CONTAINED("CDSS Test Supplier (Contained)");

  private String name;

}
