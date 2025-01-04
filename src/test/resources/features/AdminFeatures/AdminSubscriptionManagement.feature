Feature: Admin Subscription Management

  Background:
    Given I am logged in as an Admin user

  Scenario Outline: Manage subscription plans for instructors and clients
    Given I navigate to the Subscription management section
    And I have selected the "<planType>" subscription plan for "<userType>"
    When I "<action>" the plan
    Then The "<planType>" subscription plan for "<userType>" should be marked as "<status>"
    And I should see a success message: "<successMessage>"

    Examples:
      | userType    | planType | action     | status                                                    | successMessage                                                                   |
      | Soso        | Aa       | activate   | Plan not found for the specified user type and plan type. | Plan not found for the specified user type.                                      |
      | Client1     | Basic    | activate   | active                                                    | The Basic subscription plan for Client1 has been activated successfully.         |
      | Client2     | Premium  | activate   | active                                                    | The Premium subscription plan for Client2 has been activated successfully.       |
      | Instructor1 | Basic    | activate   | active                                                    | The Basic subscription plan for Instructor1 has been activated successfully.     |
      | Instructor2 | Premium  | activate   | active                                                    | The Premium subscription plan for Instructor2 has been activated successfully.   |
      | Client1     | Basic    | deactivate | inactive                                                  | The Basic subscription plan for Client1 has been deactivated successfully.       |
      | Client2     | Premium  | deactivate | inactive                                                  | The Premium subscription plan for Client2 has been deactivated successfully.     |
      | Instructor1 | Basic    | deactivate | inactive                                                  | The Basic subscription plan for Instructor1 has been deactivated successfully.   |
      | Instructor2 | Premium  | deactivate | inactive                                                  | The Premium subscription plan for Instructor2 has been deactivated successfully. |
      | SSSS        | adsad    | deactivate | Plan not found for the specified user type and plan type. | Plan not found for the specified user type.                                      |
