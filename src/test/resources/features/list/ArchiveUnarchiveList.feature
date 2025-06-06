Feature: Archive or unarchive a list

  I, as an authorized user
  I want to be able to archive or unarchive any sheet on the board
  In order to be able to remove temporarily unnecessary sheet/sheets without deleting them and, if necessary, return them to the project

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access
    And I create a list with default options and "Name list"

  Rule: Archive list

    Scenario: Archive the list from the board
      When I archive the list from the board
      Then The response status code should be 200
      And Check archive status "true"

  Rule: Unarchive a list

    Scenario: Unarchive the list from the board
      Given I archive the list from the board
      When I unarchive the list from the board
      Then The response status code should be 200
      And Check archive status "false"
