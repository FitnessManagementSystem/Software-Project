Feature: Admin Program Monitoring

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: View program statistics
    When I navigate to the Program Monitoring section
    Then I should see the report displayed
    And I should see the success message "<successMessage>"

    Examples:
      | successMessage                       |
      | The report is successfully generated |
