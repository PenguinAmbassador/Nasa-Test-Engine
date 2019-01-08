package main.java.nasaTestSuite;

import static org.junit.Assert.fail;

import org.openqa.selenium.WebElement;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.PointOption;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.XPath;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.String;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import javax.swing.JOptionPane;
import javax.xml.xpath.XPathConstants;

import io.appium.java_client.android.AndroidDriver;

public class TestFunctions 
{
	public final int OPEN_WAIT = 120;
	public final int UPDATE_WAIT = 240;
	public final int POWER_SECS = 20;
	public final int TOGGLE_SECS = 2000;//ms
	public final int BUTTON_WAIT = 20;
	public final int SIGN_IN_WAIT = 120;
	
	int oneMinute = 60;
	
	AndroidDriver driver;
	FrigiDriver d;
	Appliance app;
	Stromboli strombo;
	
	//I think I might wanna just put all this stuff in "Appliance" or more specific appliances.
	//TODO fix this so that the FrigiDriver methods are established in either TestFunctions or in FrigiDriver
	public TestFunctions(FrigiDriver frigiDriver, Appliance app){
		this.app = app;
		this.d = frigiDriver;
		this.strombo = new Stromboli(frigiDriver);
	}
	/**
	 * TODO test may run faster if you just add some random character to the name instead of naming it back and forth
	 * TODO change to static later?
	 * TODO Currently set up to check name, change name, and verify name all withing settings menu
	 * Need to implement back button and check name on the CONTROL screen rather than just the settings menu page
	 */
	public void changeName()
	{
		printStartTest("Change Name");
		
		WebElement currentNameFieldElem = d.findByXPath(XPath.applianceNameField, BUTTON_WAIT);
		
		String prevName = currentNameFieldElem.getAttribute("value");
		System.out.println("Previous Name: " + prevName);
		String expectedName = prevName + " renamed";
		currentNameFieldElem.clear();
		currentNameFieldElem.sendKeys(expectedName);
		d.tapByXPath(XPath.backButton, BUTTON_WAIT);
		WebElement currentNameLabelElem = d.findByXPath(XPath.getControlApplianceName("Strombo"), BUTTON_WAIT);
		String actualName = currentNameLabelElem.getText();
		strombo.openSettings();
		
		System.out.println("Expected name: " + expectedName);
		System.out.println("Actual name: " + actualName);

		currentNameFieldElem = d.findByXPath(XPath.applianceNameField, BUTTON_WAIT);
		currentNameFieldElem.clear();
		currentNameFieldElem.sendKeys(prevName);
		if(actualName.equals(expectedName)) {
			printEndTest("Change Name", "PASS");
		} else {
			printEndTest("Change Name", "FAIL");
			fail();
		}
	}

	public void cleanAir() 
	{
		printStartTest("Clean Air");
		
		String expectedState = "";
		boolean success = true;
		WebElement cleanAirToggle = d.findByXPath(XPath.cleanAirToggle, false, driver);
		
		String prevState = cleanAirToggle.getAttribute("class");
		System.out.println("Previous Toggle State: " + prevState);
		
		d.tapByXPath(XPath.cleanAirToggle, TOGGLE_SECS);
		d.thinkWait();
		//if OFF class="toggle" when OFF     if ON class="toggle active"
		if(prevState.equals("toggle")){
			//clean air was off, after tap it should be on
			System.out.println("Turning Clean Air ON");
			expectedState = "toggle active";
		} else if(prevState.equals("toggle active")) {
			//clean air was on, after tap it should be off
			System.out.println("Turning Clean Air OFF");
			expectedState = "toggle";
		}else {
			System.out.println("UNEXPECTED CLEAN AIR TOGGLE STATE: " + prevState);
		}
		String actualState = cleanAirToggle.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Clean Air 1st result: PASS");
		}else {
			System.out.println("Clean Air 1st result: FAIL");
			success = false;
		}


		prevState = actualState;
		System.out.println("Previous Toggle State: " + prevState);
		
