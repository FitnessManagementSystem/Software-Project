Feature: Rate and review completed programs

  Background:
    Given I am on the Feedback and Reviews page
    And I have completed the program

  Scenario Outline: Rate and review completed programs
    When I want to rate and review the program "<Program Name>" with rating "<Program Rate>" and review "<Review>"
    Then I should see a message "<Submission Message>"

    Examples:

      | Program Name    | Program Rate | Review               | Submission Message                          |
      | Weight Loss     | 9            | It was so good       | Program does not exist or is not completed. |
      | Flexibility     | 10           | Life-changing        | Review submitted successfully               |
      | Yoga            | 0            | What is this         | Invalid rating. Please try again.           |
      | Weight Loss     | -1           | Negative rating test | Invalid rating. Please try again.           |
      | Flexibility     | 11           | Exceeds rating limit | Invalid rating. Please try again.           |
      | Yoga            | 5            |                      | Invalid review. Please try again.           |
      | Muscle Building | 5            | Could be better      | Program does not exist or is not completed. |
      | Nonexistent     | 8            | Program not found    | Program does not exist or is not completed. |


