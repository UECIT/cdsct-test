package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SelectionMode implements SelectableByName {

  AUTOMATED("Automated"),
  MANUAL("Manual");

  private String name;

}
