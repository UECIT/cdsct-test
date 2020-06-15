package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class EmsSupplier implements SelectableByName {

  String name;
  String baseUrl;

  @Default
  String authToken = "";

  public static EmsSupplier from(TableRow row) {
    return EmsSupplier.builder()
        .name(row.nextCell())
        .baseUrl(row.nextCell())
        .build();
  }

}
