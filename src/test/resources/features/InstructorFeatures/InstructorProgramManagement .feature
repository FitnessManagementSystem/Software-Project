Feature: Instructor Program Management

  Background:
    Given the instructor is logged into the system

  Scenario Outline: Create, update, or delete fitness programs
    When the instructor is accessing the program management page
    Then the instructor can create a program with "<Component>"
    And update a program with "<Component>"
    And delete a program with "<Component>"

    Examples:
      | Component             |
      | Program title         |
      | Duration              |
      | Difficulty level      |
      | Price (if applicable) |

