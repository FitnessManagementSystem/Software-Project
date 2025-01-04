Feature: Admin User Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Manage user accounts
    When I navigate to the User Management section
    And I "<Action>" an account for "<User Type>" with name "<Name>", password "<Password>", and role "<Role>"
    Then I should see the account is "<Status>" with message "<successMessage>"

    Examples:
      | Action     | User Type  | Name         | Password    | Role       | Status         | successMessage                                          |
      | add        | client     |              | password123 | Instructor | created        | Client name is empty                                    |
      | add        | client     | John Doe     |             | Instructor | created        | Password is empty                                       |
      | add        | client     | John Doe     | password123 |            | created        | Role is empty                                           |
      | add        | instructor | John Doe     | password123 | Instructor | created        | Instructor created successfully!                        |
      | add        | instructor | John Doe     | password123 | Instructor | already exists | Account for instructor already exists with this name!   |
      | approve    | instructor | John Doe     | password789 | Instructor | not approved   | Instructor account approved successfully!             |

      | add        | client     | Jane Smith   | password456 | Client     | created        | Client created successfully!                            |
      | deactivate | instructor | John Doe     | password123 | Instructor | deactivated    | Account for instructor deactivated successfully!        |
      | add        | client     | Jane Smith   | password456 | Client     | already exists | Account for client already exists with this name!       |

      | deactivate | client     | Jane Smith   | password456 | Client     | deactivated    | Account for client deactivated successfully!            |
      | deactivate | client     | Khaled Ahmad | password321 | Client     | not found      | Account for client not found in the system!            |
      | deactivate | instructor | Mike Brown   | password789 | Instructor | not found      | Account for instructor not found in the system!         |
      | approve    | instructor | Emily Green  | password000 | Instructor | not approved   | Instructor account not approved due to incomplete data! |
