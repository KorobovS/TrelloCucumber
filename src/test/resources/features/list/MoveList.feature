Feature: Move List to Board

  I, as an authorized user
  I want to be able to move a sheet to another board
  In order to use it in a new project

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access
    And I create a list with default options and "Name list"

    Rule: Move list from one board to another

        Scenario: Move list from one board to another
          When I create second board with "public" access
          And I move created list on the second board
          Then The response status code should be 200
          And List moved on the second board
          And Delete second board