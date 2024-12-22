Feature: Admin Program Monitoring

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: View program statistics
    When I navigate to the Program Monitoring section
    And I view "<Report Type>"
    Then I should see "<Status>" displayed

    Examples:
      | Report Type            | Status                          |
      | most popular programs  | programs sorted by enrollment   |
      | revenue reports        | revenue statistics displayed    |
      | attendance reports     | attendance statistics displayed |
      | active and completed   | active and completed programs   |
      | client progress report | client progress statistics      |

  Scenario: Track program progress
    When I navigate to the Program Monitoring section
    And I track program progress
    Then I should see a list of active and completed programs
