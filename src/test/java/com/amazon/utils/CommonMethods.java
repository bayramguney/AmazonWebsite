package com.amazon.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.amazon.testbase.BaseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.testbase.PageInitializer;

public class CommonMethods extends PageInitializer {

	/**
	 * This method clears the textbox and sends another text
	 * 
	 * @param element
	 * @param text
	 */
	public static void sendText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * This method checks if radio/checkbox is enabled and then clicks on the
	 * element that has the value we want
	 * 
	 * 
	 * @param listElement
	 * @param value
	 */
	public static void clickRadioOrCheckbox(List<WebElement> listElement, String value) {
		String actualValue;

		for (WebElement el : listElement) {
			actualValue = el.getAttribute("value").trim();
			if (el.isEnabled() && actualValue.equals(value)) {
				el.click();
				break;
			}
		}
	}

	/**
	 * This method checks if the text is found in the dropdown element and only then
	 * it selects it
	 * 
	 * @param element
	 * @param textToSelect
	 */
	public static void selectDropdown(WebElement element, String textToSelect) {
		try {
			Select select = new Select(element);

			List<WebElement> options = select.getOptions();

			for (WebElement el : options) {
				if (el.getText().equals(textToSelect)) {
					select.selectByVisibleText(textToSelect);
					break;
				}
			}
		} catch (UnexpectedTagNameException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method checks if the index is valid and only then selects it
	 * 
	 * @param element
	 * @param index
	 */
	public static void selectDropdown(WebElement element, int index) {

		try {
			Select select = new Select(element);

			int size = select.getOptions().size();

			if (size > index) {
				select.selectByIndex(index);
			}
		} catch (UnexpectedTagNameException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method accepts alerts and catches exception if alert in not present
	 * 
	 */
	public static void acceptAlert() {
		try {
			Alert alert = BaseClass.getDriver().switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will dismiss the alert after checking if alert is present
	 * 
	 */
	public static void dismissAlert() {
		try {
			Alert alert = BaseClass.getDriver().switchTo().alert();
			alert.dismiss();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns the alert text. If no alert is present exception is
	 * caught and null is returned.
	 * 
	 * @return
	 */
	public static String getAlertText() {
		String alertText = null;

		try {
			Alert alert = BaseClass.getDriver().switchTo().alert();
			alertText = alert.getText();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}

		return alertText;
	}

	/**
	 * This method send text to the alert. NoAlertPresentException is handled.
	 * 
	 * @param text
	 */
	public static void sendAlertText(String text) {
		try {
			Alert alert =BaseClass.getDriver().switchTo().alert();
			alert.sendKeys(text);
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method switches to a frame by using name or id
	 * 
	 * @param nameOrId
	 */
	public static void switchToFrame(String nameOrId) {
		try {
			BaseClass.getDriver().switchTo().frame(nameOrId);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method switches to a frame by using an index
	 * 
	 * @param index
	 */
	public static void switchToFrame(int index) {
		try {
			BaseClass.getDriver().switchTo().frame(index);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method switches to a frame by using a WebElement
	 * 
	 * @param element
	 */
	public static void switchToFrame(WebElement element) {
		try {
			BaseClass.getDriver().switchTo().frame(element);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method switches focus to a child window
	 * 
	 */
	public static void switchToChildWindow() {
		String mainWindow = BaseClass.getDriver().getWindowHandle();
		Set<String> windows = BaseClass.getDriver().getWindowHandles();

		for (String window : windows) {
			if (!window.equals(mainWindow)) {
				BaseClass.getDriver().switchTo().window(window);
			}
		}

	}

	/**
	 * 
	 * This method creates a WebDriverWait object and returns it
	 * 
	 * @return
	 */
	public static WebDriverWait getWaitObject() {
		WebDriverWait wait = new WebDriverWait(BaseClass.getDriver(), Constants.EXPLICIT_WAIT_TIME);

		return wait;
	}

	/**
	 * This method waits for an item to be clickable
	 * 
	 * @param element
	 * @return
	 */
	public static WebElement waitForClickability(WebElement element) {
		return getWaitObject().until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * This method waits for an element to be visible
	 * 
	 * @param element
	 * @return
	 */
	public static WebElement waitForVisibility(WebElement element) {
		return getWaitObject().until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * This method click in an element and has wait implemented on it
	 * 
	 * @param element
	 */
	public static void click(WebElement element) {
		waitForClickability(element);
		element.click();
	}

	public static void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This methods casts the driver to a JavascriptExecutor and returns it
	 * 
	 * @return
	 */
	public static JavascriptExecutor getJSObject() {
		JavascriptExecutor js = (JavascriptExecutor) BaseClass.getDriver();

		return js;
	}

	/**
	 * This method will click in the element passed to it using JavascriptExecutor
	 * 
	 * @param element
	 */
	public static void jsClick(WebElement element) {
		getJSObject().executeScript("arguments[0].click()", element);
	}

	/**
	 * This method will scroll the page until the element passed to it becomes
	 * visible
	 * 
	 * @param element
	 */
	public static void scrollToElement(WebElement element) {
		getJSObject().executeScript("arguments[0].scrollIntoView(true)", element);
	}

	/**
	 * This method will scroll the page down based on the passed pixel parameter
	 * 
	 * @param pixel
	 */
	public static void scrollDown(int pixel) {
		getJSObject().executeScript("window.scrollBy(0," + pixel + ")");
	}

	/**
	 * This method will scroll the page up based on the passed pixel parameter
	 * 
	 * @param pixel
	 */
	public static void scrollUp(int pixel) {
		getJSObject().executeScript("window.scrollBy(0,-" + pixel + ")");
	}

	/**
	 * This method will select a date from the calendar
	 * 
	 * @param elements
	 * @param text
	 */
	public static void selectCalendarDate(List<WebElement> elements, String text) {
		for (WebElement day : elements) {
			if (day.isEnabled()) {
				if (day.getText().equals(text)) {
					day.click();
					break;
				}
			}
		}
	}

	public static byte[] takeScreenshot(String filename) {
		TakesScreenshot ts = (TakesScreenshot) BaseClass.getDriver();

		byte[] picBytes = ts.getScreenshotAs(OutputType.BYTES);

		File file = ts.getScreenshotAs(OutputType.FILE);
		// create destination as : filepath + filename + timestamp + .png
		String destination = Constants.SCREENSHOT_FILEPATH + filename + getTimeStamp() + ".png";

		try {
			FileUtils.copyFile(file, new File(destination));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return picBytes;
	}

	/**
	 * Method to return the current time stamp in a String
	 * 
	 * @return
	 */
	public static String getTimeStamp() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		return sdf.format(date.getTime());

	}

}