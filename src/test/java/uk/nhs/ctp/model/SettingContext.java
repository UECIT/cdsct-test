package uk.nhs.ctp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SettingContext implements SelectableByName {

  ONLINE("Online"),
  PHONE_CALL("Phone Call"),
  FACE_TO_FACE("Face to face");

  /**
   *  represents how the setting is displayed on screen on the main page
   */
  private String name;

}
