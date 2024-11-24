Feature: Admin Subscription Management
  Admins can manage subscription plans for clients and instructors

  Background:
    Given the admin is logged in

  Scenario: Manage subscription plans for clients and instructors
    When the admin accesses the subscription management page
    Then the admin can view the existing subscription plans
    And add new subscription plans
    And update existing subscription plans
    And remove existing subscription plans
    And set plans such as Basic and Premium
