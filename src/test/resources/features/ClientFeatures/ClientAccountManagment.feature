Feature: Client Account Management

  Background:
    Given I am on the account management page

  Scenario Outline: Create a Client profile
    When I enter the personal detail "<detail>" with value "<value>"
    And I set the dietary preference "<preference>" to "<preferenceValue>"
    And I add the dietary restriction "<restriction>" to "<restrictionValue>"
    And I save the profile
    Then I should see "<successMessage>"
    Examples:
      | detail | value    | preference | preferenceValue | restriction | restrictionValue | successMessage               |
      | Name   | John Doe | Vegetarian | Yes             | Gluten-Free | No               | Profile created successfully |
      | Name   | Jane Doe | Vegan      | Yes             | Nut-Free    | Yes              | Profile created successfully |

  Scenario Outline: Update an existing profile
    Given I have an existing profile
    When I update the personal detail "<detail>" to "<value>"
    And I update the dietary preference "<preference>" to "<preferenceValue>"
    And I update the dietary restriction "<restriction>" to "<restrictionValue>"
    And I save the changes
    Then I should see "<updateMessage>"
    Examples:
      | detail | value        | preference | preferenceValue | restriction | restrictionValue | updateMessage                |
      | Name   | John Smith   | Vegetarian | No              | Gluten-Free | Yes              | Profile updated successfully |
      | Name   | Jane Johnson | Vegan      | No              | Nut-Free    | No               | Profile updated successfully |