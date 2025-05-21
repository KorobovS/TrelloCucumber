Feature: Delete a board

  I, as an authorized user
  I want to be able to delete a board
  In order to complete work on the project in a timely manner

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Removing a board

    Scenario: I delete a board
      When I delete a board
      Then The board is removed