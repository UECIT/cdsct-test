Feature: Supplier Partitioning

  Scenario: Supplier data is partitioned
    Given two supplier accounts
    When we view supplier A
    Then supplier B's data is not shown

