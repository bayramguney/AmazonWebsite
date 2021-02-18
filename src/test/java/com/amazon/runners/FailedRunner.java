package com.amazon.runners;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
		features = "@target/failed.txt",
		glue = "com.amazon.steps",
		plugin = {"pretty"}
		)
public class FailedRunner extends AbstractTestNGCucumberTests {


}

