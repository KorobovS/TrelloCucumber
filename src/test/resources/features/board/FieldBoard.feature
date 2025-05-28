Feature: Work with field board

  As an authorized user
  I want to be able to receive information and edit current board settings so that
  I can track and make timely decisions about updating them in accordance with my current plans

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access

  Rule: Get a specific field (option) on a board

    Scenario Outline: Get a field on a Board
      When I am requesting "<field>" data
      Then I got "<field>" with "<value>"
      And The response status code should be <code>

      @positive
      Examples:
        | field            | code |
        | name             | 200  |
        | dateLastActivity | 200  |
        | desc             | 200  |
        | url              | 200  |

  Rule: Update a specific field (option) of the board

    Scenario Outline: Update the field on board
      When I update "<field>" to the new "<value>" on the board
      And I am requesting "<field>" data
      Then I got "<field>" with "<value>"
      And The response status code should be <code>

      @positive
      Examples:
        | field            | value          | code |
        | name             | New Board name | 200  |
        | desc             | New desc       | 200  |
        | prefs/background | red            | 200  |