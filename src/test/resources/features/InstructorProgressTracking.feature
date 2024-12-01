Feature: Instructor Progress Tracking
  Fitness instructors can monitor and support client progress.

  Background:
    Given the instructor is logged into the system

  Scenario Outline: Monitor client progress
    When the instructor accesses client progress page
    Then the instructor can monitor client progress such as "<Component>"
    Examples:
      | Component       |
      | completion rate |
      | attendance      |

  Scenario: Send motivational reminders or recommendations
    Given the instructor is on client progress page
    When the instructor enters a client's ID
    Then the instructor can send motivational reminders
    And the instructor can send recommendation


