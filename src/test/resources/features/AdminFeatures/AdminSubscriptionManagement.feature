Feature: Admin Subscription Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Manage subscription plans for instructors and clients
    When I navigate to the Subscription Management section
    And I have selected the "<planType>" subscription plan for "<userType>"
    When I "<action>" the plan
    Then The "<planType>" subscription plan for "<userType>" should be marked as "<status>"
    And I should see a success message: "<successMessage>"

    Examples:
      | userType    | planType | action     | status   | successMessage                                                                   |
      | Client1     | Basic    | activate   | active   | The Basic subscription plan for Client1 has been active successfully.         |
      | Client2     | Premium  | activate   | active   | The Premium subscription plan for Client2 has been active successfully.      |
      | Instructor1 | Basic    | activate   | active   |The Basic subscription plan for Instructor1 has been active successfully.|
      | Instructor2 | Premium  | activate   | active   | The Premium subscription plan for Instructor2 has been active successfully.   |
      | Client1     | Basic    | deactivate | inactive | The Basic subscription plan for Client1 has been inactive successfully.     |
      | Client2     | Premium  | deactivate | inactive | The Premium subscription plan for Client2 has been inactive successfully.    |
      | Instructor1 | Basic    | deactivate | inactive | The Basic subscription plan for Instructor1 has been inactive successfully.  |
      | Instructor2 | Premium  | deactivate | inactive | The Premium subscription plan for Instructor2 has been inactive successfully. |

