package com.amazon.testbase;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.amazon.utils.ConfigsReader;
import com.amazon.utils.Constants;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BaseClass {

	private static ThreadLocal<WebDriver> DriverPool = new ThreadLocal<>();

	private BaseClass() {
	}

	public static WebDriver getDriver() {
		//if this thread doesn't have a web Driver yet - create it and add to pool
		ConfigsReader.readProperties(Constants.CONFIGURATION_FILEPATH);
		if (DriverPool.get() == null) {
			System.out.println("TRYING TO CREATE Driver");
			// this line will tell which browser should open based on the value from properties file
			String browserParamFromEnv = System.getProperty("browser");
			String browser = browserParamFromEnv == null ? ConfigsReader.getProperty("browser") : browserParamFromEnv;
			switch (browser) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					DriverPool.set(new ChromeDriver());
					break;
				case "chrome_headless":
					WebDriverManager.chromedriver().setup();
					DriverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					DriverPool.set(new FirefoxDriver());
					break;
				case "firefox_headless":
					WebDriverManager.firefoxdriver().setup();
					DriverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
					break;
				case "ie":
					if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
						throw new WebDriverException("Your OS doesn't support Internet Explorer");
					}
					WebDriverManager.iedriver().setup();
					DriverPool.set(new InternetExplorerDriver());
					break;
				case "edge":
					if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
						throw new WebDriverException("Your OS doesn't support Edge");
					}
					WebDriverManager.edgedriver().setup();
					DriverPool.set(new EdgeDriver());
					break;
				case "safari":
					if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
						throw new WebDriverException("Your OS doesn't support Safari");
					}
					WebDriverManager.getInstance(SafariDriver.class).setup();
					DriverPool.set(new SafariDriver());
					break;
				case "remote_chrome":
					try {
						ChromeOptions chromeOptions = new ChromeOptions();
						chromeOptions.setCapability("platform", Platform.ANY);
						DriverPool.set(new RemoteWebDriver(new URL("http://ec2-3-95-173-125.compute-1.amazonaws.com:4444//wd/hub"), chromeOptions));
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "remote_firefox":
					try {
						FirefoxOptions firefoxOptions = new FirefoxOptions();
						firefoxOptions.setCapability("platform", Platform.ANY);
						DriverPool.set(new RemoteWebDriver(new URL("http://ec2-54-152-156-255.compute-1.amazonaws.com:4444//wd/hub"), firefoxOptions));
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
			}
		}


		//return corresponded to thread id webDriver object
		return DriverPool.get();
	}

	public static void tearDown() {
		DriverPool.get().quit();
		DriverPool.remove();

	}
	public static void setUp(){
		getDriver().manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();

		// we initialize all the page elements of the classes in package com.neotech.pages
		PageInitializer.initialize();
	}
}