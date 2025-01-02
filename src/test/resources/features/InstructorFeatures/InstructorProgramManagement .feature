Feature: Instructor Program Management

  Background:
    Given the instructor is logged into the system

  Scenario Outline: Create, update, or delete fitness programs
    When I enter the Program_title "<Program_title>" to "<Program_title_Value>"
    And I set the Duration "<Duration>" to "<Duration_Value>"
    And I add the Difficulty level "<Difficulty_level>" to "<Difficulty_level_Value>"
    And I set the Price <Price> to "<Price_Value>"
    And I save the profile
    Then I should see "<successMessage>" and announce the clients about it
    Examples:
      | Program_title       | Program_title_Value | Duration | Duration_Value | Difficulty_level | Difficulty_level_Value | Price | Price_Value | successMessage                        |
      | Weight Loss Program | Slim & Fit          | 30 Days  | 30             | Beginner         | Easy                   | 50    | 50 USD      | Program created successfully!         |
      | Muscle Building     | Strength Master     | 60 Days  | 60             | Intermediate     | Moderate               | 100   | 100 USD     | Program created successfully!         |
      | Weight Loss Program | Slim & Fit          | 14 Days  | 14             | Beginner         | Easy                   | 25    | 25 USD      | Program already exists for this title |
      |                     |                     | 90 Days  | 90             | Advanced         | Hard                   | 120   | 120 USD     | Title is empty                        |

  Scenario Outline: delete fitnes program
    When I enter the Program_title "<Program_title>" to "<Program_title_Value>"
    And I delete the program
    Then I should see "<successMessage>" for deleting program.
    Examples:
      | Program_title       | Program_title_Value  | successMessage               |
      | Weight Loss         | Slim & Fit           | Program deleted successfully |
      | Muscle Building     | Strength Master      | Program deleted successfully |
      |                     |                      | Title is empty               |
      | Flexibility Program | Stretch & Strengthen | Program does not exist       |
      | Cardio Blast        | Run & Burn           | Program does not exist       |

