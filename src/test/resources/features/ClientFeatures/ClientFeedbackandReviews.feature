Feature: Rate and review completed programs

  Background:
    Given I am on the Feedback and Reviews page
    And I have completed the program

  Scenario Outline: Rate and review completed programs
    When I want to rate and review the program "<Program Name>"
    And I want to enter my rating "<Program Rate>" and my review "<Review>"
    Then I should see "<Program Rate>" and "<Review>"
    And I should submit my rate and review

    Examples:
      | Program Name | Program Rate | Review         |
      | Weight Loss  | 9            | It was so good |

  Scenario Outline: Submit improvement suggestions to instructors
    When I want to submit improvement suggestions to instructors
    And I want to enter my improvement suggestion "<Improvement Suggestions>"
    Then I should see my "<Improvement Suggestions>"
    And I should submit my improvement suggestions

    Examples:
      | Improvement Suggestions |
      | Add feature x           |
