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
import main.java.nasaTestSuite.TestCapabilities;
import main.java.nasaTestSuite.XPath;
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
	//S8: 150 Nexus6p: 170 
	public int offset = 160; //phone offset
	public final int OPEN_WAIT = 120;
	public final int UPDATE_WAIT = 240;
	public final int POWER_SECS = 20;
	public final int BUTTON_WAIT = 20;
	public final int OFFSET_WAIT = 1;
	public final int SIGN_IN_WAIT = 120;
	public final int TOGGLE_SECS = 2000;//ms
	
	int oneMinute = 60;

	private URL testServerAddress; 


	public FrigiDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress,desiredCapabilities);
		this.manage().timeouts().implicitlyWait(1000000, TimeUnit.SECONDS);
	}
	
	//offset used in tap function
	public FrigiDriver(URL remoteAddress, Capabilities desiredCapabilities, int offset) {
		super(remoteAddress,desiredCapabilities);
		this.offset = offset;
		this.manage().timeouts().implicitlyWait(1000000, TimeUnit.SECONDS);
	}
	
	public void useWebContext() 
	{
		Set<String> contextNames = getContextHandles();
		String webView = contextNames.toArray()[1].toString();
		context(webView);
	}
	
	public void useNativeContext() 
	{
		context("NATIVE_APP");
	}
	
	public void clickByXpath(String xPath, int waitSecs)
	{
		if(myWaitXPath(xPath, waitSecs))
		{
			try 
			{
				WebElement elem = findByXPath(xPath, false, this);
				elem.click();
			}
			catch(NullPointerException e)
			{
				System.out.println("Failed to find XPath: " + xPath);
			}
		}
		
	}
	
	//TODO discuss if boolean return based on success/failure of element grab is appropriate. probably not. Have a new method for that like isDisplayed
	public boolean myWaitXPath(String xPath, int waitSecs) 
	{
		boolean success = true;
		try 
		{
			WebDriverWait wait = new WebDriverWait(this, waitSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
		}
		catch (TimeoutException e) 
		{
			System.out.println("XPath Failed: " + xPath);
			System.out.println("Timed out after " + waitSecs + " second(s)");
			success = false;
		}
		return success;
	}
	
	/**
	 * Checks is an element is displayed. Verified Method.
	 * @param xPath String: gathered from with the app that points to the desired element
	 * @param waitSecs int: time to wait for the desired element to load before throwing an exception
	 * @return boolean: determines whether or not desired element was found
	 */
	public boolean xPathIsDisplayed(String xPath, int waitSecs) 
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
		System.out.println("Verify xpath: " + xPath);
		System.out.println("Displayed: " + success);
		return success;
	}
	public boolean xPathIsDisplayed(String xPath) 
	{
		return xPathIsDisplayed(xPath, BUTTON_WAIT);
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
	
	
	//Old findByXPath that David made. It's bad code but the new method can't run without it. 
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
	
	//New findByXPath that was supposed to replace the old xpath. Causes error when I tried to remove the old method. 
	public WebElement findByXPath(String xPath, int waitSecs)
	{
		myWaitXPath(xPath, waitSecs);
		try 
		{
			WebElement elem = super.findElementByXPath(xPath);
			return elem;
		}
		catch(NullPointerException e)
		{
			System.out.println("Failed to find XPath: " + xPath);
		}
		return null;
	}
	
	//overload
	public WebElement findByXPath(String xpath)
	{
		myWaitXPath(xpath, BUTTON_WAIT);
		return findElementById(xpath);
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
	
	
	public void print(String msg)
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
			    if(xPathIsDisplayed(XPath.longerThanExpectedButton, 0)) {
			    	System.out.println("TEST FAILED: thinking longer than expected");
			    	tapByXPath(XPath.longerThanExpectedButton);
			    	fail();
			    }
			}
			System.out.println();
		}catch(Exception e){
			System.out.println("Thinking not found");
		}
	}
	
	//TODO Check for usage and delete
	public boolean searchForXPath(String xPath, int wait) 
	{
		boolean result = false;
		try 
		{
			myWaitXPath(xPath, wait);
			WebElement elem = findByXPath(xPath, false, this);
			result = true;
		}
		catch(WebDriverException e) 
		{
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
	//BAD CODE
	public boolean searchForText(String text, String xpath, int wait) 
	{
		boolean result;
		System.out.println("Searching for: " + text);
		WebElement target = findByXPath(xpath, BUTTON_WAIT);
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
	 * Tap: performs a tap action on the element at the specified XPath 
	 * @param xPath string: gathered from with the app that points to the desired element
	 * @param waitSecs int: time to wait for the desired element to load before throwing an exception
	 */
	public void tapByXPath(String xPath, int waitSecs) {
		thinkWait();
		myWaitXPath(xPath, waitSecs);
		WebElement elem = null;
		try 
		{
			elem = findByXPath(xPath, false, this); //TODO: this bugs me. why won't it work without this fallback?
		}
		catch(NullPointerException e)
		{
			System.out.println("Failed to find XPath: " + xPath);
		}
		if(elem != null) 
		{
			tapOnElement(elem);
		} 
		else 
		{
			System.out.println("Problem tapping xpath: " + xPath);
		}
	}
	
	/**
	 * Overloaded tapByXPath(String, String)
	 * @param xPath String: gathered from with the app that points to the desired element
	 */
	public void tapByXPath(String xPath) 
	{
		tapByXPath(xPath, BUTTON_WAIT);
	}
	
	private void tapOnElement(WebElement element)
	{
		float[] elementLocation = getElementCenter(element);
		int elementCoordinateX, elementCoordinateY; 
		elementCoordinateX = Math.round(elementLocation[0]);
		elementCoordinateY = Math.round(elementLocation[1]);
		MobileDriver mDriver = this;
		new TouchAction(mDriver).tap(PointOption.point(elementCoordinateX, elementCoordinateY)).perform();		
		useWebContext();
	}

	
	//My changes: offset
	public float[] getElementCenter(WebElement element){
		System.out.println();
		System.out.println("Tapping element: " + element);
		JavascriptExecutor js = (JavascriptExecutor)this;
		// get webview dimensions
		Long webviewWidth  = (Long) js.executeScript("return screen.width");
		Long webviewHeight = (Long) js.executeScript("return screen.height");
		// get element location in webview
		int elementLocationX = element.getLocation().getX();
		int elementLocationY = element.getLocation().getY();
		// get the center location of the element
		int elementWidthCenter = element.getSize().getWidth() / 2;
		int elementHeightCenter = element.getSize().getHeight() / 2;
		int elementWidthCenterLocation = elementWidthCenter + elementLocationX;
		int elementHeightCenterLocation = elementHeightCenter + elementLocationY;
		// switch to native context
		context("NATIVE_APP");
		float deviceScreenWidth, deviceScreenHeight;
		// offset. Commenting out for development of offset calculation.
//		int offset = 160;//used to be 115
		// get the actual screen dimensions
		deviceScreenWidth  = manage().window().getSize().getWidth();
		deviceScreenHeight = manage().window().getSize().getHeight();
		// calculate the ratio between actual screen dimensions and webview dimensions
		float ratioWidth = deviceScreenWidth / webviewWidth.intValue();
		float ratioHeight = deviceScreenHeight / webviewHeight.intValue();
		// calculate the actual element location on the screen
		float elementCenterActualX = elementWidthCenterLocation * ratioWidth;
		float elementCenterActualY = (elementHeightCenterLocation * ratioHeight) + offset;
		System.out.println();
		float[] elementLocation = {elementCenterActualX, elementCenterActualY};
		
		//Print Debug info
		if(!false) 
		{
			System.out.println("webview width: " + webviewWidth);
			System.out.println("webview height: " + webviewHeight);
			System.out.println("elementLocationX: " + elementLocationX);
			System.out.println("elementLocationY: " + elementLocationY);
			System.out.println("deviceScreenWidth: " + deviceScreenWidth);
			System.out.println("deviceScreenHeight: " + deviceScreenHeight);
			System.out.println("elementCenterActualX: " + elementCenterActualX);
			System.out.println("elementCenterActualY: " + elementCenterActualY);
		}
		
		return elementLocation;
	}
	
	/**
	 * Tap until password show button is successfully tapped. Find the median in an array of successful taps and set the offset to this median. 
	 */
	public void calculateOffset() 
	{
		System.out.println("Calculating Offset");
		boolean foundRange = false;
		boolean passwordShowing = false;
		ArrayList<Integer> successfulTaps = new ArrayList<Integer>();
		
		//Placeholder email since validation errors will move the target button.		
		WebElement elem = findByXPath(XPath.emailField, BUTTON_WAIT);
		elem.clear();
		elem.sendKeys("placeholder@gmail.com");
		//Typical loop should be from 50-250 but 0-300 just to be safe
		for(int i = 130; i < 2000; i = i + 10) {
			System.out.println("LoopNum: " + i);
			System.out.println("\tPasswordShowing: " + passwordShowing);
			this.offset = i; 
			if(passwordShowing) {
				tapByXPath(XPath.hidePassButton, OFFSET_WAIT);
				if(xPathIsDisplayed(XPath.passwordHiddenValidation, OFFSET_WAIT)) {
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
				tapByXPath(XPath.showPassButton, OFFSET_WAIT);
				if(xPathIsDisplayed(XPath.passwordShowingValidation, OFFSET_WAIT)) {
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
		for(int j = 0; j < (successfulTaps.size()-1); j++) 
		{
			System.out.println(successfulTaps.get(j) + ", ");
		}
		System.out.println(successfulTaps.get(successfulTaps.size()-1));
		
		double median;
		if (successfulTaps.size() % 2 == 0) 
		{
		    median = ((double)successfulTaps.get(successfulTaps.size()/2) + (double)successfulTaps.get(successfulTaps.size()/2 - 1))/2;
		}
		else 
		{
		    median = (double) successfulTaps.get(successfulTaps.size()/2);
		}
		offset = (int) median;
		System.out.println("OFFSET: " + offset);
	}
	
	public void myScroll() 
	{
//		WebElement appliancesLabel = findByXPath(XPath.appliancesLabel);
//		WebElement supportLabel = findByXPath(XPath.supportLabel);
//		
//		TouchAction myAction = new TouchAction(driver); myAction.tap(supportLabel).moveTo(appliancesLabel).release();
	}
	//how-to-scroll-with-appium
	public void scrollDown() {
	    //if pressX was zero it didn't work for me
	    int pressX = manage().window().getSize().width / 2;
	    System.out.println("pressX" + pressX);
	    // 4/5 of the screen as the bottom finger-press point
	    int bottomY = manage().window().getSize().height * 4/5;
	    System.out.println("bottomY" + bottomY);
	    // just non zero point, as it didn't scroll to zero normally
	    int topY = manage().window().getSize().height / 8;
	    System.out.println("topY" + topY);
	    //scroll with TouchAction by itself
	    scroll(pressX, (bottomY+200), pressX, topY);
	}
	
	//how-to-scroll-with-appium
	public void scroll(int fromX, int fromY, int toX, int toY) 
	{
	    TouchAction touchAction = new TouchAction(this);
	    touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
	}

	
}