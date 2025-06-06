Feature: Get resoursec of the list

  I, as an authorized user
  I want to be able to access the sheet and its resources
  In order to track and control the compliance of resources with current tasks

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access
    And I create a list with default options and "Name list"

  Rule: Get a list

    Scenario: Get a list with default options
      When I get list
      Then I got the resources
      And The response status code should be 200
      And The list name field matches the "Name list"

    Scenario Outline: Get a list with field
      When I get list with "<fields>"
      Then I got the resources
      And The response status code should be <code>
      And The list name field matches the "Name list"
      And The list contains only these "<fields>"

      @positive
      Examples:
        | fields                  | code |
        | all                     | 200  |
        | name                    | 200  |
        | name,closed             | 200  |
        | name,idBoard            | 200  |
        | name,pos                | 200  |
        | name,closed,idBoard,pos | 200  |

  Rule: Get resources of the list

    Scenario: Get actions for a list
      When I get actions for a list
      Then I got the resources
      And The response status code should be 200

    Scenario: Get the board a list is on
      When I get the board a list is on
      Then I got the resources
      And The response status code should be 200

    Scenario: Get cards in a list
      When I get cards in a list
      Then I got the resources
      And The response status code should be 200