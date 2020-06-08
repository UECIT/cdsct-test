package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CDSS implements SelectableByName {

  LOCAL("Local"),
  DOCKERIZED("Dockerized CDSS"),
  REFERENCED("Referenced"),
  CONTAINED("Contained"),
  SUPPLIER_A("Supplier A"),
  SUPPLIER_B("Supplier B");

  private String name;

}
