Feature: Admin Program Monitoring
  Admins can monitor and analyze program performance and progress

  Background:
    Given the admin is logged in

  Scenario: View statistics on the most popular programs by enrollment
    When the admin accesses the program statistics page
    Then the admin can view statistics on the most popular programs
    And filter the statistics by enrollment

  Scenario: Generate reports on revenue, attendance, and client progress
    When the admin generates a program performance report
    Then the report includes revenue data
    And attendance data
    And client progress data

  Scenario: Track active and completed programs
    When the admin accesses program tracking page
    Then the admin can track active programs
    And track completed programs