		d.tapByXPath(XPath.cleanAirToggle, TOGGLE_SECS);
		d.thinkWait();
		//if OFF class="toggle" when OFF     if ON class="toggle active"
		if(prevState.equals("toggle")){
			//clean air was off, after tap it should be on
			System.out.println("Turning Clean Air ON");
			expectedState = "toggle active";
		} else if(prevState.equals("toggle active")) { 
			//clean air was on, after tap it should be off
			System.out.println("Turning Clean Air OFF");
			expectedState = "toggle";
		} else {
			System.out.println("UNEXPECTED CLEAN AIR TOGGLE STATE: " + prevState);
		}
		actualState = cleanAirToggle.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Clean Air 2nd result: PASS");
		} else {
			System.out.println("Clean Air 2nd result: FAIL");
			success = false;
		}
		
		
		
		if(success) {
			printEndTest("Clean Air", "PASS");
		} else {
			printEndTest("Clean Air", "FAIL");
			fail();
		}
	}

	public void sleepMode() 
	{
		printStartTest("Sleep Mode");
		
		String expectedState = "";
		boolean success = true;
		WebElement sleepModeToggle = d.findByXPath(XPath.sleepModeToggle, false, driver);
		//STOPPING POINT
		String prevState = sleepModeToggle.getAttribute("class");
		System.out.println("Previous Toggle State: " + prevState);
		
		d.tapByXPath(XPath.sleepModeToggle, TOGGLE_SECS);
		d.thinkWait();
		//if OFF class="toggle" when OFF     if ON class="toggle active"
		if(prevState.equals("toggle")){
			//clean air was off, after tap it should be on
			System.out.println("Turning Sleep Mode ON");
			expectedState = "toggle active";
		} else if(prevState.equals("toggle active")) {
			//clean air was on, after tap it should be off
			System.out.println("Turning Sleep Mode OFF");
			expectedState = "toggle";
		}else {
			System.out.println("UNEXPECTED SLEEP TOGGLE STATE: " + prevState);
		}
		String actualState = sleepModeToggle.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Sleep 1st result: PASS");
		}else {
			System.out.println("Sleep 1st result: FAIL");
			success = false;
		}


		prevState = actualState;
		System.out.println("Previous Toggle State: " + prevState);
		
		d.tapByXPath(XPath.sleepModeToggle, TOGGLE_SECS);
		d.thinkWait();
		//if OFF class="toggle" when OFF     if ON class="toggle active"
		if(prevState.equals("toggle")){
			//clean air was off, after tap it should be on
			System.out.println("Turning Sleep Mode ON");
			expectedState = "toggle active";
		} else if(prevState.equals("toggle active")) { 
			//clean air was on, after tap it should be off
			System.out.println("Turning Sleep Mode OFF");
			expectedState = "toggle";
		} else {
			System.out.println("UNEXPECTED SLEEP TOGGLE STATE: " + prevState);
		}
		actualState = sleepModeToggle.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Sleep Mode 2nd result: PASS");
		} else {
			System.out.println("Sleep Mode 2nd result: FAIL");
			success = false;
		}
		
		
		
		if(success) {
			printEndTest("Sleep Mode", "PASS");
		} else {
			printEndTest("Sleep Mode", "FAIL");
			fail();
		}
	}
	
	public void timeZone() 
	{
		int c = 0;
		d.tapByXPath(XPath.timeZoneOuterButton, BUTTON_WAIT);
		for(int i = 0; i <= 11; i++) 
		{
			System.out.println(c++);
			d.tapByXPath(XPath.getTimeZoneInnerButton(i), BUTTON_WAIT);
			System.out.println(c++);
			d.tapByXPath(XPath.timeZoneOuterButton, BUTTON_WAIT);
			System.out.println(c++);
			WebElement checkedElem = d.findByXPath(XPath.timeZoneChecked, BUTTON_WAIT);
			System.out.println(c++);
			String idString = checkedElem.getAttribute("id");
			System.out.println(c++);
			int expected = i;
			System.out.println(c++);
			int actual = idString.charAt(idString.length()-1);
			System.out.println(c++);
			Assert.assertEquals(expected, actual);
			System.out.println(c++);
		}
		System.out.println(c++);
	}
	
	//should fail
	public void testAssertFail() 
	{
		Assert.assertEquals(1,0); //expected 1 actual 0
	}

	//should pass
	public void testAssertPass() 
	{
		Assert.assertEquals(1,1); //expected 1 actual 1
	}
	public void notification() 
	{
		
	}	
	
	////TEST METHODS////
	public void testPower() 
	{
		//appliance.openControls(this.getName());//ASSUME FOR NOW YOU'RE JUST GOING TO BE ON STROMBOLI SCREEN
		printStartTest("Power on function");
		for(int i = 0; i < 2; i++) {
			if(app.isPowerOn()) {
				System.out.println("Appliance is on. Shutting OFF.");
				d.tapByXPath(XPath.plainPowerButton, BUTTON_WAIT);
				//expect appliance to be off
				if(app.isPowerOn()) {
					printEndTest("Power on function", "FAIL");
					fail();
				} else {
					//pass
				}
			} else {
				System.out.println("Appliance is off. Powering ON.");
				d.tapByXPath(XPath.plainPowerButton, BUTTON_WAIT);
				//expect appliance to be on
				if(app.isPowerOn()) {
					//pass
				} else {
					printEndTest("Power on function", "FAIL");
					fail();
				}
			}			
		}
		printEndTest("Power on function", "PASS");
	}
	
	/**
	 * Simple print formatting
	 * @param testName
	 */
	public static void printStartTest(String testName) 
	{
		System.out.println("\n\n");
		System.out.println("==========================================================================");
		System.out.println("Starting Test - " + testName);
		System.out.println("==========================================================================");
	}
	
	/**
	 * Simple print formatting
	 * @param testName
	 * @param result
	 */
	public static void printEndTest(String testName, String result) 
	{
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("End Result - " + testName + ": " + result);
		System.out.println("--------------------------------------------------------------------------");
		System.out.println();
	}
	
	/**
	 * Verify the empty email validation error. 
	 * The span elements that the errors are stored in can only be accessd by the div on the outside. If the text in the div matches the expected string then it passes. 
	 */
	public void emptyEmailValidation() 
	{
		printStartTest("Empty Email Validation");
		WebElement emailField = d.findByXPath(XPath.emailField, BUTTON_WAIT);
		emailField.clear();
		d.tapByXPath(XPath.signInTwo, BUTTON_WAIT);
		
		//TODO replace this with methods and xpath fields
		WebElement element = (WebElement)(d.findElements(By.xpath("//div[@class='input--error input--error-inline']")).get(0));
		String actual = element.getText();
		String expected = "Please enter a valid email.";
		if(actual.equals(expected)) {
			System.out.println("PASS");			
		}else {
			System.out.println("FAIL");
			fail();
		}
	}
	
	/**
	 * Verify the empty password validation error. 
	 */
	public void emptyPasswordValidation() 
	{
		printStartTest("Empty Password Validation");
		WebElement passwordField = d.findByXPath(XPath.passField, BUTTON_WAIT);
		passwordField.clear();
		d.tapByXPath(XPath.signInTwo, BUTTON_WAIT);

		WebElement element = (WebElement)(d.findElements(By.xpath("//div[@class='input--error input--error-inline']")).get(1));
		String actual = element.getText();
		String expected = "Please enter a valid password (6 characters or more).";
		if(actual.equals(expected)) {
			System.out.println("PASS");			
		}else {
			System.out.println("FAIL");
			fail();
		}	
	}
	
	/**
	 * Attempt with incomplete emails without an address or with spaces. 
	 * @param email
	 */
	public void invalidEmailValidation(String email) 
	{
		//print start test in test class
		WebElement emailField = d.findByXPath(XPath.emailField, BUTTON_WAIT);
		emailField.clear();
		emailField.sendKeys(email);
		d.tapByXPath(XPath.passField, BUTTON_WAIT);

		WebElement element = (WebElement)(d.findElements(By.xpath("//div[@class='input--error input--error-inline']")).get(0));
		String actual = element.getText();
		String expected = "Please enter a valid email.";
		if(actual.equals(expected)) {
			System.out.println("PASS");			
		}else {
			System.out.println("FAIL");
			fail();
		}
	}
	
	//Shouldn't the error for this be something about the password being short?
	public void shortPasswordValidation() 
	{
		printStartTest("Short Pass Validation");
		app.signIn("eluxtester1@gmail.com", "12345");
		boolean validationErrorFound = d.searchForText("Verify your log-in information and retry.", XPath.topValidation, BUTTON_WAIT);
		Assert.assertEquals(true, validationErrorFound);
	}
	
	/**
	 * Try a wrong email or wrong password, and find out if the correct validation error appears. 
	 * @param email
	 * @param password
	 * @param correctCredential
	 */
	public void credentialValidation(String email, String password) {
		//print start test in test class
		app.signIn(email, password);
		boolean validationErrorFound = d.xPathIsDisplayed(XPath.findText("Verify your log-in information and retry."), BUTTON_WAIT);
		Assert.assertEquals(true, validationErrorFound);		
	}
	
	/**
	 * Full sign in sign out process with the correct credentials. 
	 */
	public void signInSignOutValidation() 
	{
		printStartTest("Sign In/Sign Out Validation");
		app.signIn("eluxtester1@gmail.com", "123456");
		app.signOut();
	}
	/**
	 * Forgot Password link from the Sign In page. 
	 * Offset needs to be very accurate while tapping labels. Offset is unreliable because if the top is accurate then the bottom isn't, so it needs to be changed manually for some buttons.
	 * @param email
	 */
	public void forgotPass(String email) 
	{
		boolean passing = true;
		printStartTest("Forgot Password");
		//Change offset for the top half of the screen
		d.changeOffset(-50);
		passing = passing && d.xPathIsDisplayed(XPath.forgotPasswordButton);
		d.tapByXPath(XPath.forgotPasswordButton, BUTTON_WAIT);
		//Change offset for the bottom half of the screen
		d.changeOffset(100);
		passing = passing && d.xPathIsDisplayed(XPath.forgotPasswordEmailField);
		app.typeField(XPath.forgotPasswordEmailField, email);
		passing = passing && d.xPathIsDisplayed(XPath.resetPasswordButton);
		d.tapByXPath(XPath.resetPasswordButton, BUTTON_WAIT);
		passing = passing && d.xPathIsDisplayed(XPath.sendAgainButton);
		d.tapByXPath(XPath.sendAgainButton, BUTTON_WAIT);
		passing = passing && d.xPathIsDisplayed(XPath.signInFromResetButton);
		d.tapByXPath(XPath.signInFromResetButton, BUTTON_WAIT);
		//Reseting offset to original offset
		d.changeOffset(-50);
		if(passing) {
			System.out.println("PASS");
		}else {
			System.out.println("FAIL");
			fail();
		}
	}
	/**
	 * Show password button changes the pass chars from asterisks to legible characters. 
	 * This method does not serve much purpose other than that there is a button that can be pressed. Nothing visual is verified here.  
	 */
	public void showPass() 
	{
		printStartTest("Show/Hide Password");
		d.tapByXPath(XPath.showPassButton, BUTTON_WAIT);
		if(d.xPathIsDisplayed(XPath.passwordShowingValidation, BUTTON_WAIT)) {
			System.out.println("PASS: type='text'");
		}else {
			System.out.println("FAIL");
			fail();
		}
		d.tapByXPath(XPath.hidePassButton, BUTTON_WAIT);
		if(d.xPathIsDisplayed(XPath.passwordHiddenValidation, BUTTON_WAIT)) {
			System.out.println("PASS: type='password'");
		}else {
			System.out.println("FAIL");
			fail();
		}
	}
	
	/**
	 * 
	 */
	public void relaunchApp()
	{
		//TODO move to the Appliance class based on convention of keeping test methods and functionality methods separate. 
		printStartTest("Closing app");
		d.closeApp();
		d.launchApp();
		d.useWebContext();

//		// start app CODE FROM FORUM
//		try{
//			d.runAppInBackground(Duration.ofSeconds(1));//FAILED: app never launched. Forum was unclear on what was supposed to happen...
//			}catch (Exception e) {
//				
//		}
//		try { //wait so that the app can reset
//			System.out.println("60 second wait started");
//			Thread.sleep(5000);
//			System.out.println("halfway");
//			Thread.sleep(5000);
//			System.out.println("60 second wait ended");
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		printStartTest("App has been relaunched");
	}
	
	/**
	 * 
	 */
	public void staySignedIn()
	{
		printStartTest("Stay Signed In");
		d.tapByXPath(XPath.staySignedIn);			
//		if(d.xPathIsDisplayed(XPath.staySignedIn)) {
//			d.tapByXPath(XPath.staySignedIn);			
//		}else {
//			d.tapByXPath(XPath.staySignedIn2);
//		}
		app.signIn();
		relaunchApp();
//		WebElement appPage = d.findByXPath(XPath.addAppliance);
//		if(appPage != null)
//		{
//			d.print("Test Passed");
//		}
//		else
//		{
//			fail();
//		}
		d.tapByXPath(XPath.signInOne);
		boolean expectFalse = d.xPathIsDisplayed(XPath.signInTwo);
		if(expectFalse) {
			System.out.println("TEST FAILED: First sign in button should log on automatically, but has opened the credentails screen instead.");
			fail();
		}else {
			System.out.println("Mostly passing. Need 3 checks to verify.");
			//Check: are we at the appliance control screen?
			//Check: are we at the add new appliance screen?
			//Check: are we at a connection lost screen?
			app.signOut();
		}
	}
}
