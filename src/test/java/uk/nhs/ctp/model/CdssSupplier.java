package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CdssSupplier implements SelectableByName {

  String name;
  String baseUrl;
  String serviceDescriptions;
  ReferenceType dataRefType;
  ReferenceType paramsRefType;
  CdsApiVersion supportedVersion;

  @Default
  String authToken = "";

  public enum ReferenceType {
    CONTAINED,
    REFERENCE;

    public static ReferenceType fromDisplay(String displayText) {
      switch (displayText) {
        case "Referenced": return REFERENCE;
        case "Contained" : return CONTAINED;
        default: throw new IllegalArgumentException();
      }
    }
  }

  @RequiredArgsConstructor
  @Getter
  public enum CdsApiVersion {
    ONE_ONE("1.1"),
    TWO("2.0");

    private final String display;

    public static CdsApiVersion fromDisplay(String displayText) {
      switch (displayText) {
        case "1.1": return ONE_ONE;
        case "2.0" : return TWO;
        default: throw new IllegalArgumentException();
      }
    }
  }

  public static CdssSupplier from(TableRow row) {
    return CdssSupplier.builder()
        .name(row.nextCell())
        .baseUrl(row.nextCell())
        .serviceDescriptions(row.nextCell())
        .dataRefType(ReferenceType.fromDisplay(row.nextCell()))
        .paramsRefType(ReferenceType.fromDisplay(row.nextCell()))
        .supportedVersion(CdsApiVersion.fromDisplay(row.nextCell()))
        .authToken("")
        .build();
  }

}
