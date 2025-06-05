Feature: Invite member

  I, as an authorized user
  I want to be able to invite a user to the board
  In order to collaborate on a project

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access

  Rule: Invite member to a board via Email

    Scenario: I invite a user
      When I send an invitation to email
      Then Invitation sent by email
      And The response status code should be 200

    Scenario: I invite a member with custom name
      When I send an invitation to email with "Invited_user"
      And The response status code should be 200

    Scenario: I invite a member with option
      When Invitation sent by email with "type" and "admin"
      And The response status code should be 200