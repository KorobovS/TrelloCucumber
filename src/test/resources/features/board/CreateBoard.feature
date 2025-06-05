Feature: Create a board

  As an authorized user
  I want to create a board
  so that I would be able to clearly plan my goals

  Background:
    Given I am registered user in the Trello app

  Rule: Create a board with default options

    Scenario: Create a board with 3 default lists on it
      When I create a board with default options
      Then A board is created
      And The response status code should be 200
      And 3 lists presented on the board

  Rule: Create a board with specific options

    Scenario: Create a board with public access
      When I create a board with "public" access
      Then A board is created
      And The response status code should be 200
      And A board has "public" access

    Scenario Outline: Create a board with custom options
      When I create a board with custom "<option>" and give "<value>"
      Then A board is created
      And The response status code should be <code>
      And The board has an custom "<option>" with a given "<value>"

      @positive
      Examples:
        | option                | value                    | code |
        | defaultLists          | false                    | 200  |
        | defaultLabels         | false                    | 200  |
        | desc                  | test description         | 200  |
        | idOrganization        | 67fe38e616fde7deef16ed49 | 200  |
        | prefs_permissionLevel | org                      | 200  |
        | prefs_permissionLevel | public                   | 200  |
        | prefs_voting          | members                  | 200  |
        | prefs_voting          | observers                | 200  |
        | prefs_comments        | members                  | 200  |
        | prefs_comments        | observers                | 200  |
        | prefs_invitations     | admins                   | 200  |
        | prefs_selfJoin        | false                    | 200  |
        | prefs_cardCovers      | false                    | 200  |
        | prefs_background      | orange                   | 200  |
        | prefs_background      | green                    | 200  |
        | prefs_background      | red                      | 200  |
        | prefs_background      | purple                   | 200  |
        | prefs_background      | pink                     | 200  |
        | prefs_background      | lime                     | 200  |
        | prefs_background      | sky                      | 200  |
        | prefs_background      | grey                     | 200  |
        | prefs_cardAging       | pirate                   | 200  |