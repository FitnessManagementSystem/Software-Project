Feature: Program Exploration and Enrollment

  Background:
    Given I am on the Program Exploration and Enrollment page

  Scenario Outline: Browse programs by filters
    When I want to browse programs
    And I select the "<Filter Type>" filter
    Then I should see a list of programs based on "<Filter Value>"

    Examples:
      | Filter Type      | Filter Value    |
      | Difficulty level | Beginner        |
      | Difficulty level | Intermediate    |
      | Difficulty level | Advanced        |
      | Focus area       | Weight loss     |
      | Focus area       | Muscle building |
      | Focus area       | Flexibility     |

  Scenario Outline: Enroll in a program
    When I want to enroll in a program
    And I select the program named "<Program Name>"
    And I confirm my enrollment
    Then I should see the message "<Enrollment Message>"

    Examples:
      | Program Name    | Enrollment Message    |
      | Weight Loss     | Enrolled successfully |
      | Muscle Building | Enrolled successfully |
      | Flexibility     | Enrolled successfully |
