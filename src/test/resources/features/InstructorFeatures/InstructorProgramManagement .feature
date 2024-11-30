Feature: Program Management
  Fitness instructors can create, update, or delete fitness programs with detailed information.

  Scenario: Create a fitness program
    Given the instructor is logged into the system
    When the instructor selects "Create Program"
    And enters details like title, duration, difficulty level, and goals
    And uploads materials such as video tutorials, images, or documents
    And sets a price and schedule for the program
    Then the system saves the program and displays a confirmation message

  Scenario: Update an existing fitness program
    Given the instructor is logged into the system and viewing their program dashboard
    When the instructor selects a program to edit
    And modifies details such as title, duration, or schedule
    And confirms the changes
    Then the system updates the program and notifies enrolled clients

  Scenario: Delete a fitness program
    Given the instructor has accessed the list of their active programs
    When the instructor selects a program to delete
    And confirms the deletion
    Then the system removes the program and notifies enrolled clients about its cancellation
