@product
Feature: Finding Top 10 products from Amazon

  Scenario: Searching product
    Given User goes to "https://www.amazon.com/" website
    And Randomly fetch the product from the list in the excel file
    And Search the item
    When Click Sort By and choose "Price: Low to High"
    And Take first 10 of products
    Then validate them including the item fetched from excel
    Then print them as a pdf file