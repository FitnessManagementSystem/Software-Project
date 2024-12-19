Feature: Program Exploration and Enrollment

  Background:
    Given I am on the Program Exploration and Enrollment page

  Scenario Outline: Browse programs by filters
    When I select the "<Filter Type>" filter
    And I press on search
    Then I should see a list of programs such as "<Filter Value>" based on "<Filter Type>"

    Examples:
      | Filter Type |  | Filter Value    |
      | difficulty  |  | Beginner        |
      | difficulty  |  | Intermediate    |
      | difficulty  |  | Advanced        |
      | focus area  |  | Weight loss     |
      | focus area  |  | Muscle building |
      | focus area  |  | Flexibility     |

  Scenario Outline: Enroll in a program
    When I confirm my enrollment to a program "<Program Name>" by "<username>" and status "<status>"
    Then I should see the message "<Enrollment Message>"

    Examples:
      | username |  | Program Name |  | status    | Enrollment Message                       |
      | Arqam    |  | Weight Loss  |  | active    | Enrolled successfully                    |
      | Arqa     |  | Yoga         |  | completed | The program is completed                 |
      | 2211     |  | Flexibility  |  | completed | User doesn't exist                       |
      | AA       |  |              |  | active    | The program doesn't exist                |
      | Arqam    |  | Weight Loss  |  | active    | User is already enrolled in this program |

