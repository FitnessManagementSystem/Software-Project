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
      | Goals                 |
      | Video tutorials       |
      | Images                |
      | Documents             |
      | Price (if applicable) |

  Scenario Outline: Set schedules for group sessions
    When the instructor is accessing the program management page
    Then the instructor creates group sessions
    And the instructor can set schedules for group sessions with the following properties "<Component>"
    Examples:
      | Component |
      | online    |
      | in-person |