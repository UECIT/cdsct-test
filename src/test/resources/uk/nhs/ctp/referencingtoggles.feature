Feature: Referencing toggles

  Background: Set up a new CDSS supplier
    Given a new CDSS supplier

  Scenario: Send referenced resources
    Given sending referenced resources
    When we start a triage journey
    Then the triage journey displays the first question

#  Scenario: Send contained resources
#    Given sending contained resources
#    When we start a triage journey
#    Then the triage journey displays the first question

