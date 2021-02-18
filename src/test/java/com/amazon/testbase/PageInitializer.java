package com.amazon.testbase;

import com.amazon.pages.MainPage;
import com.amazon.pages.SearchPage;

public class PageInitializer  {


	public static MainPage mainPage;
	public static SearchPage searchPage;

	public static void initialize() {
		// initialize pages

		mainPage = new MainPage();
		searchPage=new SearchPage();
	}

}
