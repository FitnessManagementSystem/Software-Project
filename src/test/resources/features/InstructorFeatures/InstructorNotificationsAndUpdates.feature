Feature: Notifications and Updates

  Background:
    Given the instructor is logged into the system
    And the instructor is on program management page


  Scenario: Announce new programs or special offers
    When the instructor creates a new program
    Then the instructor can announce that they created a program
    And announce special offers



