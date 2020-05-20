package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CdssSupplier {

  String name;
  String baseUrl;
  ReferenceType dataRefType;
  ReferenceType paramsRefType;

  public enum ReferenceType {
    CONTAINED,
    REFERENCE
  }

}
