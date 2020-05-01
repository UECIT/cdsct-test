Feature: Login

  Scenario: An admin user logs in
    Given an admin user
    When the user logs in
    Then the EMS home page is displayed
