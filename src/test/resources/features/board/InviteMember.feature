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