Feature: Referencing toggles

  Scenario: Send referenced resources
    Given sending referenced resources
    When we start a triage journey
    Then the triage journey displays the first question

  Scenario: Send contained resources
    Given sending contained resources
    When we start a triage journey
    Then the triage journey displays the first question

