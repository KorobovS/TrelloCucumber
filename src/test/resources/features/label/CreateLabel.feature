Feature: Create a label

  I, as an authorized user
  I want to be able to interact with the label
  In order to leave the label on my cards

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access

  Rule: Create label with other parameters on a board

    Scenario Outline: I create a label with a custom name and color on the board
      When I create a label with a "<name>" and a "<color>" on the board
      Then I got the resources by labels
      And The response status code should be <code>
      And I got a label with the specified "<name>" and "<color>"

      @positive
      Examples:
        | name        | color  | code |
        | name yellow | yellow | 200  |
        | name purple | purple | 200  |