Feature: Get a board with resources

  I, as an authorized user
  I want to access the board resources
  In order to track and control current resources

  Background:
    Given I am registered user in the Trello app
    And I create a board with default options

  Rule: Get all available resources on the board

    Scenario: I get everything that is on the board
      When I get resources on a board
      Then I got resource boards
      And The response status code should be 200

  Rule: Get a specific resource of the board

    Scenario: I get lists on a board
      When I get lists on a board
      Then I got the resources by lists
      And The response status code should be 200

    Scenario: I get members on a board
      When I get members on a board
      Then I got the resources by members
      And The response status code should be 200

    Scenario: I get cards on a board
      When I get cards on a board
      Then I got the resources by cards
      And The response status code should be 200

    Scenario: I get labels on a board
      When I get labels on a board
      Then I got the resources by labels
      And The response status code should be 200
