Feature: Program Exploration and Enrollment

  Background:
    Given I am on the Program Exploration and Enrollment page

  Scenario Outline: Browse programs by difficulty level
    When I want to browse programs
    And I select the "<FilterType>" filter
    Then I should see a list of programs with the selected filter type

    Examples:
      | Difficulty level | Focus Area      |
      | Beginner         | Weight loss     |
      | Intermediate     | Muscle Building |
      | Advanced         | Flexibility     |


  Scenario Outline: Enroll in a program
    When I want to enroll in a program
    And I select the program named "<Program name>"
    And I confirm my enrollment
    Then I should see the message "<Enroll Message>"

    Examples:
      | Program name    | Enroll Message        |
      | Weight Loss     | Enrolled successfully |
      | Muscle Building | Enrolled successfully |
      | Flexibility     | Enrolled successfully |
