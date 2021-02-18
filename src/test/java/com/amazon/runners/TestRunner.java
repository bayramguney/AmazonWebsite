package com.amazon.runners;


import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;



import org.testng.annotations.AfterClass;

@CucumberOptions(
			// you can specify which feature you want to run, we can also run all features
			features = "src/test/resources/features/",
			
			// shows where we can find the implementation regarding the steps in feature files above
			glue = "com.amazon.steps",
			
			// if true, it does not really run the steps. it just check if every step in feature files is defined in code
			dryRun = false,
			
			// tags is similar to groups in TestNG
			tags = "@product",
			
			monochrome = true,
			plugin = {
					"rerun:target/failed.txt",
					"pretty", //prints gherkin steps in console
					"com.cucumber.listener.ExtentCucumberFormatter:target/extent_report/index.html" ,// extent report plugin
					"html:target/cucumber-default-report", //create a basic html report in target folder
					"json:target/cucumber.json"

			}
		)
public class TestRunner extends AbstractTestNGCucumberTests {

	@AfterClass
	public void configReport(){
		Reporter.loadXMLConfig("src/test/java/com/amazon/runners/testngxmls/report.xml");
		Reporter.setSystemInfo("Author", "Bayram Guney");
		Reporter.setSystemInfo("Application name", "Amazon Website");
		Reporter.setSystemInfo("Operating System", System.getProperty("os.name"));
		Reporter.setSystemInfo("Environment", "test");
	}

}
