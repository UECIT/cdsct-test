package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CDSS implements SelectableByName {

  LOCAL("Local"),
  DOCKERIZED("Dockerized CDSS");

  private String name;

}
