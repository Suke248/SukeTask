@UI @REGRESSION
Feature: UI Feature to check out products

  Scenario Outline: Checkout products
    When I Login in to the site using user "<user>" and password "<password>"
    And I sort the products as "Name (A to Z)"
    And I sort the products as "Name (Z to A)"
    And I sort the products as "Price (low to high)"
    And I sort the products as "Price (high to low)"
    And I add "3" items to the cart
    And I visit shopping cart
    Then I verify the selected items are added in the cart
    When I remove "1" item
    And I continue shopping
    And I add "1" items to the cart
    And I visit shopping cart
    Then I verify the selected items are added in the cart
    When I click checkout button
    And I enter checkout information
    And I click continue button
    Then checkout page is displayed
    And I verify total price
    When I finish checkout
    Then order confirmation message is displayed

    Examples:
      | user                    | password     |
      | standard_user           | secret_sauce |
#      | performance_glitch_user | secret_sauce |
#      | locked_out_user         | secret_sauce |
#      | problem_user            | secret_sauce |
