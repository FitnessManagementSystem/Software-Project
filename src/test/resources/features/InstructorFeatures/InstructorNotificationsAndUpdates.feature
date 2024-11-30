Feature: Notifications and Updates
  Fitness instructors can notify clients about program updates and announce new offerings.

  Scenario: Notify clients about schedule changes
    Given the instructor updates a program schedule
    When the changes are saved
    Then the system sends notifications to all enrolled clients about the updated schedule

  Scenario: Announce new programs or special offers
    Given the instructor has created or edited a program
    When the instructor choose "Announce Program"
    And specifies the target audience
    Then the system sends announcements to the selected clients or groups
