Feature: Content Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Handle user feedback and complaints
    When I navigate to the Content Management section
    And I handle feedback from "<Instructor>" to "<Client>"
    Then I should set status to "<Status>" with the success message "<SuccessMessage>"

    Examples:
      | Instructor  | Client  | Status  | SuccessMessage                                             |

      | Instructor1 | Client1 | handled | Feedback from Instructor1 to Client1 handled successfully! |

      | Instructor2 | Client2 | handled | Feedback from Instructor2 to Client2 handled successfully! |