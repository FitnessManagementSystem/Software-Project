Feature: Admin Subscription Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Manage subscription plans for instructors and clients
    When I navigate to the Subscription Management section
    And I "<Action>" the "<Subscription Plan>" plan for "<User Type>"
    Then the plan status should be "<Status>"

    Examples:
      | Action     | Subscription Plan | User Type  | Status      |
      | activate   | Basic             | instructor | active      |
      | activate   | Premium           | instructor | active      |
      | deactivate | Basic             | instructor | deactivated |
      | deactivate | Premium           | instructor | deactivated |
      | activate   | Basic             | client     | active      |
      | activate   | Premium           | client     | active      |
      | deactivate | Basic             | client     | deactivated |
      | deactivate | Premium           | client     | deactivated |
