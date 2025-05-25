Feature: Invite member

  I, as an authorized user
  I want to be able to invite a user to the board
  In order to collaborate on a project

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Invite member to a board via Email

    Scenario: I invite a user
      When I send an invitation to email
      Then Invitation sent by email
      And The response status code should be 200

    Scenario Outline: I invite a user with options
      When I send an invitation to email with "<option>" and "<value>"
      Then Invitation sent by email with "<option>" and "<value>"
      And The response status code should be <code>

      @positive
      Examples:
        | option   | value        | code |
        | type     | admin        | 200  |
        | type     | observer     | 200  |
        | fullName | Invited_user | 200  |