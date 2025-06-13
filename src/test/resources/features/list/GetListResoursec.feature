Feature: Get resoursec of the list

  I, as an authorized user
  I want to be able to access the list and its resources
  In order to track and control the compliance of resources with current tasks

  Background:
    Given I am registered user in the Trello app
    And I create a board with "public" access
    And I create a list with default options and "Name list"

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