package com.amazon.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.testbase.BaseClass;

public class MainPage {

	@FindBy(id = "twotabsearchtextbox")
	public WebElement searchArea;

	public MainPage() {
		PageFactory.initElements(BaseClass.getDriver(), this);
	}

}
