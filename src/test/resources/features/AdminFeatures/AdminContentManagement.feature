Feature: Admin Content Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Approve or reject content
    When I navigate to the Content Management section
    And I "<Approval Action>" a "<Content Type>"
    Then the content should be "<Status>"

    Examples:
      | Approval Action | Content Type     | Status   |
      | approve         | wellness article | approved |
      | approve         | health tip       | approved |
      | approve         | recipe           | approved |
      | reject          | wellness article | rejected |
      | reject          | health tip       | rejected |
      | reject          | recipe           | rejected |

  Scenario: Handle user feedback and complaints
    When I navigate to the Content Management section
    And I handle user complaints
    Then I should see the complaints are marked as resolved
