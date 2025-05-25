Feature: Create a board

  As an authorized user
  I want to create a board
  so that I would be able to clearly plan my goals

  Background:
    Given I am registered user in the Trello app
    Then The response status code should be 200

  Rule: Create a board with default options

    Scenario: Create a board with 3 default lists on it
      When I create a board with default options
      Then A board is created
      And 3 lists presented on the board

  Rule: Create a board with specific options

    Scenario: Create a board with public access
      When I create a board with "public" access
      Then A board is created
      And A board has "public" access

    Scenario Outline: Create a board with custom options
      When I create a board with custom "<option>" and give "<value>"
      Then A board is created
      And The board has an custom "<option>" with a given "<value>"

      @positive
      Examples:
        | option                | value                    |
        | desc                  | test description         |
        | idOrganization        | 67fe38e616fde7deef16ed49 |
        | prefs_permissionLevel | org                      |
        | prefs_permissionLevel | public                   |
        | prefs_voting          | members                  |
        | prefs_voting          | observers                |
        | prefs_comments        | members                  |
        | prefs_comments        | observers                |
        | prefs_invitations     | admins                   |
        | prefs_background      | orange                   |
        | prefs_background      | green                    |
        | prefs_background      | red                      |
        | prefs_background      | purple                   |
        | prefs_background      | pink                     |
        | prefs_background      | lime                     |
        | prefs_background      | sky                      |
        | prefs_background      | grey                     |
        | prefs_cardAging       | pirate                   |

#    Scenario: Create a board without default lists
#      When I create a board without default lists
#      Then A board is created
#      And A board doesn't have 3 default lists
#
#    Scenario: Create a board with description
#      When I create a board with description
#      Then A board is created
#      And Description is presented on the board
#
#    Scenario: Create a board with red colored background
#      When I create a board with red colored background option
#      Then A board is created
#      And The color of background is red