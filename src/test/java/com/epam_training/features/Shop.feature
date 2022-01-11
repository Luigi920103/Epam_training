Feature: Shop

  Background:
    Given the user navigates to the base page
    And the user selects Shop button


  @RegularTest @Shop
  Scenario: S001 read-more stock Functionality
    When the user filter by price between 150 to 450
    Then the products show it a price in that range

  @RegularTest @Shop
  Scenario: S008 - S002 read-more stock Functionality
    When the user opens the read more on each product
    Then it has to have the add button according to its stock
    #And just have to see the product selected

  @RegularTest @Shop
  Scenario Outline: S003 ---------"<order>"
    When the user order the results by "<order>"
    Then the user has to see the results order by "<order>"
    Examples:
      | order                      |
      | Default sorting            |
      | Sort by popularity         |
      | Sort by average rating     |
      | Sort by newness            |
      | Sort by price: low to high |
      | Sort by price: high to low |


  @RegularTest @Shop
  Scenario: S009 Shop-Sale Functionality
    Then the user can view the current price
    And the user can view old price stricken for the sale written products

  @RegularTest @Shop
  Scenario: S010 Basket Functionality
    When the user selects "5" books
    Then the user can see the same number of books on the basket
    And the user can see the total of the basket

  @ExploratoryTest @Shop
  Scenario: S010 Basket Functionality - random selection
    When the user selects "12" random books
    Then the user can see the same number of books on the basket
    And the user can see the total of the basket

  @RegularTest @Shop
  Scenario: S010 Basket Functionality total always > subtotal
    And the user selects "5" books on the catalog
    When the user goes to the basket
    Then the total price is higher than subtotal

  @RegularTest @Shop
  Scenario: S010 checkout page view Billing Details,Order Details,Additional details and Payment gateway details.
    And the user selects "5" books on the catalog
    When the user goes to the basket
    And the user proceeds to checkout
    Then the user should view Billing Details,Order Details,Additional details and Payment gateway details.

  @RegularTest @Shop
  Scenario: S011 Order confirmation page validation of order details,bank details,customer details and billing details
    And the user selects "1" books on the catalog
    And the user goes to the basket and proceeds to checkout
    And the user fills his details on the page
    When the user place the order
    Then the user should see the order details, bank details,customer details and billing details

  @RegularTest @Shop
  Scenario Outline: S011 Basket Functionality tax functionality "<tax>"
    And the user selects "5" books on the catalog
    When the user goes to the basket
    Then the "<tax>" should be correct
    Examples:
      | tax    |
      | abroad |



