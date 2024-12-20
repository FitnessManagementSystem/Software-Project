Feature: Admin User Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Manage user accounts
    When I navigate to the User Management section
    And I "<Action>" an account for "<User Type>"
    Then I should see the account is "<Status>"

    Examples:
      | Action     | User Type      | Status      |
      | add        | instructor     | created     |
      | add        | client         | created     |
      | update     | instructor     | updated     |
      | update     | client         | updated     |
      | deactivate | instructor     | deactivated |
      | deactivate | client         | deactivated |
      | approve    | new instructor | approved    |
