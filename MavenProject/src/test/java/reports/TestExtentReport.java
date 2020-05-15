package reports;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import common.Utility;

public class TestExtentReport
	{
		ExtentReports extent;
		ExtentTest logger;
		WebDriver driver;

		@BeforeSuite
		public void setup()
			{
				ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/Test_Results.html");
				extent = new ExtentReports();
				extent.attachReporter(reporter);
			}

		// This code will run before executing any test
		@BeforeTest
		public void beforeTest()
			{
				// String projectpath = System.getProperty("user.dir");
				System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver_v80.exe");
				driver = new ChromeDriver();

			}

		// Actual Test which will start the application and verify the title
		@Test
		public void loginTestPass() throws IOException
			{
				logger = extent.createTest("Login Test Pass ", "Login Test Pass Description");
				logger.log(Status.INFO, "Login test pass started ");
				driver.get("http://www.google.com");
				System.out.println("title is " + driver.getTitle());
				Assert.assertTrue(driver.getTitle().contains("Google"));

			}

		@Test
		public void loginTestFail() throws IOException
			{
				logger = extent.createTest("Login Test Pass", "Login Test Fail Description");
				logger.log(Status.INFO, "Login test Fail started ");
				driver.get("http://www.google.com");
				System.out.println("title is " + driver.getTitle());
				Assert.assertTrue(driver.getTitle().contains("James"));

			}

		@AfterMethod // when assertTrue() fails/pass it come here
		public void tearDown(ITestResult result) throws IOException
			{
				if (result.getStatus() == ITestResult.FAILURE)
					{
						String spath = Utility.getScreenshot(driver);

						logger.fail(result.getThrowable().getMessage(),
								MediaEntityBuilder.createScreenCaptureFromPath(spath).build());
						logger.log(Status.FAIL, "fail");
					}

				else // (result.getStatus() == ITestResult.SUCCESS)
					{
						String spath = Utility.getScreenshot(driver);

						logger.pass("PASSED", MediaEntityBuilder.createScreenCaptureFromPath(spath).build());
						logger.log(Status.PASS, "Passed");
					}

				// logger.addScreenCaptureFromPath("screenshot.png");

				logger.log(Status.INFO, "Login test completed ");

			}

		// This will run after testcase and it will capture screenshot and add in report
		@AfterTest
		public void afterTest()
			{
				logger.log(Status.INFO, "Login test completed ");
			}

		@AfterSuite
		public void tearDown()
			{
				driver.quit();
				extent.flush();

			}

	}
