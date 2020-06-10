Feature: create and manage CDSS Suppliers

  Scenario: Create and view a new CDSS Supplier
    Given a new CDSS supplier
    When I create the new supplier
    Then I can view the new CDSS Supplier details

  Scenario: Set the auth token for a CDSS supplier
    Given a new CDSS supplier with an auth token
    When I create the new supplier
    Then the created supplier has the auth token