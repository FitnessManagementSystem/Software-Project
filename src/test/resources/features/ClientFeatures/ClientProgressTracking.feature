Feature: Progress Tracking

  Background:
    Given I am on the Progress Tracking page

  Scenario Outline: Track personal fitness milestones
    When I want to track my personal fitness milestones
    Then I should see my personal fitness milestone "<parameter>" with the value "<value>"

    Examples:
      | parameter  | value |
      | Weight     | xx kg |
      | BMI        | xx    |
      | Attendance | xxx   |

  Scenario Outline: View achievements or badges for completing programs
    When I want to view my achievements or badges for completing programs
    Then I should see a list of the programs I have completed, with the achievement or badge "<badge>"

    Examples:
      | badge       |
      | Weight Loss |
      | Muscle Gain |
      | Flexibility |
