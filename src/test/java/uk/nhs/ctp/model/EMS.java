package uk.nhs.ctp.model;

import lombok.Data;

@Data
public class EMS {

  User user;

  public void reset() {
    this.user = null;
  }

}
