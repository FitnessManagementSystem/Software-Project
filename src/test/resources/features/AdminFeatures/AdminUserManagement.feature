Feature: Admin User Management
  Admins can manage user accounts, approve registrations, and monitor engagement

  Background:
    Given  the admin is logged in

  Scenario: Add, update, and deactivate accounts
    When the admin accesses the user management panel
    Then the admin can add accounts for instructors and clients
    And update accounts for instructors and clients
    And deactivate accounts for instructors and clients

  Scenario: Approve new instructor registration
    When the admin views pending instructor registration
    Then the admin can approve new instructor registration

  Scenario: Monitor user activity and engagement statistics
    When the admin the user activity dashboard
    Then the admin can monitor user activity
    And Monitor engagement statistics
