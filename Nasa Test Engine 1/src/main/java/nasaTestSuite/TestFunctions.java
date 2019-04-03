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
import org.openqa.selenium.interactions.InvalidCoordinatesException;
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
	public final int BUTTON_WAIT = 5;
	public final int SIGN_IN_WAIT = 120;
	public final int TEXT_SEARCH_WAIT = 5;
	
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

		//change name back
		currentNameFieldElem = d.findByXPath(XPath.applianceNameField, BUTTON_WAIT);
		currentNameFieldElem.clear();
		currentNameFieldElem.sendKeys(prevName);
		d.tapByXPath(XPath.backButton, BUTTON_WAIT);
		strombo.openSettings();
		
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

		boolean success = toggleTest(XPath.cleanAirToggle);
		if(success) {
			printEndTest("Clean Air", "PASS");
		} else {
			printEndTest("Clean Air", "FAIL");
			fail();
		}
	}
	
	/**
	 * Test a toggle functionality based on a element/xpath parameter and an expected state (on/off)
	 * @param toggleElem
	 * @param toggleXPath
	 * @param expectedState Are you turning the toggle on or off? Depends on the class attribute of toggleElem is. If you are turning the toggle on an example would be "toggle toggle-select" for the celsius toggle.
	 * @return
	 */
	private boolean toggleTest(WebElement toggleElem, String toggleXPath, String expectedState){//TODO Can't currently tap by element. Remove the toggleXPath if this changes.
		boolean success = true;
		d.tapByXPath(toggleXPath, TOGGLE_SECS);
		d.thinkWait();
		
		String actualState = toggleElem.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Toggle result: PASS");
		} else {
			System.out.println("Toggle result: FAIL - Actual State: " + actualState);
			success = false;
		}
		return success;
	}
	
	/**
	 * Test the temperature on and off functionality.
	 */
	public void toggleTempTest() {
		printStartTest("Temperature Unit Test");
		boolean success = true;
		//Fahreneheit = "toggle toggle-select active" Celsius = "toggle toggle-select"
		String fahrenheit = "toggle toggle-select active";
		String celsius = "toggle toggle-select";
		d.scrollDown(-100);
		WebElement cleanAirToggle = d.findByXPath(XPath.unitToggle);
		String prevState = cleanAirToggle.getAttribute("class");
		
		System.out.println("Previous Toggle State: " + prevState);
		if(prevState.equals(celsius)){
			//If the toggle state was previously on celcius, activate fahrenheit. 
			System.out.println("Turning Toggle ON - Activating Fahrenheit ");
			success = toggleTest(cleanAirToggle, XPath.unitToggle, fahrenheit);		
			d.tapByXPath(XPath.backButton);
			//toggleTest verifies that the toggle moved, but this if/else will go to the previous screen and verify the temps are within the fahrenheit range.
			if(strombo.getTargTemp() >= 60) {
				//PASS - Open settings and scroll down to prepare for the next 
				strombo.openSettings();
				d.scrollDown(-100);
			}else {
				printEndTest("Temp Test", "fail");
				fail();
			}
			//Check the opposite toggle behavior
			System.out.println("Turning Toggle OFF - Activating Celsius");
			success = toggleTest(cleanAirToggle, XPath.unitToggle, celsius);	
			d.tapByXPath(XPath.backButton);
			if(strombo.getTargTemp() < 60) {
				//PASS
				strombo.openSettings();
			}else {
				printEndTest("Temp Test", "fail");
				fail();
			}
		//Check the opposite toggle behavior
		} else if(prevState.equals(fahrenheit)) {
			//CELCIUS
			System.out.println("Turning Toggle OFF - Activating Celsius");
			success = toggleTest(cleanAirToggle, XPath.unitToggle, celsius);	
			d.tapByXPath(XPath.backButton);
			if(strombo.getTargTemp() < 60) {
				//PASS
				strombo.openSettings();
				d.scrollDown(-100);
			}else {
				printEndTest("Temp Test", "fail");
				fail();
			}
			//FAHRENHEIT
			System.out.println("Turning Toggle ON - Activating Fahrenheit");
			success = toggleTest(cleanAirToggle, XPath.unitToggle, fahrenheit);		
			d.tapByXPath(XPath.backButton);
			if(strombo.getTargTemp() >= 60) {
				//PASS
				strombo.openSettings();
			}else {
				printEndTest("Temp Test", "fail");
				fail();
			}
		} else {
			System.out.println("UNEXPECTED TOGGLE STATE: " + prevState);
		}
		if(success) {
			//pass
		}else {
			fail();
		}			
	}
	
	//TODO Remove dependencies and delete this function. The other toggleTest is more flexible. 
	private boolean toggleTest(String toggleXPath) {
		boolean success = true;
		String expectedState = "";
		WebElement cleanAirToggle = d.findByXPath(toggleXPath);
		
		String prevState = cleanAirToggle.getAttribute("class");
		System.out.println("Previous Toggle State: " + prevState);
		//if OFF class="toggle" when OFF     if ON class="toggle active"
		if(prevState.equals("toggle")){
			//clean air was off, after tap it should be on
			System.out.println("Toggling ON");
			expectedState = "toggle active";
		} else if(prevState.equals("toggle active")) {
			//clean air was on, after tap it should be off
			System.out.println("Toggling OFF");
			expectedState = "toggle";
		}else {
			System.out.println("UNEXPECTED TOGGLE STATE: " + prevState);
		}		
		System.out.println("OFFSET: " + d.getOffset());
		d.tapByXPath(toggleXPath, TOGGLE_SECS);
		d.thinkWait();
		
		String actualState = cleanAirToggle.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Toggle 1st result: PASS");
		}else {
			System.out.println("Toggle 1st result: FAIL - Actual State: " + actualState);
			success = false;
		}


		prevState = actualState;
		System.out.println("Previous Toggle State: " + prevState);
				//if OFF class="toggle" when OFF     if ON class="toggle active"
		if(prevState.equals("toggle")){
			//clean air was off, after tap it should be on
			System.out.println("Turning Toggle ON");
			expectedState = "toggle active";
		} else if(prevState.equals("toggle active")) { 
			//clean air was on, after tap it should be off
			System.out.println("Turning Toggle OFF");
			expectedState = "toggle";
		} else {
			System.out.println("UNEXPECTED TOGGLE STATE: " + prevState);
		}
		d.tapByXPath(toggleXPath, TOGGLE_SECS);
		d.thinkWait();
		
		actualState = cleanAirToggle.getAttribute("class");
		if(actualState.equals(expectedState)) {
			System.out.println("Toggle 2nd result: PASS");
		} else {
			System.out.println("Toggle 2nd result: FAIL");
			success = false;
		}		
		return success;
	}

	public void sleepMode() 
	{
		printStartTest("Sleep Mode");

		boolean success = toggleTest(XPath.sleepModeToggle);
		
		
		
		if(success) {
			printEndTest("Sleep Mode", "PASS");
		} else {
			printEndTest("Sleep Mode", "FAIL");
			fail();
		}
	}

	public void notificationTest() {
		printStartTest("Notification Toggle");

		boolean success = toggleTest(XPath.notificationToggle);		
		
		if(success) {
			printEndTest("Notification Toggle", "PASS");
		} else {
			printEndTest("Notification Toggle", "FAIL");
			fail();
		}
	}
	
	public void timeZone() 
	{
		printStartTest("Time Zone");
		String[] timezones = {"Eastern", "Central", "Arizona", "Mountain", "Pacific", "Alaska", "Aleutian", "Hawaii", "Samoa", "Chamorro", "Atlantic", "Newfoundland"};
		boolean fail = false;		
		boolean recheckEastern = false;
		
		WebElement elem = d.findByXPath(XPath.timeZoneOuterButton, TEXT_SEARCH_WAIT);
		String timezone = elem.getText();		
		if(timezone.equals("Eastern")) {
			System.out.println("Recheck Eastern TRUE");
			recheckEastern = true;
		}
		
		for(int i = 0; i <= 11; i++) 
		{
			//TODO add logic for doing the timezone that already is checked off last. 
			if(recheckEastern && i == 0) {
//				d.tapByXPath(XPath.backButton);
			} else {
				d.tapByXPath(XPath.timeZoneOuterButton, BUTTON_WAIT);
				System.out.println("LOOP: " + i);
				//scroll down before tapping the lower elements in the list
				if(i > 7) {
					System.out.println("Scroll Down changed since this was tested. May need to change to scrollDown(+200)");
					d.scrollDown();
				}
				d.tapByXPath(XPath.getTimeZoneInnerButton(i), BUTTON_WAIT);
				WebElement checkedElem = d.findByXPath(XPath.timeZoneOuterButton, BUTTON_WAIT);
				String expected = timezones[i];
				String actual = checkedElem.getText();
				//if Eastern is already checked on the first pass then skip to the next timezone and do eastern last using the easternLast boolean
			
				System.out.println("Actual: " + actual);
				if(expected.equals(actual)) {
					//PASS
				} else {
					System.out.println(i + " FAILED");
					fail = true;
				}				
			}
		}
		
		//recheck eastern
		if(recheckEastern) {
			d.tapByXPath(XPath.timeZoneOuterButton, BUTTON_WAIT);
			d.tapByXPath(XPath.getTimeZoneInnerButton(0), BUTTON_WAIT);
			WebElement checkedElem = d.findByXPath(XPath.timeZoneOuterButton, BUTTON_WAIT);
			String expected = timezones[0];
			String actual = checkedElem.getText();
			//if Eastern is already checked on the first pass then skip to the next timezone and do eastern last using the easternLast boolean
		
			System.out.println("Actual: " + actual);
			if(expected.equals(actual)) {
				//PASS
			}else {
				System.out.println("Eastern Recheck - FAILED");
				fail = true;
			}	
		}
		
		if(fail) {
			printEndTest("Time Zone", "FAIL");
			fail();
		}else {

			printEndTest("Time Zone", "PASS");
		}
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
		printEndTest("Show/Hide Password", "PASS");
	}
	
	/**
	 * Verifies "Stay Signed In" checkbox functionality. 
	 * Behavior: Enter credentials, check the "Stay Signed In" option, and relaunch app. 
	 * Expectation: The first sign in button leads into the app without asking for credentials. 
	 */
	public void staySignedIn()
	{
		printStartTest("Stay Signed In");
		d.tapByXPath(XPath.staySignedIn);			
		app.signIn();
		app.relaunchApp();
		
		//If the app is not signed in, press the first sign in button and if it is still not signed in fail the 
		if(!app.isSignedIn()) {
			d.tapByXPath(XPath.signInOne);
			if(!app.isSignedIn()) {
				printEndTest("Stay Signed In", "FAIL");
				fail();	
			}			
		} else {		
			//PASS
			System.out.println("Did the app sign in without pressing the first sign in button?");
		}
		printEndTest("Stay Signed In", "PASS");
		app.signOut();	
	}
	public void forgotPassBackButton() {
		//TODO add the second back button
		printStartTest("Forgot password back button");
		boolean passing = true;
		d.tapByXPath(XPath.forgotPasswordButton);
		passing = passing && d.xPathIsDisplayed(XPath.forgotPasswordEmailField);	
		d.tapByXPath(XPath.backButton);	
		passing = passing && d.xPathIsDisplayed(XPath.forgotPasswordButton);	
		if(passing) {
			System.out.println("First back button PASS");
		}
	}
	
	public void tempUpBy(int numTaps) {
		System.out.println("Temp up by: " + numTaps);
		WebElement tempPlusElm = d.findByXPath(XPath.stromboTempUp, BUTTON_WAIT);
		int cMinTemp = 16;
		int cMaxTemp = 32;
		int fMinTemp = 60;
		int fMaxTemp = 90;
		int expectedTemp = -1;
		//Change mode until you reach a mode that can change the temperature
		int tempMode = strombo.getModeValue();
		while(tempMode==3 || tempMode==5) 
		{
			strombo.clickModeUp();
			tempMode = strombo.getModeValue();
		}
		int currentTemp = strombo.getTargTemp();
		
		if(currentTemp > cMaxTemp) {
			//fahrenheit
			if((currentTemp + numTaps) > fMaxTemp) {
				expectedTemp = (currentTemp + numTaps) - (fMaxTemp - fMinTemp) - 1;
			} else {
				expectedTemp = currentTemp + numTaps;
			}
		} else {
			//celcius
			if((currentTemp + numTaps) > cMaxTemp) {
				expectedTemp = (currentTemp + numTaps) - (cMaxTemp - cMinTemp) - 1;
			} else {
				expectedTemp = currentTemp + numTaps;
			}
		}
		
		for(int i = 0; i < numTaps; i++) {
			tempPlusElm.click();
		}
		d.thinkWait();	
		currentTemp = strombo.getTargTemp();
		System.out.println("Verify expectedTemp = " + expectedTemp);
		System.out.println("Verify currentTemp = " + currentTemp);
		System.out.println("removed +1 in conditional: verify.");
		if(expectedTemp != currentTemp) 
		{
			printEndTest("Temp Up By", "FAIL");
			fail();
		}
		else
		{
			printEndTest("Temp Up By", "PASS");
		}		
	}
	
	//TODO possibly add a tmepUpBy and then randomize the numbers
	/**
	 * gets difference between 
	 * @param targTemp Expected target temp after execution. 
	 */
	public void tempUpTo(int targTemp) {
		System.out.println("Temp up to: " + targTemp);
		WebElement tempPlusElm = d.findByXPath(XPath.stromboTempUp, BUTTON_WAIT);
		int cMinTemp = 16;
		int cMaxTemp = 32;
		int fMinTemp = 60;
		int fMaxTemp = 90;
		int numTaps = -1;
		//Change mode until you reach a mode that can change the temperature
		int tempMode = strombo.getModeValue();
		while(tempMode==3 || tempMode==5) 
		{
			strombo.clickModeUp();
			tempMode = strombo.getModeValue();
		}
		
		//Current target Temp
		int currTTemp = strombo.getTargTemp();
		//establish number of taps needed
		if(currTTemp <= targTemp) {
			numTaps = targTemp - currTTemp;
			System.out.println("Basic NumTaps: " + numTaps);
		} else {	
			//if the target temp is lower, and you are increasing the temperature you must account for the max temp
			if(currTTemp > cMaxTemp) {
				//fahrenheit
				numTaps = ((targTemp - fMinTemp) + (fMaxTemp - currTTemp));
				System.out.println("F tap num: " + numTaps);
			} else {
				//celcius
				numTaps = ((targTemp - cMinTemp) + (cMaxTemp - currTTemp));		
				System.out.println("C tap num: " + numTaps);	
			}	
		}
		for(int i = 0; i < numTaps; i++) {
			tempPlusElm.click();
		}
		d.thinkWait();		
		int currentTemp = strombo.getTargTemp();
		System.out.println("Verify expectedTemp = " + targTemp);
		System.out.println("Verify currentTemp = " + currentTemp);
		System.out.println("removed +1 in conditional: verify.");
		if(targTemp != currentTemp) 
		{
			printEndTest("Temp Up To", "FAIL");
			fail();
		}
		else
		{
			printEndTest("Temp Up To", "PASS");
		}		
	}
	
	public void tempDownBy(int numTaps) {
		System.out.println("Temp down by: " + numTaps);
		WebElement tempMinusElm = d.findByXPath(XPath.stromboTempDown, BUTTON_WAIT);
		int cMinTemp = 16;
		int cMaxTemp = 32;
		int fMinTemp = 60;
		int fMaxTemp = 90;
		int expectedTemp = -1;
		//Change mode until you reach a mode that can change the temperature
		int tempMode = strombo.getModeValue();
		while(tempMode==3 || tempMode==5) 
		{
			strombo.clickModeUp();
			tempMode = strombo.getModeValue();
		}
		int currentTemp = strombo.getTargTemp();
		
		if(currentTemp > cMaxTemp) {
			//fahrenheit
			if((currentTemp - numTaps) < fMinTemp) {
				expectedTemp = (currentTemp - numTaps) + (fMaxTemp - fMinTemp) + 1;
			} else {
				expectedTemp = currentTemp - numTaps;
			}
		} else {
			//celcius
			if((currentTemp - numTaps) < cMinTemp) {
				expectedTemp = (currentTemp - numTaps) + (cMaxTemp - cMinTemp) + 1;
			} else {
				expectedTemp = currentTemp - numTaps;
			}
		}
		
		for(int i = 0; i < numTaps; i++) {
			tempMinusElm.click();
		}
		d.thinkWait();			
		currentTemp = strombo.getTargTemp();
		System.out.println("Verify expectedTemp = " + expectedTemp);
		System.out.println("Verify currentTemp = " + currentTemp);
		System.out.println("removed +1 in conditional: verify.");
		if(expectedTemp != currentTemp) 
		{
			printEndTest("Temp Down by", "FAIL");
			fail();
		}
		else
		{
			printEndTest("Temp Down by", "PASS");
		}		
	}
	
	//TODO possibly add a tmepUpBy and then randomize the numbers
	/**
	 * gets difference between 
	 * @param targTemp Expected target temp after execution. 
	 */
	public void tempDownTo(int targTemp) {
		System.out.println("Temp Down to: " + targTemp);
		WebElement tempMinusElm = d.findByXPath(XPath.stromboTempDown, BUTTON_WAIT);
		int cMinTemp = 16;
		int cMaxTemp = 32;
		int fMinTemp = 60;
		int fMaxTemp = 90;
		int numTaps = -1;
		//Change mode until you reach a mode that can change the temperature
		int tempMode = strombo.getModeValue();
		while(tempMode==3 || tempMode==5) 
		{
			strombo.clickModeUp();
			tempMode = strombo.getModeValue();
		}
		
		//Current target Temp
		int currTTemp = strombo.getTargTemp();
		//establish number of taps needed
		if(currTTemp >= targTemp) {
			numTaps =  currTTemp - targTemp;
			System.out.println("Basic NumTaps: " + numTaps);
		} else {	
			//if the target temp is lower, and you are increasing the temperature you must account for the max temp
			if(currTTemp > cMaxTemp) {
				//fahrenheit
				numTaps = ((currTTemp - fMinTemp) + (fMaxTemp - targTemp)) + 1;
				System.out.println("F tap num: " + numTaps);
			} else {
				//celcius
				numTaps = ((currTTemp - cMinTemp) + (cMaxTemp -  targTemp)) + 1;		
				System.out.println("C tap num: " + numTaps);	
			}	
		}
		for(int i = 0; i < numTaps; i++) {
			tempMinusElm.click();
		}
		d.thinkWait();		
		int currentTemp = strombo.getTargTemp();
		System.out.println("Verify expectedTemp = " + targTemp);
		System.out.println("Verify currentTemp = " + currentTemp);
		System.out.println("removed +1 in conditional: verify.");
		if(targTemp != currentTemp) 
		{
			printEndTest("Temp Down To", "FAIL");
			fail();
		}
		else
		{
			printEndTest("Temp Down To", "PASS");
		}		
	}
	
	/**
	 * Changes mode once and checks to verify result. 
	 */
	public void modeUp() {
		int expectedMode = strombo.getNextExpectedMode();
		strombo.clickModeUp();
		System.out.println("Mode: " + strombo.getModeValue());
		System.out.println("Expected: " + expectedMode);
		if(expectedMode == strombo.getModeValue()) {
			printEndTest("Mode Up", "PASS");
		}else{
			printEndTest("Mode Up", "FAIL");
			fail();
		}	
	}
	
	/**
	 * Changes mode once and checks to verify result. 
	 */
	public void modeDown() {
		int expectedMode = strombo.getPrevExpectedMode();
		strombo.clickModeDown();
		int currentMode = strombo.getModeValue();
		System.out.println("Mode: " + currentMode);
		System.out.println("Expected: " + expectedMode);
		if(expectedMode == currentMode) {
			printEndTest("Mode Down", "PASS");
		} else {
			printEndTest("Mode Down", "FAIL");
			fail();
		}	
	}
	
	/**
	 * Changes speed once and checks to verify result. 
	 */
	public void speedUp() {
		//Avoid dry mode
		if(strombo.getModeValue()==5) {
			strombo.clickModeUp();
		}
		int expectedSpeed = strombo.getNextExpectedSpeed();
		strombo.clickSpeedUp();
		System.out.println("Speed: " + strombo.getSpeed());
		System.out.println("Expected: " + expectedSpeed);
		if(expectedSpeed == strombo.getSpeed()){
			printEndTest("Speed Up", "PASS");
		}else {
			printEndTest("Speed Up", "FAIL");
			fail();
		}
	}
	
	/**
	 * Changes speed once and checks to verify result. 
	 */
	public void speedDown() {
		//Avoid dry mode
		if(strombo.getModeValue()==5) {
			strombo.clickModeUp();
		}
		int expectedSpeed = strombo.getPrevExpectedSpeed();
		strombo.clickSpeedDown();
		int currentSpeed = strombo.getSpeed();
		System.out.println("Speed: " + currentSpeed);
		System.out.println("Expected: " + expectedSpeed);
		if(expectedSpeed == currentSpeed) {
			printEndTest("Speed Down", "PASS");
		} else {
			printEndTest("Speed Down", "FAIL");
			fail();
		}	
	}
	
	/**
	 * Function checks temp range edge cases to verify that temp increase will go from 90 to 60 and 32 to 16
	 */
	public void tempUpPastMax() {
		if(strombo.getTargTemp() > 32) {
			System.out.println("Temp up past Max fahrenheit");
			tempUpTo(90);
			tempUpBy(1);
			
			strombo.changeTempUnit();
			
			System.out.println("Temp up past Max celcius");
			tempUpTo(32);
			tempUpBy(1);
		} else {
			System.out.println("Temp up past Max celcius");
			tempUpTo(32);
			tempUpBy(1);
						
			strombo.changeTempUnit();
			
			System.out.println("Temp up past Max fahrenheit");
			tempUpTo(90);
			tempUpBy(1);			
		}		
	}
	
	/**
	 * Function checks temp range edge cases to verify that temp decrease will go from 60 to 90 and 16 to 32
	 */
	public void tempDownPastMin() {
		if(strombo.getTargTemp() > 32) {
			System.out.println("Temp down past min fahrenheit");
			tempDownTo(60);
			tempDownBy(1);
			
			strombo.changeTempUnit();

			System.out.println("Temp down past min celsius");
			tempDownTo(16);
			tempDownBy(1);
		} else {
			System.out.println("Temp down past min celsius");
			tempDownTo(16);
			tempDownBy(1);
			
			strombo.changeTempUnit();

			System.out.println("Temp down past min fahrenheit");
			tempDownTo(60);
			tempDownBy(1);			
		}		
	}
}
