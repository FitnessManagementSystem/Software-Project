Feature: Client Interaction

  Background:
    Given the instructor accesses the client interaction page

  Scenario Outline: Send a report to the client
    When the instructor sends a report "<report>"  from "<Instructor>"to the "<client>"
    Then I should see a report "<successMessage>"

    Examples:
      | report         | successMessage           |
      | you doing good | Report sent successfully |
      |                | Report is empty          |

  Scenario Outline: Send a message to the client
    When the instructor sends a message "<message>"  from "<Instructor>"to the "<client>"
    Then I should see a message  "<successMessage>"

    Examples:
      | message        | successMessage            |
      | you doing good | Message sent successfully |
      |                | Message is empty          |

  Scenario Outline: Send feedback to the client
    When the instructor sends a feedback "<feedback>"  from "<Instructor>"to the "<client>"
    Then I should see a feedback "<successMessage>"

    Examples:
      | feedback       | successMessage             |
      | you doing good | Feedback sent successfully |
      |                | Feedback is empty          |
      | nice progress  | Feedback sent successfully |