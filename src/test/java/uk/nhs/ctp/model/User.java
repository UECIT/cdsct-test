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

  public static User supplier1() {
    return User.builder()
        .username("supplier1")
        .password("admin@123")
        .build();
  }

  public static User supplier2() {
    return User.builder()
        .username("supplier2")
        .password("admin@123")
        .build();
  }

}
