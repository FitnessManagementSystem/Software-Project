Feature: Client Interaction
  Fitness instructors can communicate with clients and provide feedback to improve their experience.

  Scenario: Send messages to enrolled clients
    Given the instructor is managing a specific program
    When the instructor accesses the messaging feature
    Then they can send messages or respond to client queries

  Scenario: Provide feedback or progress reports
    Given the instructor is viewing a client’s progress report
    When the instructor adds feedback or updates progress metrics
    Then the system notifies the client about the updated feedback
