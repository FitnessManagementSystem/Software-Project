Feature: Client Account Management

  Background:
    Given I am on the account management page

  Scenario Outline: Create a Client profile
    When I enter the name to "<name>", dietary preference to "<preference>", dietary restriction to "<restriction>", the age to "<ageValue>", the fitness goal to "<fitnessGoal>" and save
    Then I should see "<successMessage>"

    Examples:
      | name     | preference | restriction | ageValue | fitnessGoal | successMessage                       |
      |          | Vegetarian | Gluten-Free | 25       | Weight Loss | Name is empty                        |
      | John Doe | Vegetarian | Gluten-Free | 25       | Weight Loss | Profile created successfully         |
      | Jack     | Vegetarian | Gluten-Free | 20       | Weight Loss | Profile already exists for this name |


