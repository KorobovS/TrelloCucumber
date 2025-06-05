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

  Rule: Get label data

    Scenario: I get all label
      When I get labels on a board
      Then I got the resources by labels
      And The response status code should be 200

    Scenario Outline: I get label with custom parameter
      Given I create a label with a "<name>" and a "<color>" on the board
      When I get labels on a board with "<parameter>" = "<value>"
      Then I got label with "<parameter>" = "<value>"
      And The response status code should be <code>

      @positive
      Examples:
        | name  | color | parameter | value | code |
        | name1 | green | name      | name1 | 200  |
        | name2 | red   | color     | red   | 200  |

  Rule: Update label data

    Scenario Outline: I update label with optional parameter
      Given I create a label with a "<name>" and a "<color>" on the board
      When I update label name "<name>" and color "<color>"
      Then I got label with "name" = "<name>"
      And I got label with "color" = "<color>"
      And The response status code should be <code>

      @positive
      Examples:
        | name  | color | code |
        | name1 | green | 200  |
        | name2 |       | 200  |

  Rule: Update label data with field

    Scenario Outline: I update label with field
      Given I create a label with a "Name" and a "green" on the board
      When I update label "<parameter>" with "<value>"
      Then I got label with "<parameter>" = "<value>"
      And The response status code should be <code>

      @positive
      Examples:
        | parameter | value    | code |
        | name      | New name | 200  |
        | color     | pink     | 200  |

  Rule: Delete label

    Scenario: I delete label on the board
      Given I create a label with a "Name" and a "green" on the board
      When I delete label
      Then The response status code should be 200
      And I get deleted label