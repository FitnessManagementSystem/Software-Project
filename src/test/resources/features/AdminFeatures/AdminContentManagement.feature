Feature: Content Management

  Scenario Outline: Handle user feedback and complaints
    Given I am logged in
    When I navigate to the Content Management section
    And I handle feedback from "<Instructor>" to "<Client>"
    Then I should set status to "<Status>" with the success message "<SuccessMessage>"

    Examples:
      | Instructor  | Client  | SuccessMessage                                             | Status  |
      |             | Client1 | Instructor name is empty                                   |         |
      | Instructor1 |         | Client name is empty                                       |         |
      | Instructor1 | Client1 | Feedback from Instructor1 to Client1 handled successfully! | handled |
      | Instructor2 | Client2 | Specified feedback is not available                        | handled |


