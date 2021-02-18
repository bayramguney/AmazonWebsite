package com.amazon.steps;


import com.amazon.pages.SearchPage;
import com.amazon.testbase.BaseClass;
import com.amazon.utils.CommonMethods;
import com.amazon.utils.ConfigsReader;
import com.amazon.utils.ExcelUtilities;
import com.amazon.utils.pdfUtil;
import cucumber.api.java.en.*;
import org.openqa.selenium.Keys;


import java.util.List;
import java.util.Random;

public class Product extends CommonMethods {

    @Given("^User goes to \"([^\"]*)\" website$")
    public void user_goes_to_website(String url)  {
        BaseClass.getDriver().get(url);


    }
    String product;
    @Given("^Randomly fetch the product from the list in the excel file$")
    public void randomly_fetch_the_product_from_the_list_in_the_excel_file() {
        ExcelUtilities excelUtilities=new ExcelUtilities(ConfigsReader.getProperty("pathexcel"),"product");
        Random randomNumber=new Random();
        int rand=randomNumber.nextInt(excelUtilities.rowCount()-1)+1;
        product=excelUtilities.getCellData(rand,0);
    }

    @Given("^Search the item$")
    public void search_the_item() {
    mainPage.searchArea.sendKeys(product, Keys.ENTER);
    }

    @When("^Click Sort By and choose \"([^\"]*)\"$")
    public void click_Sort_By_and_choose(String select) {
        SearchPage.selectDropdownAndClick(searchPage.sortBy, select);
    }
    List<String> list;
    @When("^Take first (\\d+) of products$")
    public void take_first_of_products(int arg1) {
        list=SearchPage.getTheTenProducts();
        System.out.println(list);

    }

    @Then("^validate them including the item fetched from excel$")
    public void validate_them_including_the_item_fetched_from_excel() {}

    @Then("^print them as a pdf file$")
    public void print_them_as_a_pdf_file() {

        pdfUtil.createPdfOneColumn("Low10Amazon.pdf","AMAZON PRODUCTS FOR LOW 10",10,"amazon.jpg",list,"Description");
    }
}
