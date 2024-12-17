Feature: Notifications and Updates
  Fitness instructors can notify clients about program updates and special announcements.

  Background:
    Given the instructor is logged into the system
    And the instructor is on program management page

  Scenario: Notify clients about schedule changes
    When the instructor updates a program schedule
    Then the instructor can notify the client about the changes

  Scenario: Announce new programs or special offers
    When the instructor creates a new program
    Then the instructor can announce that they created a program
    And announce special offers



