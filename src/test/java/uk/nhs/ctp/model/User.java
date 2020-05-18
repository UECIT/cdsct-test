package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

  String username;
  String password;

  public static User admin() {
    return User.builder()
        .username("admin")
        .password("admin@123")
        .build();
  }

}
