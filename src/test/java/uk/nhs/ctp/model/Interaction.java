package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Interaction {

  String type;
  String date;
  String interactionId;

  public static Interaction from(TableRow row) {
    return Interaction.builder()
        .type(row.nextCell())
        .date(row.nextCell())
        .build();
  }

}
