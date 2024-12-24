Feature: Progress Tracking

  Background:
    Given I am on the Progress Tracking page

  Scenario: View achievements or badges for completing programs
    When I want to see my completed programs list
    Then I should see a list of the programs I have completed, with the program name