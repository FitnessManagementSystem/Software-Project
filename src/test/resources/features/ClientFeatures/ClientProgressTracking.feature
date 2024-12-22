Feature: Progress Tracking

  Background:
    Given I am on the Progress Tracking page

  Scenario Outline: View achievements or badges for completing programs
    Then I should see a list of the programs I have completed, with the badge "<name>" and message "<expectedMessage>"

    Examples:
      | name        | expectedMessage                                |
      | Yoga        | Programs found successfully                    |
      | aaaa        | No completed programs match the specified name |
      | Muscle Gain | User isn't enrolled in this program            |
      | Boxing      | program is still active                        |
