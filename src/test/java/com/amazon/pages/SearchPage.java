package com.amazon.pages;

import com.amazon.testbase.BaseClass;
import com.amazon.utils.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import java.util.LinkedList;
import java.util.List;

public class SearchPage {
	//div[@data-component-type="s-search-result"]
	@FindBy(id = "s-result-sort-select")
	public WebElement sortBy;

	@FindBy(xpath = "//div[@data-component-type='s-search-result']//div/h2")
	public  static List<WebElement> productList ;


	public SearchPage() {
		PageFactory.initElements(BaseClass.getDriver(), this);
	}

	public static void selectDropdownAndClick(WebElement element, String textToSelect) {
		try {
			Select select = new Select(element);

			List<WebElement> options = select.getOptions();

			for (WebElement el : options) {
				if (el.getText().equals(textToSelect)) {
					el.click();
					break;
				}
			}
		} catch (UnexpectedTagNameException e) {
			e.printStackTrace();
		}

	}

	public  static List<String> getTheTenProducts() {
		List<String>list1=new LinkedList<>();
		List<String>list=new LinkedList<>();
		CommonMethods.wait(5);
		for (WebElement each:productList) {
			list1.add(each.getText());
		}
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i <10 ; i++) {
			list.add(list1.get(i));
		}


		return list;
	}

}
