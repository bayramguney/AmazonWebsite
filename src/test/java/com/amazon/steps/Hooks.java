package com.amazon.steps;

import com.amazon.testbase.BaseClass;
import com.amazon.utils.CommonMethods;
import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;


public class Hooks  {

	@Before
	public void start() {
		BaseClass.getDriver();
		BaseClass.setUp();
	}

	@After // After each scenario
	public void after(Scenario scenario) throws IOException {
		System.out.println("After scenario: " + scenario.getName());
		if (scenario.isFailed()) {
			File sourcePath = ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.FILE);
			File destinationPath = new File(System.getProperty("user.dir") + "/target/extent_report/screenshots/failed/" + scenario.getName()+CommonMethods.getTimeStamp() + ".png");
			copyFile(sourcePath, destinationPath);
			Reporter.addScreenCaptureFromPath("./screenshots/failed/" + scenario.getName()+CommonMethods.getTimeStamp() + ".png");
		}else{
			File sourcePath = ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.FILE);
			File destinationPath = new File(System.getProperty("user.dir") + "/target/extent_report/screenshots/passed/" + scenario.getName()+CommonMethods.getTimeStamp() + ".png");
			copyFile(sourcePath, destinationPath);
			Reporter.addScreenCaptureFromPath("./screenshots/passed/" + scenario.getName()+CommonMethods.getTimeStamp() + ".png");

		}
		BaseClass.tearDown();
	}

}
