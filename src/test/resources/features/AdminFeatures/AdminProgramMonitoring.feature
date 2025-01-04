Feature: Admin Program Monitoring

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: View program statistics
    When I navigate to the Program Monitoring section
    And I should see the success message "<successMessage>"
    Examples:
      | successMessage                                              |
      | The report is successfully generated and ready for viewing. |
      | No programs or enrollment data available.                   |
      | No report data found.                                       |
      | An error occurred while generating the report.              |
      | An error occurred                                           |

  Scenario Outline: Track active and completed programs
    When I navigate to the Program Monitoring section
    Then I should see the count of active programs as "<activePrograms>"
    And I should see the count of completed programs as "<completedPrograms>"

    Examples:
      | activePrograms | completedPrograms |
      | 2              | 3                 |
      | 0              | 0                 |