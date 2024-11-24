Feature: Admin Content Management

  Background:
    Given the admin is logged in

  Scenario Outline: Approve or reject content types shared by instructors
    When the admin reviews the submitted "<ContentType>"
    Then the admin can approve the "<ContentType>"
    And the admin can reject the "<ContentType>"

    Examples:
      | ContentType       |
      | wellness articles |
      | tips              |
      | recipes           |

  Scenario Outline: Approve articles or tips shared on health and wellness
    When the admin reviews the submitted "<ContentType>"
    Then the admin can approve the "<ContentType>"
    Examples:
      | ContentType    |
      | health article |
      | wellness tip   |

  Scenario: Handle user feedback and complaints
    When the admin reviews user feedback and complaints
    Then the admin can handle the user feedback
    And handle the complaints