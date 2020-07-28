Feature: Create and View Audits for Validation

  Scenario: Generate audits by simply going to the main page and view them in the validation page
    Given audits have been generated
    When I navigate to the validation page
    Then I can view the audits