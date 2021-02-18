$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("top10product.feature");
formatter.feature({
  "line": 2,
  "name": "Finding Top 10 products from Amazon",
  "description": "",
  "id": "finding-top-10-products-from-amazon",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@product"
    }
  ]
});
formatter.before({
  "duration": 3130895700,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "Searching product",
  "description": "",
  "id": "finding-top-10-products-from-amazon;searching-product",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "User goes to \"https://www.amazon.com/\" website",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "Randomly fetch the product from the list in the excel file",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "Search the item",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "Click Sort By and choose \"Price: Low to High\"",
  "keyword": "When "
});
formatter.step({
  "line": 9,
  "name": "Take first 10 of products",
  "keyword": "And "
});
formatter.step({
  "line": 10,
  "name": "validate them including the item fetched from excel",
  "keyword": "Then "
});
formatter.step({
  "line": 11,
  "name": "print them as a pdf file",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "https://www.amazon.com/",
      "offset": 14
    }
  ],
  "location": "Product.user_goes_to_website(String)"
});
formatter.result({
  "duration": 252910952800,
  "status": "passed"
});
formatter.match({
  "location": "Product.randomly_fetch_the_product_from_the_list_in_the_excel_file()"
});
formatter.result({
  "duration": 925089900,
  "status": "passed"
});
formatter.match({
  "location": "Product.search_the_item()"
});
formatter.result({
  "duration": 37911546600,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Price: Low to High",
      "offset": 26
    }
  ],
  "location": "Product.click_Sort_By_and_choose(String)"
});
formatter.result({
  "duration": 532858100,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "10",
      "offset": 11
    }
  ],
  "location": "Product.take_first_of_products(int)"
});
formatter.result({
  "duration": 5946530600,
  "status": "passed"
});
formatter.match({
  "location": "Product.validate_them_including_the_item_fetched_from_excel()"
});
formatter.result({
  "duration": 20900,
  "status": "passed"
});
formatter.match({
  "location": "Product.print_them_as_a_pdf_file()"
});
formatter.result({
  "duration": 267899400,
  "status": "passed"
});
formatter.after({
  "duration": 1382130800,
  "status": "passed"
});
});