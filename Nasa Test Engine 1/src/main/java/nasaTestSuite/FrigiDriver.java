package main.java.nasaTestSuite;

import java.net.URL;
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
import io.cucumber.datatable.dependency.com.fasterxml.jackson.core.io.SegmentedStringWriter;
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.MyXPath;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.String;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
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

public class FrigiDriver extends AndroidDriver
{
	public final int OPEN_WAIT = 120;
	public final int UPDATE_WAIT = 240;
	public final int POWER_SECS = 20;
	public final int BUTTON_WAIT = 20;
	public final int SIGN_IN_WAIT = 120;
	public final int TOGGLE_SECS = 2000;//ms
	
	int oneMinute = 60;

	private URL testServerAddress; 
//	private static AndroidDriver driver; NO LONGER REQUIRED

	public FrigiDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress,desiredCapabilities);
		this.manage().timeouts().implicitlyWait(1000000, TimeUnit.SECONDS);
	}
	
	public void useWebContext() {
		Set<String> contextNames = getContextHandles();
		String webView = contextNames.toArray()[1].toString();
		System.out.println("Switching to web view: " + webView);
		context(webView);
	}
	
	public void useNativeContext() {
		System.out.println("Switching to native view");
		context("NATIVE_APP");
	}
	
//	keep for record. 
//	public void switchToWebView() {
//		Set<String> availableContexts = getContextHandles();
////		System.out.println("Total No of Context Found After we reach to WebView = " + availableContexts.size());
//		for (String context : availableContexts) {
//			System.out.println("Checking: " + context);
//			if (context.contains("WEBVIEW")) {
//				System.out.println("Switching to: " + context);
//				context(context);
//				break;
//			}
//		}
//	}
	
	//srt: JIHAD'S HELPER METHODS
	private MobileElement grabFromClass(String className,int index, AndroidDriver d)
	{
		List<MobileElement> results = null;
		boolean looping = true;
		while(looping) {
			try {
				results = d.findElementsByClassName(className);
				if(results.size()>0) 
				{

					print("Size of " + className + " Elements: " + results.size());
					looping = false;
				}
			}catch(NullPointerException e) {
				print("Looking for button by class: " + className);
				//loops forever if button isn't there
			}
		}
		return results.get(index);
	}
	
	public void clickByXpath(String xPath, int waitSecs)
	{
		myWaitXPath(xPath, waitSecs);
		try {
			WebElement elem = findByXPath(xPath, false, this);
			elem.click();
		}catch(NullPointerException e){
			System.out.println("Failed to find XPath: " + xPath);
		}
	}
	
	//TODO discuss if boolean return based on success/failure of element grab is appropriate. probably not. Have a new method for that like isDisplayed
	public boolean myWaitXPath(String xPath, int waitSecs) 
	{
		boolean success = true;
		try {
			WebDriverWait wait = new WebDriverWait(this, waitSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		}catch (TimeoutException e) {
			System.out.println("XPath Failed: " + xPath);
			System.out.println("Timed out after " + waitSecs + " second(s)");
			success = false;
		}
		return success;
	}
	
	public void myWaitText(String text, int waitSecs) 
	{
		try {
			WebDriverWait wait = new WebDriverWait(this, waitSecs);
			wait.until(ExpectedConditions.visibilityOf(findElementByAndroidUIAutomator("new UiSelector().textContains(\""+ text +"\")")));
		}catch (TimeoutException e) {
			System.out.println("Timed out after " + waitSecs + " second(s)");
		}
	}
	
	public WebElement findByID(String id, boolean looping, AndroidDriver d)
	{
		WebElement result = null;
		if(looping == true) {
			while(looping) {
				try
				{
					result = d.findElementById("com.ELXSmart:id/"+id);
				}
				catch(Exception e)
				{
					print("Failed to find Element with ID:" + id);
				}
			}
		}else {
			try
			{
				result = d.findElementById("com.ELXSmart:id/"+id);
			}
			catch(Exception e)
			{
				print("Failed to find Element with ID:" + id);
			}
		}
		return result;
		
	}
	
	public WebElement findByXPath(String xpath, boolean looping, AndroidDriver d)
	{
		WebElement result = null;
		if(looping == true) {
			while(looping) {
				try
				{
					result = d.findElementById(xpath);
				}
				catch(Exception e)
				{
					print("Failed to find Element with xPath:" + xpath);
				}
			}
		}else {
			try
			{
				result = d.findElementByXPath(xpath);
			}
			catch(Exception e)
			{
				print("Failed to find Element with xPath:" + xpath);
			}
		}
		return result;
		
	}
	

	public WebElement findByXPath(String xPath, int waitSecs)
	{
		myWaitXPath(xPath, waitSecs);
		try {
			WebElement elem = findByXPath(xPath, false, this);
			return elem;
		}catch(NullPointerException e){
			System.out.println("Failed to find XPath: " + xPath);
		}
		return null;
	}
	
//	public WebElement findByXPath(String xpath, boolean looping, AndroidDriver driver)
//	{
//		myWait(xpath, BUTTON_WAIT);
//		return findElementById(xpath);
//	}
	
//	public WebElement findByXPath(String xpath)
//	{
//		myWaitXPath(xpath, BUTTON_WAIT);
//		return findElementById(xpath);
//	}
	
	
	private void switchWifi(String ssid)
	{
		String filePath = new File("").getAbsolutePath();
		filePath = filePath.concat("\\nasaTestSuite\\");
		//Print(filePath);
		
		String exeCommand = "cmd /c start WifiSwitcher.exe";
		String cmdLineArg = " \"" + ssid + "\"";
		String fullCommand = exeCommand + cmdLineArg;
		System.out.println(fullCommand);
		
		try 
		{	
			Runtime.getRuntime().exec(fullCommand, null, new File(filePath));
			System.out.println("Execute!");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void Sleep(int milli)
	{
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void print(String msg)
	{
		System.out.println(msg);
	}
	
	//TODO redesign think so that appium looks for a thinking element before each click rather than waiting after a click
	//There is potential for designing an abstract button class with code that comes with each button. Either that or add stuff to the tap methods
	/**
	 * Stops the driver while the app is thinking
	 */
	public void thinkWait() 
	{	
		//TODO LOOK INTO THE IMPLICIT WAIT ISSUE
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			WebElement thinking = findElementByXPath("//div[@class='loading--content']");
				System.out.println();
				while(thinking.isDisplayed()) {
				    System.out.print("thinking");
				}
				System.out.println();
		}catch(Exception e){
			System.out.println("Thinking not found");
		}
//		System.out.println(1);
//		myWaitXPath(MyXPath.thinking,30);
//		System.out.println(2);
//		try {
//			WebElement thinking = findElementByXPath(MyXPath.thinking);
//			System.out.println(3);
//			WebDriverWait wait = new WebDriverWait(driver, 60);
//			System.out.println(4);
//			wait.until(ExpectedConditions.invisibilityOf(thinking));
//			System.out.println(5);
//		}catch(Exception e) {
//			System.out.println(6);
//			e.getMessage();
//			System.out.println("CAUGHT ERROR: Thinking Stale Reference");
//		}
//		System.out.println(7);
	}
	
//	//REDESIGNED METHOD
//	//TODO There is potential for designing an abstract button class with code that comes with each button. Either that or add stuff to the tap methods
//	/**
//	 * Stops the driver while the app is thinking
//	 */
//	public void thinkWait() 
//	{
//		System.out.println("before");
//		if(myWaitXPath(MyXPath.thinking,1)) {
//			System.out.println("THINKING");
//			WebElement thinking = findByXPath(MyXPath.thinking, false, driver);
//			try {
//				WebDriverWait wait = new WebDriverWait(driver, 10);
//				wait.until(ExpectedConditions.invisibilityOf(thinking));
//				System.out.println("FINISHED THIKNING");
//			}catch(Exception e) {
//				e.getMessage();
//				System.out.println("CAUGHT ERROR: Thinking Stale Reference");
//			}
//		}
//		System.out.println("after");
//	}
	
	public boolean searchForXPath(String xPath, int wait) 
	{
		boolean result = false;
		try {
			myWaitXPath(xPath, wait);
			WebElement elem = findByXPath(xPath, false, this);
			result = true;
		}catch(WebDriverException e) {
			e.getMessage();
			System.out.println("XPath not found: " + xPath);
			System.out.println("Did that print twice?");
		}
		return result;
	}
	
	public boolean searchForText(String text, int wait) 
	{
		System.out.println("Searching for: " + text);
		boolean result = false;
		try {
			myWaitText(text, wait);
			WebElement elem = findElementByAndroidUIAutomator("new UiSelector().textContains(\""+ text +"\")");
			result = true;
			System.out.println("Found text: " + text);
		}catch(WebDriverException e) {
			//e.printStackTrace();
			System.out.println("Not Found in Search: " + text);
		}
		return result;
	}

	public void tapByXPath(String xPath, int waitSecs) {
		myWaitXPath(xPath, waitSecs);
		WebElement elem = null;
		try {
			elem = findByXPath(xPath, false, this);
		}catch(NullPointerException e){
			System.out.println("Failed to find XPath: " + xPath);
		}
		if(elem != null) {
			tapOnElement(elem);
		} else {
			System.out.println("Problem tapping xpath: " + xPath);
		}
	}
	
	public void tapOnElement(WebElement element){
		thinkWait();
		float[] elementLocation = getElementCenter(element);
		int elementCoordinateX, elementCoordinateY; 
		elementCoordinateX = Math.round(elementLocation[0]);
		elementCoordinateY = Math.round(elementLocation[1]);
//		context("NATIVE_APP");
//		System.out.println("Switching to web view: NATIVE_APP");
		MobileDriver mDriver = this;
		new TouchAction(mDriver).tap(PointOption.point(elementCoordinateX, elementCoordinateY)).perform();
//		TouchAction action = new TouchAction(driver); OLD OUTDATED CODE
//		action.tap(elementCoordinateX, elementCoordinateX).perform();

//		ActionParameter action = new ActionParameter("longPress", LongPressOptions());
//	    parameterBuilder.add(action);
	    //noinspection unchecked
		
	    //TODO too many webcontext switches
		useWebContext();
	}

	//My changes: offset
	public float[] getElementCenter(WebElement element){
		System.out.println();
		System.out.println("Tapping element: " + element);
//		useWebContext();
		JavascriptExecutor js = (JavascriptExecutor)this;
		// get webview dimensions
		Long webviewWidth  = (Long) js.executeScript("return screen.width");
		Long webviewHeight = (Long) js.executeScript("return screen.height");
		System.out.println("webview width: " + webviewWidth);
		System.out.println("webview height: " + webviewHeight);
		// get element location in webview
		int elementLocationX = element.getLocation().getX();
		int elementLocationY = element.getLocation().getY();
		System.out.println("elementLocationX: " + elementLocationX);
		System.out.println("elementLocationY: " + elementLocationY);
		// get the center location of the element
		int elementWidthCenter = element.getSize().getWidth() / 2;
		int elementHeightCenter = element.getSize().getHeight() / 2;
		int elementWidthCenterLocation = elementWidthCenter + elementLocationX;
		int elementHeightCenterLocation = elementHeightCenter + elementLocationY;
		// switch to native context
		context("NATIVE_APP");
		System.out.println("Switching to web view: NATIVE_APP");
		float deviceScreenWidth, deviceScreenHeight;
		// offset
		int s8offset = 160;//used to be 115
		// get the actual screen dimensions
		deviceScreenWidth  = manage().window().getSize().getWidth();
		deviceScreenHeight = manage().window().getSize().getHeight();
		System.out.println("deviceScreenWidth: " + deviceScreenWidth);
		System.out.println("deviceScreenHeight: " + deviceScreenHeight);
		// calculate the ratio between actual screen dimensions and webview dimensions
		float ratioWidth = deviceScreenWidth / webviewWidth.intValue();
		float ratioHeight = deviceScreenHeight / webviewHeight.intValue();
		// calculate the actual element location on the screen
		float elementCenterActualX = elementWidthCenterLocation * ratioWidth;
		float elementCenterActualY = (elementHeightCenterLocation * ratioHeight) + s8offset;
		System.out.println("elementCenterActualX: " + elementCenterActualX);
		System.out.println("elementCenterActualY: " + elementCenterActualY);
		System.out.println();
		float[] elementLocation = {elementCenterActualX, elementCenterActualY};
		// switch back to webview context
//		useWebContext();
		return elementLocation;
	}
	
	////TEST METHODS////
	public void testPowerOn() 
	{
		//appliance.openControls(this.getName());//ASSUME FOR NOW YOU'RE JUST GOING TO BE ON STROMBOLI SCREEN
		printStartTest("Power on function");
		if(this.isPowerOn()) {//if power is on turn it off so we can test power on function
			System.out.println("Appliance was already on. Powering down to verify test.");
			tapByXPath(MyXPath.powerOffButton, BUTTON_WAIT);
		}
		tapByXPath(MyXPath.powerOnButton, BUTTON_WAIT);

		//verify
		if(this.isPowerOn()) {
			printEndTest("Power on function", "PASS");
		}else{
			printEndTest("Power on function", "FAIL");
			fail();
		}
	}
	
	
	
	public static void printStartTest(String testName) 
	{
		System.out.println("\n\n");
		System.out.println("==========================================================================");
		System.out.println("Starting Test - " + testName);
		System.out.println("==========================================================================");
	}
	
	public static void printEndTest(String testName, String result) 
	{
		System.out.println("==========================================================================");
		System.out.println("End Result - " + testName + ": " + result);
		System.out.println("==========================================================================");
		System.out.println();
	}
	////ACTIONS////
	
//	//David: I plan on abstracting this class at a later date, but I don't want to break anything right now
//	public void powerButton() 
//	{
//		boolean powerOn = isPowerOn();
//		//assumes isPowerOn() has been used at the start of testing - David
//		clickByXpath(MyXPath.powerButton, POWER_SECS);
//		if(powerOn) 
//		{
//			System.out.println(this.getName() + " powering down");
//		}
//		else 
//		{
//			System.out.println(this.getName() + " powering on");
//		}
//		thinkWait();
//	}
	
	////GETTERS SETTERS////
	
//	public boolean isPowerOn() 
//	{
//		boolean powerOn = !searchForText("Off", BUTTON_WAIT);
//		System.out.println("isPowerOn: " + powerOn);
//		return powerOn;
//	}

	public boolean isPowerOn() 
	{
		boolean powerOn = false;
		try {
			if(findByXPath(MyXPath.powerOnButton, BUTTON_WAIT).isDisplayed()) {
				System.out.println("POWER IS ON AND Xpath powerOnButton *SHOULD* BE SHOWING");
				powerOn = false;	
			}
		}catch(Exception e) {
			powerOn = true;
			System.out.println(e.getMessage());
		}
		System.out.println("isPowerOn: " + powerOn);
		return powerOn;
	}
	
	public AndroidDriver getDriver() 
	{
		return this;
	}
}