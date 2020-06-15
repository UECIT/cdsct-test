Feature: Create and manage EMS Suppliers

  Scenario: Create and view a new EMS Supplier
    Given a new EMS supplier
    When I create the new EMS supplier
    Then I can view the new EMS Supplier details

  Scenario: Set the auth token for a EMS supplier
    Given a new EMS supplier with an auth token
    When I create the new EMS supplier
    Then I can view the new EMS Supplier details