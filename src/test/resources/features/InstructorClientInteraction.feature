Feature: Client Interaction
  Fitness instructors can communicate with clients and provide feedback to improve their experience.

  Background:
    Given the instructor is logged into the system


  Scenario Outline: Communicate with clients
    When the instructor accesses client interaction page
    Then the instructor can communicate with clients via "<Method>"
    And provide feedback to clients
    And provide progress reports to clients

    Examples:
      | Method            |
      | messaging         |
      | discussion forums |


