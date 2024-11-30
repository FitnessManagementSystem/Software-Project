Feature: Progress Tracking
  Fitness instructors can monitor the progress of their clients and offer motivational support.

  Scenario: View client progress
    Given the instructor is viewing their list of enrolled clients
    When the instructor selects a client
    Then the system displays the client’s attendance, completion rate, and performance metrics

  Scenario: Send motivational reminders or recommendations
    Given the instructor is viewing a client’s profile
    When the instructor click "Send Reminder"
    And enters a motivational message or recommendation
    Then the system sends the notification to the client
