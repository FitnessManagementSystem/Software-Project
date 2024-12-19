Feature: Client Account Management

  Background:
    Given I am on the account management page

  Scenario Outline: Create a Client profile
    When I enter the personal detail "<detail>" with value "<value>"
    And I set the dietary preference "<preference>" to "<preferenceValue>"
    And I add the dietary restriction "<restriction>" to "<restrictionValue>"
    And I set the age to "<ageValue>"
    And I set the fitness goal to "<fitnessGoal>"
    And I save the profile
    Then I should see "<successMessage>"

    Examples:
      | detail | value    | preference | preferenceValue | restriction | restrictionValue | ageValue | fitnessGoal | successMessage                       |
      | Name   | John Doe | Vegetarian | Yes             | Gluten-Free | No               | 25       | Weight Loss | Profile created successfully         |
      | Name   | Alice    | Vegetarian | Yes             | Gluten-Free | No               | 20       | Weight Loss | Profile created successfully         |
      | Name   |          | Vegetarian | Yes             | Gluten-Free | No               | 25       | Weight Loss | Name is empty                        |
      | Name   | Alice    | Vegetarian | Yes             | Gluten-Free | Yes              | 20       | Weight Loss | Profile already exists for this name |


