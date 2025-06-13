Feature: Update a List

  I, as an authorized user
  I want to be able to update the list options (fields) and the list itself
  So that the current settings correspond to the current tasks

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

  Rule: Update a list

    Scenario Outline: Update a list
      When I update a list change the "<option>" to a new "<value>"
      Then The response status code should be <code>
      And Check the "<option>" have new "<value>"

      @positive
      Examples:
        | option                        | value                     | code |
        | name                          | New name                  | 200  |
        | closed                        | true                      | 200  |
#        | idBoard    |      | 200  |
        | pos                           | 100                       | 200  |
        | subscribed                    | true                      | 200  |
        | name, closed, pos, subscribed | New name, true, 100, true | 200  |

  Rule: Update a field on a List

    Scenario Outline: Update a field on a List
      When I update a "<field>" on a list with "<value>"
      Then The response status code should be <code>
      And Check the "<field>" have new "<value>"

      @positive
      Examples:
        | field      | value    | code |
        | name       | New name | 200  |
        | pos        | 100      | 200  |
        | subscribed | true     | 200  |