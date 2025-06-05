Feature: Create a list on a board

  I, as an authorized user
  I want to be able to create a sheet on the board
  In order to add cards (tasks) for the project

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access

  Rule: Create a list on a board

    Scenario: Create a list with default options
      When I create a list with default options and "Name list"
      Then I got the created list
      And The response status code should be 200
      And The list name field matches the "Name list"

    Scenario Outline: Create a list based on an existing one and set the position
      Given I create base list
      When I create a list with "<name>" based on an existing one base list and set the position "<value>"
      Then I got the created list
      And The response status code should be <code>
      And The list name field matches the "<name>"
      And The list position matches the "<value>"
#      And The new list contains data from another list "<listId>"

      @positive
      Examples:
        | name                 | value  | code |
        | New list with top    | top    | 200  |
        | New list with bottom | bottom | 200  |
        | New list with 0.5    | 0.5    | 200  |
        | New list with 10     | 10     | 200  |