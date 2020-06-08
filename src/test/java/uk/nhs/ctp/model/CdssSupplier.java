package uk.nhs.ctp.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CdssSupplier implements SelectableByName{

  String name;
  String baseUrl;
  ReferenceType dataRefType;
  ReferenceType paramsRefType;

  public enum ReferenceType {
    CONTAINED,
    REFERENCE
  }

}
