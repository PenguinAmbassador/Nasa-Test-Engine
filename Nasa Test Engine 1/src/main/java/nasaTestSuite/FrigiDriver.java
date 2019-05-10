package main.java.nasaTestSuite;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import main.java.nasaTestSuite.XPath;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.String;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class FrigiDriver extends AndroidDriver
{
	//S8: 150 Nexus6p: 170 
	private int offset = 160; //phone offset
	public final int OPEN_WAIT = 120;
	public final int UPDATE_WAIT = 240;
	public final int POWER_SECS = 20;
	public final int BUTTON_WAIT = 20;
	public final int SHORT_WAIT = 1;
	public final int SIGN_IN_WAIT = 120;
	public final int TEXT_SEARCH_WAIT = 5;
	public final int TOGGLE_SECS = 2000;//ms
	
	int oneMinute = 60;

	private URL testServerAddress; 
	private PhoneConfig phone;

	//Coordinate vars
	Long webviewWidth, webviewHeight;
	float ratioWidth, ratioHeight, nativeDeviceScreenWidth, nativeDeviceScreenHeight;
	boolean propertiesLoaded = false;

	public FrigiDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress,desiredCapabilities);
		this.manage().timeouts().implicitlyWait(1000000, TimeUnit.SECONDS);
		phone = new PhoneConfig(150, "", "");
	}
	
	//offset used in tap function
	public FrigiDriver(URL remoteAddress, Capabilities desiredCapabilities, PhoneConfig phone) {
		super(remoteAddress,desiredCapabilities);
		this.manage().timeouts().implicitlyWait(1000000, TimeUnit.SECONDS);
		this.phone = phone;
		this.offset = phone.getOffset();
	}
	
	public void useWebContext() {
		Set<String> contextNames = getContextHandles();
		String webView = contextNames.toArray()[1].toString();
//		System.out.println("Switching to web view: " + webView);
		context(webView);
	}
	
	public void useNativeContext() {
//		System.out.println("Switching to native view");
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
	
	//srt: JIHAD'S HELPER METHOD
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
		try {
			WebElement elem = findByXPath(xPath, waitSecs);
			System.out.println("Clicking element: " + elem);
			elem.click();
		}catch(NullPointerException e){
			System.out.println("Click null: " + xPath);
		}catch(Exception j) {
			System.out.println("Click failed: " + xPath);
			j.getMessage();
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
	
	/**
	 * Checks is an element is displayed. Verified Method.
	 * @param xPath
	 * @param waitSecs
	 * @return
	 */
	public boolean xPathIsDisplayed(String xPath, int waitSecs, boolean silentMode) 
	{
		boolean success = true;
		try {
			WebDriverWait wait = new WebDriverWait(this, waitSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		}catch (TimeoutException e) {
			if(!silentMode) {
				System.out.println("XPath Failed: " + xPath);
				System.out.println("Timed out after " + waitSecs + " second(s)");				
			}
			success = false;
		}
		if(!silentMode) {
			System.out.println("Verify xpath: " + xPath);
			System.out.println("Displayed: " + success);
		}
		return success;
	}
	
	public boolean xPathIsDisplayed(String xPath) 
	{
		return xPathIsDisplayed(xPath, BUTTON_WAIT, false);
	}
	
	//default silentmode false
	public boolean xPathIsDisplayed(String xPath, int waitSecs) 
	{
		return xPathIsDisplayed(xPath, waitSecs, false);
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
	
//	public WebElement findByID(String id, boolean looping, AndroidDriver d)
//	{
//		WebElement result = null;
//		if(looping == true) {
//			while(looping) {
//				try
//				{
//					result = d.findElementById("com.ELXSmart:id/"+id);
//				}
//				catch(Exception e)
//				{
//					print("Failed to find Element with ID:" + id);
//				}
//			}
//		}else {
//			try
//			{
//				result = d.findElementById("com.ELXSmart:id/"+id);
//			}
//			catch(Exception e)
//			{
//				print("Failed to find Element with ID:" + id);
//			}
//		}
//		return result;
//		
//	}
	
	//Old findByXPath that David made. It's bad code but the new method can't run without it. 
	public WebElement findByXPath(String xpath, boolean looping, AndroidDriver d)
	{
		WebElement result = null;
		if(looping == true) {
//			while(looping) {
				try
				{
					result = d.findElementById(xpath);
				}
				catch(Exception e)
				{
					print("Failed to find Element with xPath:" + xpath);
				}
//			}
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
	
	//TODO New findByXPath that was supposed to replace the old xpath. Causes error when I tried to remove the old method. 
	public WebElement findByXPath(String xPath, int waitSecs)
	{
		myWaitXPath(xPath, waitSecs);
		try {
			AndroidDriver tempDriver = (AndroidDriver)this; //temp driver cast
//			WebElement elem = findByXPath(xPath, false, this);//replacing this line with AppiumDriver method instead of FrigiDriver method didn't work. TODO fix this later.
			WebElement elem = tempDriver.findElementByXPath(xPath); //This worked to replace the old method. Cast was required, however would it be better to use super?
			return elem;
		}catch(NullPointerException e){
			System.out.println("Failed to find XPath: " + xPath);
		}
		return null;
	}

	//list overload
	public WebElement findByXPaths(String xPath, int index, int waitSecs)
	{
		myWaitXPath(xPath, waitSecs);
		try {
			WebElement elem = (WebElement)findElementsByXPath(xPath).get(index);//replacing this line with AppiumDriver method instead of FrigiDriver method didn't work. TODO fix this later.
			return elem;
		}catch(NullPointerException e){
			System.out.println("Failed to find XPath: " + xPath);
		}
		return null;
	}
	
	//overload
	public WebElement findByXPath(String xpath)
	{
		return findByXPath(xpath, BUTTON_WAIT);
	}
	
	
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
			e.printStackTrace();
		}
	}
	public void print(String msg)
	{
		System.out.println(msg);
	}

	//TODO redesign think so that appium looks for a thinking element before each click rather than waiting after a click
	//There is potential for designing an abstract button class with code that comes with each button. Either that or add stuff to the tap methods
	/**
	 * Wait for the thinking element to appear then disappear
	 * @param seconds Seconds spent waiting before checking for the element. Most of the time thinking icon will appear after 3 seconds, but will appear immediatly after using power button.
	 */
	public void thinkWait(int seconds) {
		long startTime = System.currentTimeMillis();
		//TODO Account for Connection Down screen
		//TODO Account for Internet Alert error
		//TODO LOOK INTO THE IMPLICIT WAIT ISSUE
		if(seconds > 0) {
			try {
				Thread.sleep(seconds*1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			WebElement thinkingElement = findElementByXPath("//div[@class='loading--content']");
			boolean thinking = thinkingElement.isDisplayed();
			if(thinking) {
				System.out.println("Thinking");
			}
			while(thinking) {
			    if(xPathIsDisplayed(XPath.longerThanExpectedButton, 0, true)) {
			    	System.out.println("TEST FAILED: thinking longer than expected");
			    	tapByXPath(XPath.longerThanExpectedButton);
			    	fail();
			    }
			    thinking = thinkingElement.isDisplayed();
			}
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

		long stopTime = System.currentTimeMillis();
		System.out.println("Thinking in Seconds: " + ((stopTime-startTime)/1000f));
	
	}
	
	/**
	 * Stops the driver while the app is thinking. Currently checks for a thinking element after 4 seconds of waiting. The program stays inside a loop until app is done thinking.
	 */
	public void thinkWait() 
	{	
		thinkWait(4);
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
	
	//same as xpath?
	//TODO Check for usage and delete
	public boolean searchForXPath(String xPath, int wait) 
	{
		boolean result = false;
		try {
			WebElement elem = findByXPath(xPath, wait);
			result = true;
		}catch(WebDriverException e) {
			e.getMessage();
			System.out.println("XPath not found: " + xPath);
			System.out.println("Did that print twice?");
		}
		return result;
	}
	
	/**
	 * Search by text. Currently not working or is unreliable. Span elements have proven difficult to locate. 
	 * @param text
	 * @param xpath
	 * @param wait
	 * @return
	 */
	//TODO BAD CODE?
	public boolean searchForText(String text, String xpath, int wait) 
	{
		boolean result;
		System.out.println("Searching for: " + text);
		WebElement target = findByXPath(xpath, wait);
		String actual = target.getText();
		System.out.println("Actual: " + actual);
		if(actual.equals(text)){
			result =  true;
		}else {
			result = false;
		}
		System.out.println("Found: " + result);
		return result;
	}
	
	/**
	 * Tap by xpath
	 * @param xPath
	 * @param waitSecs
	 */
	public void tapByXPath(String xPath, int waitSecs){
		myWaitXPath(xPath, waitSecs);
		WebElement elem = null;
		try {
			elem = findByXPath(xPath, waitSecs); //TODO: this bugs me. why won't it work without this fallback?
		}catch(NullPointerException e){
			System.out.println("Failed to find XPath: " + xPath);
		}
		if(elem != null) {
			tapOnElement(elem);
		} else {
			System.out.println("Problem tapping xpath: " + xPath);
		}
		thinkWait(0);
	}
	
	/**
	 * Overloaded tapByXPath(String, String)
	 * @param xPath
	 */
	public void tapByXPath(String xPath) {
		tapByXPath(xPath, BUTTON_WAIT);
	}

	//testing
	public String getInnerHTML(String xpath) {
		WebElement element = (WebElement)findElementsByXPath(xpath).get(0);
		String result = element.getAttribute("innerHTML");
		System.out.println("THE INNER HTML" + result);
		return result;
	}
	
	public void tapOnElement(WebElement element)  {
		float[] elementLocation = getElementCenter(element);
		int elementCoordinateX, elementCoordinateY; 
		elementCoordinateX = Math.round(elementLocation[0]);
		elementCoordinateY = Math.round(elementLocation[1]);
		MobileDriver mDriver = this;
		new TouchAction(mDriver).tap(PointOption.point(elementCoordinateX, elementCoordinateY)).perform();		
		useWebContext();
	}
	
	public void loadScreenProperties(){
		long startTime = System.currentTimeMillis();
		JavascriptExecutor js = (JavascriptExecutor)this;
		// get webview dimensions
		webviewWidth  = (Long) js.executeScript("return screen.width");
		webviewHeight = (Long) js.executeScript("return screen.height");
		
				
		// switch to native context
		context("NATIVE_APP");
		// get the actual screen dimensions
		nativeDeviceScreenWidth  = manage().window().getSize().getWidth();
		nativeDeviceScreenHeight = manage().window().getSize().getHeight();
		// calculate the ratio between actual screen dimensions and webview dimensions
		ratioWidth = nativeDeviceScreenWidth / webviewWidth.intValue();
		ratioHeight = nativeDeviceScreenHeight / webviewHeight.intValue();
		useWebContext();
		propertiesLoaded = true;
		long stopTime = System.currentTimeMillis();
		System.out.println("Seconds to load properties: " + ((stopTime - startTime)/1000f));
	}
	
	//My changes: offset
	public float[] getElementCenter(WebElement element){
		long startTime = System.currentTimeMillis();
		if(!propertiesLoaded) {
			loadScreenProperties();
		} 
		
		System.out.println("Tapping element: " + element);
		// get the center location of the element
		int elementWidthCenter = element.getSize().getWidth() / 2;
		int elementHeightCenter = element.getSize().getHeight() / 2;
		// get element location in webview
		int elementLocationX = element.getLocation().getX();
		int elementLocationY = element.getLocation().getY();
		
		int elementWidthCenterLocation = elementWidthCenter + elementLocationX;
		int elementHeightCenterLocation = elementHeightCenter + elementLocationY;
				
		useNativeContext();
		// calculate the actual element location on the screen
		float elementCenterActualX = elementWidthCenterLocation * ratioWidth;
		float elementCenterActualY = (elementHeightCenterLocation * ratioHeight) + offset;
		float[] elementLocation = {elementCenterActualX, elementCenterActualY};
		
		//Print Debug info
		if(false) {
			System.out.println("webview width: " + webviewWidth);
			System.out.println("webview height: " + webviewHeight);
			System.out.println("elementLocationX: " + elementLocationX);
			System.out.println("elementLocationY: " + elementLocationY);
			System.out.println("deviceScreenWidth: " + nativeDeviceScreenWidth);
			System.out.println("deviceScreenHeight: " + nativeDeviceScreenHeight);
			System.out.println("elementCenterActualX: " + elementCenterActualX);
			System.out.println("elementCenterActualY: " + elementCenterActualY);
		}	

		long stopTime = System.currentTimeMillis();
		System.out.println("Seconds to tap by coordinates: " + (((float)stopTime - (float)startTime)/1000f));
		return elementLocation;
	}
	
	/**
	 * Tap until password show button is successfully tapped. Find the median in an array of successful taps and set the offset to this median. 
	 */
	public void calculateOffset() {
		System.out.println("Calculating Offset");
		boolean foundRange = false;
		boolean passwordShowing = false;
		ArrayList<Integer> successfulTaps = new ArrayList<Integer>();
		
		//Placeholder email since validation errors will move the target button.		
		WebElement elem = findByXPath(XPath.emailField, BUTTON_WAIT);
		elem.clear();
		elem.sendKeys("placeholder@gmail.com");
		//Typical loop should be from 50-250 but 0-300 just to be safe
		for(int i = 0; i < 200; i = i + 10) {
			System.out.println("LoopNum: " + i);
			System.out.println("\tPasswordShowing: " + passwordShowing);
//			this.offset = i; //DELETE
			if(passwordShowing) {
				tapByXPath(XPath.hidePassButton, SHORT_WAIT);
				if(xPathIsDisplayed(XPath.passwordHiddenValidation, SHORT_WAIT)) {
					System.out.println(i + ": Successful Hide Tap");
					successfulTaps.add(i);
					passwordShowing = false;
					foundRange = true;
				}else if(foundRange){
					//unsuccessful tap
					//range already found, so end loop
					i = 1000;
				}else{
					//unsuccessful tap
				}				
			}else{
				tapByXPath(XPath.showPassButton, SHORT_WAIT);
				if(xPathIsDisplayed(XPath.passwordShowingValidation, SHORT_WAIT)) {
					System.out.println(i + ": Successful Show Tap");
					successfulTaps.add(i);
					passwordShowing = true;
					foundRange = true;
				}else if(foundRange){
					//unsuccessful tap
					//range already found, so end loop
					i = 1000;
				}else{
					//unsuccessful tap
				}
			}		
		}		
		System.out.println("ARRAY: " + successfulTaps);
		for(int j = 0; j < (successfulTaps.size()-1); j++) {
			System.out.println(successfulTaps.get(j) + ", ");
		}
		System.out.println(successfulTaps.get(successfulTaps.size()-1));
		
		double median;
		if (successfulTaps.size() % 2 == 0) {
		    median = ((double)successfulTaps.get(successfulTaps.size()/2) + (double)successfulTaps.get(successfulTaps.size()/2 - 1))/2;
		}else {
		    median = (double) successfulTaps.get(successfulTaps.size()/2);
		}
		offset = (int) median;
		changeOffset(50);
		System.out.println("OFFSET: " + offset);
	}

	public void changeOffset(int difference) {
		if(phone.isAccurateOffset()) {
			//do nothing
		}else {
			offset += difference;			
		}		
	}
	
	//how-to-scroll-with-appium
	public void scrollDown() {
		useNativeContext();
	    //if pressX was zero it didn't work for me
	    int pressX = manage().window().getSize().width / 2;
	    System.out.println("pressX" + pressX);
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = manage().window().getSize().height * 7/8; //used to be 4/5
	    System.out.println("bottomY" + bottomY);
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = manage().window().getSize().height / 8;
	    System.out.println("topY" + topY);
	    //scroll with TouchAction by itself
	    scroll(pressX, bottomY, pressX, topY);
	    useWebContext();
	}

	//how-to-scroll-with-appium
	public void scrollDown(int scrollOffset) {
		useNativeContext();
	    //if pressX was zero it didn't work for me
	    int pressX = manage().window().getSize().width / 2;
	    System.out.println("pressX" + pressX);
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = manage().window().getSize().height * 7/8; //used to be 4/5
	    System.out.println("bottomY" + bottomY);
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = manage().window().getSize().height / 8;
	    System.out.println("topY" + topY);
	    //scroll with TouchAction by itself
	    scroll(pressX, (bottomY + scrollOffset), pressX, topY);
	    useWebContext();
	}
	
	//NOT WORKING, labels are hard to find by xpath
	public void scrollDown(WebElement element) {
		//certain elements are unscrollable so it is more reliable to scroll starting at a particular element
		int bottomY = (int)getElementCenter(element)[1];
		
	    //center of the screen
	    int pressX = manage().window().getSize().width / 2;
	    System.out.println("pressX" + pressX);
	    System.out.println("bottomY" + bottomY);
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = manage().window().getSize().height / 8;
	    System.out.println("topY" + topY);
	    //scroll with TouchAction by itself
	    scroll(pressX, bottomY, pressX, topY);
	}
	
	/**
	 * Take a screenshot and save it the project root directory
	 * @param name Filename of the picture. Should not include a file extension. 
	 */
	public void takeScreenshot(String name) {
		try {
			File file  = ((TakesScreenshot)this).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(name + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void takeScreenshot(String name, String path) {
		takeScreenshot(path+name);
	}
	
	//how-to-scroll-with-appium
	public void scroll(int fromX, int fromY, int toX, int toY) {
	    TouchAction touchAction = new TouchAction(this);
	    touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
	}	
	
	public int getOffset() {
		return offset;
	}
	
	public AndroidDriver getDriver() 
	{
		return this;
	}
}